package xyz.atrius.shadercube.shader

import org.bukkit.Location
import xyz.atrius.shadercube.data.Spatial
import xyz.atrius.shadercube.data.Updatable
import xyz.atrius.shadercube.util.plugin
import xyz.atrius.shadercube.util.schedule

typealias Update =
    Shader.() -> Unit

typealias Cancel =
    Shader.() -> Boolean

open class Shader protected constructor(): Spatial {
    override lateinit var location: Location

    val time: Long
        get() = System.currentTimeMillis()

    val start: Long = time

    val elapsed: Long
        get() = time - start

    var framecount: Int = 0
        private set

    private var taskId: Int = -1

    private var objects: Array<out Updatable> = arrayOf()

    var update: Update? = null

    var finish: Update? = null

    open var cancel: Cancel = { false }

    fun update(vararg objects: Updatable, block: Update? = null) {
        this.objects = objects
        update = block
    }

    protected fun update(rate: Long = 0) {
        taskId = schedule.scheduleSyncRepeatingTask(plugin, {
            if (cancel()) {
                schedule.cancelTask(taskId)
                finish?.invoke(this)
            }
            try {
                update?.invoke(this)
                objects.forEach(Updatable::update)
            } catch(e: Exception) {
                e.printStackTrace()
                schedule.cancelTask(taskId)
                finish?.invoke(this)
            }
            framecount++
        }, 0L, rate)
    }

    fun finish(block: Update) {
        finish = block
    }

    fun cancel(block: Cancel) {
        cancel = block
    }

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

fun shader(location: Location? = null, rate: Long = 0, shader: Update) =
    Shader.start(location, rate, shader)