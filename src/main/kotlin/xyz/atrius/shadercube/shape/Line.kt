package xyz.atrius.shadercube.shape

import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.util.Vector
import xyz.atrius.shadercube.data.Coordinate
import xyz.atrius.shadercube.data.Style
import xyz.atrius.shadercube.shader.Shader

class Line(
    override var location: Location,
                 point2  : Location    = location,
    override var particle: Particle    = Particle.REDSTONE,
             val vertexes: Int         = 100,
    override val style   : Style<Line> = {}
) : Shape<Line> {

    override val size: Vector = point
        .subtract(point2.toVector())
        .multiply(-1.0 / vertexes)

    override fun vertexes(): Array<Coordinate> {
        val vertices = mutableListOf<Coordinate>()
        val pos = point
        repeat(vertexes) {
            vertices += Coordinate(point, pos.add(size))
        }
        return vertices.toTypedArray()
    }
}

fun Shader.line(
        point   : Vector      = this.point,
        point2  : Vector      = this.point,
        particle: Particle    = Particle.REDSTONE,
        vertexes: Int         = 100,
        block   : Style<Line> = {}
) = Line(point.toLocation(world), point2.toLocation(world), particle, vertexes, block)
