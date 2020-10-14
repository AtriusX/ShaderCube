package xyz.atrius.shadercube.shape

import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.util.Vector
import xyz.atrius.shadercube.util.radians
import xyz.atrius.shadercube.util.vec2d

class Polygon(
    override var location: Location,
    override var particle: Particle       = Particle.REDSTONE,
                 size    : Double         = 1.0,
                 faces   : Int            = 3,
                 vertexes: Int            = 25,
    override val block   : Style<Polygon> = {}
) : Shape<Polygon> {
    override val size: Vector = size.vec2d
    override val points: Array<Vector> = Array(faces) { point }

    init {
        val angle = (360.0 / faces).radians
        points.forEachIndexed { i, p ->
            p.rotateY(angle * i, size)
        }
        points.forEachIndexed { i, p ->
            Line(
                p.toLocation(world), points[(i + 1) % faces].toLocation(world), particle, vertexes
            ) { (v) ->
                block(Data(v, this@Polygon))
            }
        }
    }
}