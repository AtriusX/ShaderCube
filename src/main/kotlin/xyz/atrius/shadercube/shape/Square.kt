package xyz.atrius.shadercube.shape

import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.util.Vector
import xyz.atrius.shadercube.data.Coordinate
import xyz.atrius.shadercube.data.Style
import xyz.atrius.shadercube.shader.Shader
import xyz.atrius.shadercube.util.iterator
import xyz.atrius.shadercube.util.vec2d

class Square(
    override var location: Location,
    override var particle: Particle      = Particle.REDSTONE,
    override val size    : Vector        = 1.vec2d,
    private  val step    : Int           = 5,
    private  val hollow  : Boolean       = true,
    private  val centered: Boolean       = true,
    private  val corners : Boolean       = false,
    override val style   : Style<Square> = {}
) : Shape<Square>() {

    private val w     = size.x
    private val h     = size.z
    private val wSize = (w * step).toInt()
    private val hSize = (h * step).toInt()

    override fun vertexes(): Array<Coordinate> {
        val vertices = mutableListOf<Coordinate>()
        for((x, z) in range(wSize) to range(hSize)) if (!hollow || isEdge(x, z)) {
            vertices += Coordinate(point, Vector(
                adjust(point.x, x.toDouble(), w), point.y, adjust(point.z, z.toDouble(), h)
            ))
        }
        return vertices.toTypedArray()
    }

    private fun range(size: Int): IntProgression =
        0..size step if (corners) size else 1

    private fun isEdge(x: Int, z: Int): Boolean =
        x in listOf(0, wSize) || z in listOf(0, hSize)

    private fun adjust(value: Double, other: Double, size: Double): Double =
        value + other / step - if (centered) size / 2 else 0.0
}

fun Shader.square(
    point   : Vector        = this.point,
    particle: Particle      = Particle.REDSTONE,
    size    : Vector        = 1.vec2d,
    step    : Int           = 5,
    hollow  : Boolean       = true,
    center  : Boolean       = true,
    corners : Boolean       = false,
    block   : Style<Square> = {}
) = Square(point.toLocation(world), particle, size, step, hollow, center, corners, block)
