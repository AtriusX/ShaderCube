package xyz.atrius.shadercube.shader

import org.bukkit.Location
import xyz.atrius.shadercube.Spatial
import xyz.atrius.shadercube.util.plugin
import xyz.atrius.shadercube.util.schedule

typealias Setup =
    Shader.() -> Unit

typealias Update =
    Shader.() -> Unit

class Shader : Spatial {

    override lateinit var point: Location

    val time: Long
        get() = System.currentTimeMillis()

    var setup: () -> Unit = {}

    var update: () -> Unit = {}

    var taskId: Int = -1

    fun setup(block: () -> Unit) {
        setup = block
    }

    fun update(block: () -> Unit) {
        update = block
    }

    fun cancel() =
        schedule.cancelTask(taskId)
}

fun shader(rate: Long = 0, shader: Shader.() -> Unit) = Shader().apply {
    // Construct the shader script
    shader(this)
    // Run the setup script
    setup()
    // Setup update loop
    taskId = schedule.scheduleSyncRepeatingTask(plugin, { update() }, 0L, rate)
}
