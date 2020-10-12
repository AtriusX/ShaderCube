package xyz.atrius.shadercube.shader

import org.bukkit.Location
import xyz.atrius.shadercube.Spatial
import xyz.atrius.shadercube.util.plugin
import xyz.atrius.shadercube.util.schedule

typealias Setup =
    Shader.() -> Unit

typealias Update =
    Shader.() -> Unit

typealias Cancel =
    Shader.() -> Boolean

class Shader : Spatial {

    override lateinit var point: Location

    val time: Long
        get() = System.currentTimeMillis()

    var setup: () -> Unit = {}

    var update: () -> Unit = {}

    var cancel: () -> Boolean = { false }

    var taskId: Int = -1

    fun setup(block: () -> Unit) {
        setup = block
    }

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
        update()
    }, 0L, rate)
}
