package xyz.atrius.shadercube.shape

import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.util.Vector
import xyz.atrius.shadercube.data.Style
import xyz.atrius.shadercube.shader.Shader
import xyz.atrius.shadercube.util.radians
import xyz.atrius.shadercube.util.vec2d

/**
 * This is a shape implementation for generating polygons of any number within of sides
 * within shader scripts. This shape relies on the [line generation][Line.Companion.generate]
 * algorithm for its processing.
 *
 * @constructor       Generates an N-sided polygon.
 * @property location The location this shape is generated at.
 * @property particle The particle used in this shape's update cycle.
 * @property scale    The size at which the polygon is generated at.
 * @property faces    The number of sides that this shape is generated with.
 * @property vertexes The number of vertices to generate for each side.
 * @property style    The style block associated with this shape.
 *
 * @see Shape
 * @see Line.Companion.generate
 */
class Polygon(
    override var location: Location,
    override var particle: Particle       = Particle.REDSTONE,
    private  val scale   : Double         = 1.0,
    private  val faces   : Int            = 3,
    private  val vertexes: Int            = 25,
    override val style   : Style<Polygon> = {}
) : Shape<Polygon>() {
    override val size: Vector = scale.vec2d

    init {
        vertexes()
        update()
    }

    override fun vertexes() {
        val angle = (360.0 / faces).radians
        val temp = mutableListOf<Vector>()
        repeat(faces) {
            temp += point.rotateY(angle * it, scale)
        }
        temp.forEachIndexed { i, p ->
            vertices.addAll(Line.generate(
                p, temp[(i + 1) % faces], vertexes
            ))
        }
    }
}

/**
 * A small DSL function for generating polygons within shader scripts.
 *
 * @property point    The location this shape is generated at.
 * @property particle The particle used in this shape's update cycle.
 * @property scale    The size at which the polygon is generated at.
 * @property faces    The number of sides that this shape is generated with.
 *
 * @see Polygon
 */
fun Shader.polygon(
    point   : Vector         = this.point,
    particle: Particle       = Particle.REDSTONE,
    scale   : Double         = 1.0,
    faces   : Int            = 3,
    vertexes: Int            = 25,
    block   : Style<Polygon> = {}
) = Polygon(point.toLocation(world), particle, scale, faces, vertexes, block)
