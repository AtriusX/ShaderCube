package xyz.atrius.shadercube.shape

import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.util.Vector
import xyz.atrius.shadercube.util.vec

class Line(
    override var location: Location,
                 point2  : Location,
    override var particle: Particle    = Particle.REDSTONE,
                 vertexes: Int         = 100,
    override val block   : Style<Line> = {}
) : Shape<Line> {

    override val size: Vector = point
        .subtract(point2.toVector())
        .multiply(-1.0 / vertexes)

    val midpoint: Vector = point
        .subtract(point2.toVector())
        .divide((-2).vec)

    override val points: Array<Vector> = Array(vertexes) { point }

    init {
        val pos = point
        for (i in points.indices) {
            points[i] = pos
            particle(particle, pos) {
                block(Data(point, this@Line))
            }
            pos.add(size)
        }
    }
}