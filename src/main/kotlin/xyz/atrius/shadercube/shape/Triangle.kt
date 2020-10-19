package xyz.atrius.shadercube.shape

import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.util.Vector
import xyz.atrius.shadercube.data.Coordinate
import xyz.atrius.shadercube.data.Style
import xyz.atrius.shadercube.shader.Shader

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

    override fun vertexes(): Array<Coordinate> {
        val vertices = mutableListOf<Coordinate>()

        val halfW  = base / 2
        val halfH  = height / 2
        val coords = arrayOf(
            Vector(point.x, point.y, point.z + halfH),
            Vector(point.x - halfW - skew, point.y, point.z - halfH),
            Vector(point.x + halfW - skew, point.y, point.z - halfH)
        )
        for (i in coords.indices) {
            vertices += Line(
                coords[i].toLocation(world),
                coords[(i + 1) % coords.size].toLocation(world),
                particle, vertexes
            ).vertices
        }
        return vertices.toTypedArray()
    }
}

fun Shader.triangle(
    point   : Vector          = this.point,
    particle: Particle        = Particle.REDSTONE,
    base    : Double          = 1.0,
    height  : Double          = 1.0,
    skew    : Double          = 0.0,
    vertexes: Int             = 25,
    block   : Style<Triangle> = {}
) = Triangle(point.toLocation(world), particle, base, height, skew, vertexes, block)
