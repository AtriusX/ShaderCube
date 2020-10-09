package xyz.atrius.shadercube.shape

import com.destroystokyo.paper.ParticleBuilder
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.util.Vector
import xyz.atrius.shadercube.util.radians

class Circle(
    override var point   : Location,
    override var particle: Particle                                 = Particle.REDSTONE,
                 size    : Double                                   = 1.0,
                 vertexes: Int                                      = 32,
             var block   : ParticleBuilder.(Circle, Vector) -> Unit = { _, _ -> }
) : Shape {
    override val size  : Vector = Vector(0.0, size, 0.0)
    override val points: Array<Vector> = Array(vertexes) { center.toVector() }

    init {
        val angle = (360.0 / vertexes).radians
        points.forEachIndexed { i, point ->
            point.rotateY(angle * i, this.size.y)
            particle(
                particle, { block(this@Circle, point) }, point
            )
        }
    }
}