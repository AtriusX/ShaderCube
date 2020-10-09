package xyz.atrius.shadercube.shader

import com.destroystokyo.paper.ParticleBuilder
import org.bukkit.*
import org.bukkit.util.Vector
import xyz.atrius.shadercube.KotlinPlugin
import xyz.atrius.shadercube.Spatial
import xyz.atrius.shadercube.util.schedule
import kotlin.math.abs

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

fun shader(plugin: KotlinPlugin, rate: Long = 1, shader: Shader.() -> Unit) = Shader().apply {
    // Construct the shader script
    shader(this)
    // Run the setup script
    setup()
    // Setup update loop
    schedule.scheduleSyncRepeatingTask(plugin, { update() }, 0L, rate)
}
