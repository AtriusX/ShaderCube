package xyz.atrius.shadercube.shape

import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.util.Vector
import xyz.atrius.shadercube.data.Style
import xyz.atrius.shadercube.shader.Shader

/**
 * This is a shape implementation for generating triangles within a shader script.
 * This shape relies on the [line generation][Line.generate] algorithm for its
 * processing.
 *
 * @constructor       Generate a star shape.
 * @property location The location this shape is generated at.
 * @property particle The particle used in this shape's update cycle.
 * @property base     The size of the base of the triangle.
 * @property height   The overall height of the triangle.
 * @property skew     The amount of skew applied to the top of the triangle.
 * @property vertexes The number of vertexes to generate for each side.
 * @property style    The style block associated with this shape.
 *
 * @see Shape
 * @see Line.generate
 */
class Triangle(
    override var location: Location,
    override var particle: Particle        = Particle.REDSTONE,
    private  val base    : Double          = 1.0,
    private  val height  : Double          = 1.0,
    private  val skew    : Double          = 0.0,
    private  val vertexes: Int             = 25,
    override val style   : Style<Triangle> = {}
) : Shape<Triangle>() {
    override val size: Vector = Vector(base, 0.0, height)

    init {
        vertexes()
        update()
    }

    override fun vertexes() {
        val halfW  = base / 2
        val halfH  = height / 2
        val coords = arrayOf(
            Vector(point.x, point.y, point.z + halfH),
            Vector(point.x - halfW - skew, point.y, point.z - halfH),
            Vector(point.x + halfW - skew, point.y, point.z - halfH)
        )
        for (i in coords.indices) {
            vertices.addAll(Line.generate(
                coords[i], coords[(i + 1) % coords.size], vertexes
            ))
        }
    }
}

/**
 * A small DSL function for generating triangles within shader scripts.
 *
 * @property point    The location this shape is generated at.
 * @property particle The particle used in this shape's update cycle.
 * @property base     The size of the base of the triangle.
 * @property height   The overall height of the triangle.
 * @property skew     The amount of skew applied to the top of the triangle.
 * @property vertexes The number of vertexes to generate for each side.
 * @property block    The style block associated with this shape.
 *
 * @see Triangle
 */
fun Shader.triangle(
    point   : Vector          = this.point,
    particle: Particle        = Particle.REDSTONE,
    base    : Double          = 1.0,
    height  : Double          = 1.0,
    skew    : Double          = 0.0,
    vertexes: Int             = 25,
    block   : Style<Triangle> = {}
) = Triangle(point.toLocation(world), particle, base, height, skew, vertexes, block)
