package xyz.atrius.shadercube.shape

import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.util.Vector
import xyz.atrius.shadercube.data.Style
import xyz.atrius.shadercube.shader.Shader
import xyz.atrius.shadercube.util.radians
import xyz.atrius.shadercube.util.vec2d

class Star(
    override var location: Location,
    override var particle: Particle    = Particle.REDSTONE,
    private  val scale   : Double      = 1.0,
    private  val points  : Int         = 5,
    private  val jump    : Int         = 2,
    private  val vertexes: Int         = 25,
    override val style   : Style<Star> = {}
) : Shape<Star>() {

    override val size: Vector = scale.vec2d

    init {
        vertexes()
        update()
    }

    override fun vertexes() {
        val angle = (360.0 / points).radians
        val temp  = mutableListOf<Vector>()
        repeat(points) {
            temp += point.rotateY(angle * it, scale)
        }
        temp.forEachIndexed { i, v ->
            val jumpIndex = (i + jump) % points
            vertices.addAll(Line.generate(
                v, temp[jumpIndex], vertexes
            ))
        }
    }
}

fun Shader.star(
    point   : Vector      = this.point,
    particle: Particle    = Particle.REDSTONE,
    size    : Double      = 1.0,
    points  : Int         = 5,
    jump    : Int         = 2,
    vertexes: Int         = 25,
    block   : Style<Star> = {}
) = Star(point.toLocation(world), particle, size, points, jump, vertexes, block)
