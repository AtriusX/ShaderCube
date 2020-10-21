@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package xyz.atrius.shadercube.shader

import org.bukkit.Location
import xyz.atrius.shadercube.data.Spatial
import xyz.atrius.shadercube.data.Updatable
import xyz.atrius.shadercube.util.plugin
import xyz.atrius.shadercube.util.schedule

/**
 * A high-order function that specifies a section of code that will run on
 * each frame update.
 */
typealias Update =
    Shader.() -> Unit

/**
 * A high-order function that specifies the condition or conditions that will
 * cause the execution of a shader to end. By default, this will have no exit
 * condition applied.
 */
typealias Cancel =
    Shader.() -> Boolean

/**
 * Shaders are the primary object used within the Shadercube API. They update for
 * an indefinite amount of time, or until [cancel] is called successfully by the
 * system. By the nature of the [Scheduler][org.bukkit.scheduler.BukkitScheduler]
 * system, the shader will run at a max of 20 frames per second. This number
 * will fluctuate with the rate at which updates are set to occur at depending on
 * the update rate specified.
 *
 * @constructor Constructs a new shader. This may **not** be called directly,
 *              please use [shader] to build instead.
 *
 * @see Spatial
 */
open class Shader protected constructor() : Spatial {
    override lateinit var location: Location

    /**
     * @property time A reference to the current system time (in milliseconds).
     */
    val time: Long
        get() = System.currentTimeMillis()

    /**
     * @property start The starting time (in milliseconds) for the execution
     *                 of the shader.
     */
    val start: Long = time

    /**
     * @property elapsed The elapsed amount of time (in milliseconds) since the
     *                   start of shader execution.
     */
    val elapsed: Long
        get() = time - start

    /**
     * @property framecount The current frame count for this shader's execution.
     */
    var framecount: Int = 0
        private set

    private var taskId: Int = -1

    private var objects: Array<out Updatable> = arrayOf()

    /**
     * @property update The code section to execute on each frame update.
     */
    var update: Update? = null

    /**
     * @property finish The code section to execute immediately following the
     *                  cancellation of this shader.
     */
    var finish: Update? = null

    /**
     * @property cancel The conditional code snippet that describes what conditions
     *                  will cause this shader to be cancelled.
     */
    open var cancel: Cancel = { false }

    /**
     * Assigns the update code snippet for this shader and links any
     * external co-updates to run along side this method.
     *
     * @param objects The collection of objects to update alongside
     *                the shader script.
     * @param block   The code snippet to run on each update frame.
     */
    fun update(vararg objects: Updatable, block: Update? = null) {
        this.objects = objects
        update = block
    }

    /**
     * This function is called internally to jump-start the shader script.
     * All lifecycle activities are handled by this in conjunction with
     * the server's [Scheduler][org.bukkit.scheduler.BukkitScheduler] system.
     */
    protected fun update(rate: Long = 0) {
        taskId = schedule.scheduleSyncRepeatingTask(plugin, {
            if (cancel()) {
                schedule.cancelTask(taskId)
                finish?.invoke(this)
            }
            try {
                update?.invoke(this)
                objects.forEach(Updatable::update)
            } catch (e: Exception) {
                e.printStackTrace()
                schedule.cancelTask(taskId)
                finish?.invoke(this)
            }
            framecount++
        }, 0L, rate)
    }

    /**
     * Assigns the code block that runs immediately following the cancellation
     * of this shader.
     *
     * @param block The code snippet to execute upon this shader's cancellation.
     */
    fun finish(block: Update) {
        finish = block
    }

    /**
     * Assigns the code snippet that determines the conditions for which this
     * shader will be cancelled for.
     *
     * @param block The code snippet to use for setting this shader's cancel conditions.
     */
    fun cancel(block: Cancel) {
        cancel = block
    }

    /**
     * A DSL function that limits the execution of a subsection of the shader
     * to only run after every X number of frames.
     *
     * @param frames The number of frames to wait before executing this
     *               section of code.
     * @param block  The shader subsection to execute every X frames.
     */
    fun every(frames: Int, block: Update) {
        if (framecount % frames == 0 && framecount != 0) block(this)
    }

    internal companion object {

        internal fun start(location: Location?, rate: Long, shader: Shader.() -> Unit) = Shader().apply {
            location?.let { this.location = it }
            shader(this) // Construct the shader script
            update(rate) // Update the shader at the given rate
        }
    }
}

/**
 * This functions as the user-facing constructor for [Shader] objects. For an in-depth
 * explanation to the shader system, please check the constructor documentation.
 *
 * @param location The optional initializer for this shader's location.
 * @param rate     The number of ticks to wait between running frame updates.
 * @param shader   The shader script to run for this animation.
 *
 * @see Shader
 */
fun shader(location: Location? = null, rate: Long = 0, shader: Update) =
    Shader.start(location, rate, shader)
