package xyz.atrius.shadercube.shape

import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.util.Vector
import xyz.atrius.shadercube.data.Coordinate
import xyz.atrius.shadercube.data.Style
import xyz.atrius.shadercube.shader.Shader
import xyz.atrius.shadercube.util.radians
import xyz.atrius.shadercube.util.vec2d

/**
 * One of the basic primitive shapes provided with this library. This shape is considered
 * special because of its simplicity as well as because its main vertex generator is used
 * elsewhere in the library for other shapes.
 *
 * @constructor       Creates a circle instance.
 * @property location The location that the circle is centered on.
 * @property particle The particle effect used in this shape's update cycle.
 * @property radius   The distance away from the center that the circle generates at.
 * @property vertexes The number of vertices generated for this circle.
 * @property style    The style block associated with this shape.
 *
 * @see Shape
 * @see Sphere
 */
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
        // Calls this shape's public generator function
        vertices.addAll(generate(radius, vertexes))
    }

    companion object {

        /**
         * General vertex generator for generating a ring of vertices.
         *
         * @param radius   The distance from the center that the circle generates at.
         * @param vertexes The number of vertices to generate for this circle.
         */
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

/**
 * A small DSL function for generating circles within a shader script.
 *
 * @receiver          The shader script that this shape is attached to.
 * @property point    The location that the circle is centered on.
 * @property particle The particle effect used in this shape's update cycle.
 * @property radius   The distance away from the center that the circle generates at.
 * @property vertexes The number of vertices generated for this circle.
 * @property block    The style block associated with this shape.
 *
 * @see Circle
 */
fun Shader.circle(
    point   : Vector        = this.point,
    particle: Particle      = Particle.REDSTONE,
    radius  : Double        = 1.0,
    vertexes: Int           = 32,
    block   : Style<Circle> = {}
) = Circle(point.toLocation(world), particle, radius, vertexes, block)
