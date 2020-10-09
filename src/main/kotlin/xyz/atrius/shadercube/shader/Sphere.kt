package xyz.atrius.shadercube.shader

import com.destroystokyo.paper.ParticleBuilder
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.util.Vector
import xyz.atrius.shadercube.shape.Circle
import xyz.atrius.shadercube.shape.Shape
import xyz.atrius.shadercube.util.radians

class Sphere(
    override var point   : Location,
    override var particle: Particle                                 = Particle.REDSTONE,
                 size    : Double                                   = 1.0,
                 rings   : Int                                      = 16,
                 segments: Int                                      = 32,
                 block   : ParticleBuilder.(Sphere, Vector) -> Unit = { _, _ -> }
) : Shape {

    constructor(
            point   : Location,
            particle: Particle,
            size    : Double,
            vertexes: Int,
            block   : ParticleBuilder.(Sphere, Vector) -> Unit = { _, _ -> }
    ) : this(point, particle, size, vertexes, vertexes, block)

    override val size  : Vector        = Vector(size, size, size)
    override val points: Array<Vector> = Array(segments * rings) { point.toVector() }

    init {
        val angle = (360.0 / segments).radians
        for (i in 1..rings)
            Circle(point, particle, size, segments) { _, v ->
                location(v
                    .rotateY(angle * (i / 2.0)) // Offset rings so it looks less regular
                    .rotateX(angle * i)         // Rotate the rings to form a sphere
                    .rotateZ(90.radians)        // Correct direction so sphere has poles facing Y axis
                )
                block(this@Sphere, v)
            }

    }
}