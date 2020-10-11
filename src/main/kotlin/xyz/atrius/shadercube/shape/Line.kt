package xyz.atrius.shadercube.shape

import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.util.Vector
import xyz.atrius.shadercube.util.vec

class Line(
    override var point   : Location,
                 point2  : Location,
    override var particle: Particle    = Particle.REDSTONE,
                 vertexes: Int         = 100,
    override val block   : Style<Line> = { _, _ ->}
) : Shape<Line> {

    override val size: Vector = point.toVector()
        .subtract(point2.toVector())
        .multiply(-1.0 / vertexes)

    val midpoint: Vector = point.toVector()
        .subtract(point2.toVector())
        .divide((-2).vec)

    override val points: Array<Vector> = Array(vertexes) { point.toVector() }

    init {
        val pos = point
        for (i in points.indices) {
            points[i] = pos.toVector()
            particle(particle, pos.toVector()) {
                block(this@Line, point.toVector())
            }
            pos.add(size)
        }
    }
}