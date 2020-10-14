package xyz.atrius.shadercube.shape

import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.util.Vector
import xyz.atrius.shadercube.shader.Shader
import xyz.atrius.shadercube.util.radians
import xyz.atrius.shadercube.util.vec2d

class Star(
    override var location: Location,
    override var particle: Particle    = Particle.REDSTONE,
                 size    : Double      = 1.0,
                 points  : Int         = 5,
                 jump    : Int         = 2,
                 vertexes: Int         = 25,
    override val block   : Style<Star> = {}
) : Shape<Star> {
    override val size: Vector      = size.vec2d
    override val points: Array<Vector> = Array(points) { point }

    init {
        val angle = (360.0 / points).radians
        this.points.forEachIndexed { i, v ->
            v.rotateY(angle * i, size)
        }
        this.points.forEachIndexed { i, v ->
            val jumpIndex = (i + jump) % points
            Line(
                v.toLocation(world), this.points[jumpIndex].toLocation(world), particle, vertexes
            ) { (p) ->
                block(Data(p, this@Star))
            }
        }
    }
}

fun Shader.star(
    point   : Vector      = this.point,
    particle: Particle    = Particle.REDSTONE,
    size    : Double      = 1.0,
    points  : Int         = 5,
    jump    : Int         = 2,
    vertexes: Int         = 25,
    block   : Style<Star> = {}
) = Star(point.toLocation(world), particle, size, points, jump, vertexes, block)