package xyz.atrius.shadercube.shader

import com.destroystokyo.paper.ParticleBuilder
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.util.Vector
import xyz.atrius.shadercube.shape.Shape

class Line(
    override var point   : Location,
                 point2  : Location,
    override var particle: Particle                       = Particle.REDSTONE,
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
            particle(particle, point.add(size.add(Vector(i, i, i)))) {
                block(this@Line)
            }
        }
    }
}