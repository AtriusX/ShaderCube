package xyz.atrius.shadercube.shape

import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.util.Vector
import xyz.atrius.shadercube.data.Coordinate
import xyz.atrius.shadercube.data.Style
import xyz.atrius.shadercube.shader.Shader

class Line(
    override var location: Location,
    private  val point2  : Location    = location,
    override var particle: Particle    = Particle.REDSTONE,
             val vertexes: Int         = 100,
    override val style   : Style<Line> = {}
) : Shape<Line>() {

    override val size: Vector = getDirection(point, point2.toVector(), vertexes)

    val midpoint: Vector =
        location.toVector().midpoint(point2.toVector())

    init {
        vertexes()
        update()
    }

    override fun vertexes() {
        vertices.addAll(generate(point, point2.toVector(), vertexes))
    }

    companion object {

        private fun getDirection(point: Vector, point2: Vector, vertexes: Int) = point.clone()
            .subtract(point2).multiply(-1.0 / vertexes)

        fun generate(point: Vector, point2: Vector, vertexes: Int): List<Coordinate> {
            val vertices  = mutableListOf<Coordinate>()
            val direction = getDirection(point, point2, vertexes)
            val pos = point.clone()
            repeat(vertexes) {
                vertices.add(Coordinate(point, pos.add(direction)))
            }
            return vertices
        }
    }
}

fun Shader.line(
        point   : Vector      = this.point,
        point2  : Vector      = this.point,
        particle: Particle    = Particle.REDSTONE,
        vertexes: Int         = 100,
        block   : Style<Line> = {}
) = Line(point.toLocation(world), point2.toLocation(world), particle, vertexes, block)
