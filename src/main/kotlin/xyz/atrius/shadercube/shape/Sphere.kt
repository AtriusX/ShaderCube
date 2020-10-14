package xyz.atrius.shadercube.shape

import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.util.Vector
import xyz.atrius.shadercube.util.radians
import xyz.atrius.shadercube.util.vec

class Sphere(
    override var location: Location,
    override var particle: Particle      = Particle.REDSTONE,
                 size    : Double        = 1.0,
                 rings   : Int           = 16,
                 segments: Int           = 32,
    override val block   : Style<Sphere> = {}
) : Shape<Sphere> {

    constructor(
        point   : Location,
        particle: Particle      = Particle.REDSTONE,
        size    : Double,
        vertexes: Int,
        block   : Style<Sphere> = {}
    ) : this(point, particle, size, vertexes, vertexes, block)

    override val size  : Vector        = size.vec
    override val points: Array<Vector> = Array(segments * rings) { point }

    init {
        val angle = (360.0 / segments).radians
        for (i in 1..rings)
            Circle(location, particle, size, segments) { (v) ->
                location(v
                    .rotateY(angle * (i / 2.0)) // Offset rings so it looks less regular
                    .rotateX(angle * i)         // Rotate the rings to form a sphere
                    .rotateZ(90.radians)        // Correct direction so sphere has poles facing Y axis
                )
                block(Data(v, this@Sphere))
            }

    }
}