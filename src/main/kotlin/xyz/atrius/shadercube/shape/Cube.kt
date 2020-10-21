package xyz.atrius.shadercube.shape

import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.util.Vector
import xyz.atrius.shadercube.data.Style
import xyz.atrius.shadercube.shader.Shader
import xyz.atrius.shadercube.util.vec

/**
 * This is a shape implementation for generating cubes or prisms within shader
 * scripts. This shape relies on the [line generation][Line.generate] algorithm
 * for its processing.
 *
 * @constructor        Generates a cube shape.
 * @property location  The location this shape is generated at.
 * @property particle  The particle used in this shape's update cycle.
 * @property size      The dimensions of the cube.
 * @property vertexes  The number of vertexes to generate for each edge.
 * @property triangles Whether or not to connect the sides into triangles.
 * @property centered  Whether or not the cube is centered on its location.
 * @property style     The style block associated with this shape.
 *
 * @see Shape
 * @see Line.generate
 */
class Cube(
    override var location : Location,
    override var particle : Particle    = Particle.REDSTONE,
    override val size     : Vector      = 1.vec,
    private  val vertexes : Int         = 25,
    private  val triangles: Boolean     = false,
    private  val centered : Boolean     = true,
    override val style    : Style<Cube> = {}
) : Shape<Cube>() {
    // Calculate the offsets for this shape
    private val offsets: List<Vector> = arrayOf(
        Vector(1, 1, 1), Vector(1, 1, 0),
        Vector(0, 1, 0), Vector(0, 1, 1),
        Vector(1, 0, 1), Vector(1, 0, 0),
        Vector(0, 0, 0), Vector(0, 0, 1)
    ).map {
        it.apply {
            multiply(size).add(point)
            if (centered)
                subtract(size.clone().multiply(0.5))
        }
    }

    init {
        vertexes()
        update()
    }

    override fun vertexes() {
        // Generate the primary edges for the cube
        for (i in 0 until 4) vertices.addAll(
            Line.generate(offsets[i],     offsets[(i + 1) % 4],       vertexes) +
            Line.generate(offsets[i],     offsets[(i + 4) % 8],       vertexes) +
            Line.generate(offsets[i + 4], offsets[4 + ((i + 1) % 4)], vertexes)
        )
        // If triangles are enabled, connect the outer corners to each other
        if (triangles) arrayOf(2, 5, 7).forEachIndexed { i, o ->
            vertices.addAll(
                Line.generate(offsets[0], offsets[o],         vertexes) +
                Line.generate(offsets[6], offsets[o - i - 1], vertexes)
            )
        }
    }
}

/**
 * A small DSL function for generating cubes within shader scripts.
 *
 * @property point     The location this shape is generated at.
 * @property particle  The particle used in this shape's update cycle.
 * @property size      The dimensions of the cube.
 * @property vertexes  The number of vertexes to generate for each edge.
 * @property triangles Whether or not to connect the sides into triangles.
 * @property centered  Whether or not the cube is centered on its location.
 * @property block     The style block associated with this shape.
 *
 * @see Cube
 */
fun Shader.cube(
    point    : Vector      = this.point,
    particle : Particle    = Particle.REDSTONE,
    size     : Vector      = 1.vec,
    vertexes : Int         = 25,
    triangles: Boolean     = false,
    centered : Boolean     = true,
    block    : Style<Cube> = {}
) = Cube(point.toLocation(world), particle, size, vertexes, triangles, centered, block)
