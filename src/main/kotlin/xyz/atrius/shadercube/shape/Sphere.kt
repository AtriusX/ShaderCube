package xyz.atrius.shadercube.shape

import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.util.Vector
import xyz.atrius.shadercube.data.Coordinate
import xyz.atrius.shadercube.data.Style
import xyz.atrius.shadercube.shader.Shader
import xyz.atrius.shadercube.util.radians
import xyz.atrius.shadercube.util.vec

//
//import org.bukkit.Location
//import org.bukkit.Particle
//import org.bukkit.util.Vector
//import xyz.atrius.shadercube.shader.Shader
//import xyz.atrius.shadercube.util.radians
//import xyz.atrius.shadercube.util.vec
//
//class Sphere(
//    override var location: Location,
//    override var particle: Particle      = Particle.REDSTONE,
//                 size    : Double        = 1.0,
//                 rings   : Int           = 16,
//                 segments: Int           = rings,
//    override val style   : Style<Sphere> = {}
//) : Shape<Sphere> {
//
//    override val size  : Vector        = size.vec
//    override val vertices: Array<Vector> = Array(segments * rings) { point }
//
//    init {
//        val angle = (360.0 / segments).radians
//        for (i in 1..rings)
//            Circle(location, particle, size, segments) { (v) ->
//                location(v
//                    .rotateY(angle * (i / 2.0)) // Offset rings so it looks less regular
//                    .rotateX(angle * i)         // Rotate the rings to form a sphere
//                    .rotateZ(90.radians)        // Correct direction so sphere has poles facing Y axis
//                )
//                block(Data(v, this@Sphere))
//            }
//
//    }
//}
//


class Sphere(
    override var location: Location,
    override var particle: Particle      = Particle.REDSTONE,
    private val  scale   : Double        = 1.0,
    private val  rings   : Int           = 16,
    private val  segments: Int           = rings,
    override val style   : Style<Sphere> = {}
) : Shape<Sphere>() {
    override val size: Vector = scale.vec

    override fun vertexes(): Array<Coordinate> {
        val vertices = mutableListOf<Coordinate>()
        val angle    = (360.0 / segments).radians
        for (i in 1..rings)
            Circle(location, particle, scale, segments).vertices.forEach {
                vertices += it.apply {
                    rotateAroundY(angle * (i / 2.0)) // Offset rings so it looks less regular
                    rotateAroundX(angle * i)         // Rotate the rings to form a sphere
                    rotateAroundZ(90.radians)        // Correct direction so sphere has poles facing Y axis
                }
            }
        return vertices.toTypedArray()
    }
}

fun Shader.sphere(
    point   : Vector        = this.point,
    particle: Particle      = Particle.REDSTONE,
    size    : Double        = 1.0,
    rings   : Int           = 16,
    segments: Int           = 32,
    block   : Style<Sphere> = {}
) = Sphere(point.toLocation(world), particle, size, rings, segments, block)