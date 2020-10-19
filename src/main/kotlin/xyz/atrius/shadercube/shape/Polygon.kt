package xyz.atrius.shadercube.shape

import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.util.Vector
import xyz.atrius.shadercube.data.Coordinate
import xyz.atrius.shadercube.data.Style
import xyz.atrius.shadercube.shader.Shader
import xyz.atrius.shadercube.util.radians
import xyz.atrius.shadercube.util.vec2d

class Polygon(
    override var location: Location,
    override var particle: Particle           = Particle.REDSTONE,
    private  val scale   : Double             = 1.0,
    private  val faces   : Int                = 3,
    private  val vertexes: Int                = 25,
        override val style   : Style<Polygon> = {}
) : Shape<Polygon>() {
    override val size: Vector = scale.vec2d

    override fun vertexes(): Array<Coordinate> {
        val vertices = mutableListOf<Coordinate>()
        val angle = (360.0 / faces).radians
        val temp = mutableListOf<Vector>()
        repeat(faces) {
            temp += point.rotateY(angle * it, scale)
        }
        temp.forEachIndexed { i, p ->
            vertices += Line(
                p.toLocation(world), temp[(i + 1) % faces].toLocation(world), particle, vertexes
            ).vertices
        }
        return vertices.toTypedArray()
    }
}

fun Shader.polygon(
    point   : Vector         = this.point,
    particle: Particle       = Particle.REDSTONE,
    size    : Double         = 1.0,
    faces   : Int            = 3,
    vertexes: Int            = 25,
    block   : Style<Polygon> = {}
) = Polygon(point.toLocation(world), particle, size, faces, vertexes, block)
