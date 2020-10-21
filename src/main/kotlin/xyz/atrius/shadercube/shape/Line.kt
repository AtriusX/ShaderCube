@file:Suppress("unused", "MemberVisibilityCanBePrivate")
package xyz.atrius.shadercube.shape

import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.util.Vector
import xyz.atrius.shadercube.data.Coordinate
import xyz.atrius.shadercube.data.Style
import xyz.atrius.shadercube.shader.Shader
import xyz.atrius.shadercube.util.abs

/**
 * One of the basic primitive shapes provided with this library. This shape is considered
 * special because of its simplicity as well as because its main vertex generator is used
 * elsewhere in the library for other shapes.
 *
 * @constructor       Creates a line instance.
 * @property location The starting point for this line
 * @property point2   The ending point for this line.
 * @property particle The particle effect used in this shape's update cycle.
 * @property vertexes The number of vertices generated for this line.
 * @property style    The style block associated with this shape.
 *
 * @see Shape
 * @see Triangle
 * @see Cube
 * @see Polygon
 * @see Star
 */
class Line(
    override var location: Location,
    private  val point2  : Location    = location,
    override var particle: Particle    = Particle.REDSTONE,
             val vertexes: Int         = 100,
    override val style   : Style<Line> = {}
) : Shape<Line>() {

    override val size: Vector = abs(location.subtract(point2).toVector())

    /**
     * @property midpoint The point on the line that is equal distance
     *                    away from both ends of the line.
     */
    val midpoint: Vector
        get() = location.toVector().midpoint(point2.toVector())

    init {
        vertexes()
        update()
    }

    override fun vertexes() {
        vertices.addAll(generate(point, point2.toVector(), vertexes))
    }

    companion object {

        /**
         * General vertex generator for generating a line of vertices.
         *
         * @param point    The starting point of this line.
         * @param point2   The ending point of this line.
         * @param vertexes The number of vertices to generate for this line.
         * @return         A list of coordinates that fall along the generated line.
         */
        fun generate(point: Vector, point2: Vector, vertexes: Int): List<Coordinate> {
            val vertices  = mutableListOf<Coordinate>()
            val direction = getDirection(point, point2, vertexes)
            val pos = point.clone()
            repeat(vertexes) {
                vertices.add(Coordinate(point, pos.add(direction)))
            }
            return vertices
        }

        private fun getDirection(point: Vector, point2: Vector, vertexes: Int) = point.clone()
                .subtract(point2).multiply(-1.0 / vertexes)
    }
}


/**
 * A small DSL function for generating lines within a shader script.
 *
 * @property point    The starting point for this line
 * @property point2   The ending point for this line.
 * @property particle The particle effect used in this shape's update cycle.
 * @property vertexes The number of vertices generated for this line.
 * @property block    The style block associated with this shape.
 */
fun Shader.line(
    point   : Vector      = this.point,
    point2  : Vector      = this.point,
    particle: Particle    = Particle.REDSTONE,
    vertexes: Int         = 100,
    block   : Style<Line> = {}
) = Line(point.toLocation(world), point2.toLocation(world), particle, vertexes, block)
