package xyz.atrius.shadercube.shader

import org.bukkit.Location
import xyz.atrius.shadercube.Spatial
import xyz.atrius.shadercube.util.plugin
import xyz.atrius.shadercube.util.schedule

typealias Update =
    Shader.() -> Unit

typealias Cancel =
    Shader.() -> Boolean

open class Shader : Spatial {

    override lateinit var point: Location

    val time: Long
        get() = System.currentTimeMillis()

    val start: Long = time

    val elapsed: Long
        get() = time - start

    var framecount: Int = 0
        private set

    private var taskId: Int = -1

    var update: Update? = null

    open var cancel: Cancel = { false }

    fun update(block: Update) {
        update = block
    }

    open fun cancel(block: Cancel) {
        cancel = block
    }

    fun every(frames: Int, block: Update) {
        if (framecount % frames == 0 && framecount != 0) block(this)
    }

    companion object {

        internal fun start(rate: Long = 0, shader: Shader.() -> Unit) = Shader().apply {
            shader(this)        // Construct the shader script
            if (update != null) // Setup update loop
                taskId = schedule.scheduleSyncRepeatingTask(plugin, {
                    if (cancel()) schedule.cancelTask(taskId)
                    update?.invoke(this)
                    framecount++
                }, 0L, rate)
        }
    }
}

fun shader(rate: Long = 0, shader: Update) =
    Shader.start(rate, shader)