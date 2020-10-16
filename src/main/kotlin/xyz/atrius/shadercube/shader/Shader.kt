package xyz.atrius.shadercube.shader

import org.bukkit.Bukkit
import org.bukkit.Location
import xyz.atrius.shadercube.Spatial
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

    open var cancel: Cancel = { false }

    fun update(vararg objects: Updatable, block: Update) {
        this.objects = objects
        update = block
    }

    protected fun update(rate: Long = 0) {
        taskId = schedule.scheduleSyncRepeatingTask(plugin, {
            if (cancel())
                schedule.cancelTask(taskId)
            try {
                update?.invoke(this)
            } catch(e: Exception) {
                Bukkit.getLogger().warning(e.message)
                schedule.cancelTask(taskId)
            }
            objects.forEach(Updatable::update)
            framecount++
        }, 0L, rate)
    }

    fun cancel(block: Cancel) {
        cancel = block
    }

    fun every(frames: Int, block: Update) {
        if (framecount % frames == 0 && framecount != 0) block(this)
    }

    companion object {

        internal fun start(rate: Long, shader: Shader.() -> Unit) = Shader().apply {
            shader(this) // Construct the shader script
            update(rate) // Update the shader at the given rate
        }
    }
}

fun shader(rate: Long = 0, shader: Update) =
    Shader.start(rate, shader)