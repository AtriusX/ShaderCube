package xyz.atrius.shadercube.shader

import org.bukkit.Location
import xyz.atrius.shadercube.Spatial
import xyz.atrius.shadercube.util.plugin
import xyz.atrius.shadercube.util.schedule

class Shader : Spatial {

    override lateinit var point: Location

    val time: Long
        get() = System.currentTimeMillis()

    var centered: Boolean = false

    var setup: () -> Unit = {}

    var update: () -> Unit = {}

    fun setup(block: () -> Unit) {
        setup = block
    }

    fun update(block: () -> Unit) {
        update = block
    }
}

fun shader(rate: Long = 1, shader: Shader.() -> Unit) = Shader().apply {
    // Construct the shader script
    shader(this)
    // Run the setup script
    setup()
    // Setup update loop
    schedule.scheduleSyncRepeatingTask(plugin, { update() }, 0L, rate)
}
