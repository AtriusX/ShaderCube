package xyz.atrius.shadercube.shader

import com.destroystokyo.paper.ParticleBuilder
import org.bukkit.*
import org.bukkit.util.Vector
import xyz.atrius.shadercube.KotlinPlugin
import kotlin.math.cos
import kotlin.math.sin

interface Shader {

    val time: Long
        get() = System.currentTimeMillis()

    var position: Location

    var rotation: Vector

    var size: Vector

    var centered: Boolean

    var setup: () -> Unit

    var update: () -> Unit

    fun Vector.rotateX(angle: Double, offset: Double = 0.0, center: Vector = position.toVector()) =
        subtract(center).add(Vector(0.0, offset, offset)).rotateAroundX(angle).add(center)

    fun Vector.rotateY(angle: Double, offset: Double = 0.0, center: Vector = position.toVector()) =
        subtract(center).add(Vector(offset, 0.0, offset)).rotateAroundY(angle).add(center)

    fun Vector.rotateZ(angle: Double, offset: Double = 0.0, center: Vector = position.toVector()) =
        subtract(center).add(Vector(offset, offset, 0.0)).rotateAroundZ(angle).add(center)
}

operator fun Vector.component1() = blockX

operator fun Vector.component2() = blockY

operator fun Vector.component3() = blockZ

fun shader(plugin: KotlinPlugin, shader: Shader.() -> Unit): Shader {
    val shade = object : Shader {
        override var position: Location =
            Location(null, 0.0, 0.0, 0.0)
        override var rotation: Vector = Vector(0.0, 0.0, 0.0)
        override var size: Vector = Vector(5.0, 0.0, 5.0)
        override var centered: Boolean = false
        override var setup  = {}
        override var update = {}
    }
    shader(shade)
    shade.setup()
    Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, {
        shade.update()
    }, 0L, 1L)
    return shade
}

fun Shader.setup(block: () -> Unit) {
    setup = block
}

fun Shader.update(block: () -> Unit) {
    update = block
}

fun Shader.particle(particle: Particle, block: ParticleBuilder.() -> Unit) {
    val builder = ParticleBuilder(particle).location(position)
    block(builder)
    builder.spawn()
}

interface Shape {
    val center: Vector

    val points: Array<Vector>
}