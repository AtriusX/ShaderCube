package xyz.atrius.shadercube.shape

import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.util.Vector

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

    override val points: Array<Vector> = Array(vertexes) { point.toVector() }

    init {
        val size = size.clone()
        points.forEachIndexed { i, point ->
            particle(particle, point.add(size.add(Vector(i, i, i)))) {
                block(this@Line, point)
            }
        }
    }
}