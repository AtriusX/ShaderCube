package xyz.atrius.shadercube.shader

import org.bukkit.Location
import xyz.atrius.shadercube.Spatial
import xyz.atrius.shadercube.util.plugin
import xyz.atrius.shadercube.util.schedule

typealias Update =
    Shader.() -> Unit

typealias Cancel =
    Shader.() -> Boolean

class Shader : Spatial {

    override lateinit var point: Location

    val time: Long
        get() = System.currentTimeMillis()

    var update: (() -> Unit)? = null

    var cancel: () -> Boolean = { false }

    var taskId: Int = -1

    fun update(block: () -> Unit) {
        update = block
    }

    fun cancel(block: () -> Boolean) {
        cancel = block
    }
}

fun shader(rate: Long = 0, shader: Shader.() -> Unit) = Shader().apply {
    shader(this)        // Construct the shader script
    if (update != null) // Setup update loop
    taskId = schedule.scheduleSyncRepeatingTask(plugin, {
        if (cancel())
            schedule.cancelTask(taskId)
        update?.invoke()
    }, 0L, rate)
}
