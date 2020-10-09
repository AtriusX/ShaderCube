package xyz.atrius.shadercube.shader

import com.destroystokyo.paper.ParticleBuilder
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.util.Vector
import xyz.atrius.shadercube.shape.Shape

class Line(
    override var point   : Location,
                 point2  : Location,
                 vertexes: Int                            = 100,
    private  val block   : ParticleBuilder.(Line) -> Unit = {}
) : Shape {

    override val size: Vector = point.toVector()
        .subtract(point2.toVector())
        .multiply(-1.0 / vertexes)

    override val points: Array<Vector> = Array(vertexes) { point.toVector() }

    init {
        val size = size.clone()
        points.forEachIndexed { i, point ->
            particle(Particle.REDSTONE, {
                block(this@Line)
            }, point.add(size.add(Vector(i, i, i))))
        }
    }
}