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

    var setup: () -> Unit = {}

    var update: (() -> Unit)? = null

    var cancel: () -> Boolean = { update == null }

    var taskId: Int = -1

    fun update(block: () -> Unit) {
        update = block
    }

    fun cancel(block: () -> Boolean) {
        cancel = block
    }
}

fun shader(rate: Long = 0, shader: Shader.() -> Unit) = Shader().apply {
    // Construct the shader script
    shader(this)
    // Run the setup script
    setup()
    // Setup update loop
    taskId = schedule.scheduleSyncRepeatingTask(plugin, {
        if (cancel())
            schedule.cancelTask(taskId)
        update?.invoke()
    }, 0L, rate)
}
