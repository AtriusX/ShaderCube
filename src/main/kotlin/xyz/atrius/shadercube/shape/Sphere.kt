package xyz.atrius.shadercube.shape

import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.util.Vector
import xyz.atrius.shadercube.data.Style
import xyz.atrius.shadercube.shader.Shader
import xyz.atrius.shadercube.shape.Circle.Companion.generate
import xyz.atrius.shadercube.util.radians
import xyz.atrius.shadercube.util.vec

class Sphere(
    override var location: Location,
    override var particle: Particle      = Particle.REDSTONE,
    private val  scale   : Double        = 1.0,
    private val  rings   : Int           = 16,
    private val  segments: Int           = rings,
    override val style   : Style<Sphere> = {}
) : Shape<Sphere>() {
    override val size: Vector = scale.vec

    init {
        vertexes()
        update()
    }

    override fun vertexes() {
        val angle = (360.0 / segments).radians
        for (i in 1..rings) generate(scale, segments).forEach {
            vertices.add(it.apply {
                rotateAroundY(angle * (i / 2.0)) // Offset rings so it looks less regular
                rotateAroundX(angle * i)         // Rotate the rings to form a sphere
                rotateAroundZ(90.radians)        // Correct direction so sphere has poles facing Y axis
            })
        }
    }
}

fun Shader.sphere(
    point   : Vector        = this.point,
    particle: Particle      = Particle.REDSTONE,
    size    : Double        = 1.0,
    rings   : Int           = 16,
    segments: Int           = 32,
    block   : Style<Sphere> = {}
) = Sphere(point.toLocation(world), particle, size, rings, segments, block)
