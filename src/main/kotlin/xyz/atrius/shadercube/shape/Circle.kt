package xyz.atrius.shadercube.shape

import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.util.Vector
import xyz.atrius.shadercube.data.Coordinate
import xyz.atrius.shadercube.data.Style
import xyz.atrius.shadercube.shader.Shader
import xyz.atrius.shadercube.util.radians
import xyz.atrius.shadercube.util.vec2d

class Circle(
    override var location: Location,
    override var particle: Particle      = Particle.REDSTONE,
    private  val radius  : Double        = 1.0,
    private  val vertexes: Int           = 32,
    override val style   : Style<Circle> = {}
) : Shape<Circle>() {
    override val size: Vector = radius.vec2d

    init {
        vertexes()
        update()
    }

    override fun vertexes() {
        vertices.addAll(generate(radius, vertexes))
    }

    companion object {

        fun Shape<*>.generate(radius: Double, vertexes: Int): List<Coordinate> {
            val vertices = mutableListOf<Coordinate>()
            val angle = (360.0 / vertexes).radians
            repeat(vertexes) {
                vertices.add(Coordinate(point, point.rotateY(angle * it, radius)))
            }
            return vertices
        }
    }
}

fun Shader.circle(
        point   : Vector        = this.point,
        particle: Particle      = Particle.REDSTONE,
        radius  : Double        = 1.0,
        vertexes: Int           = 32,
        block   : Style<Circle> = {}
) = Circle(point.toLocation(world), particle, radius, vertexes, block)
