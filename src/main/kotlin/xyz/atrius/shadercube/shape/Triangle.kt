package xyz.atrius.shadercube.shape

import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.util.Vector

class Triangle(
    override var point   : Location,
    override var particle: Particle        = Particle.REDSTONE,
                 base    : Double          = 1.0,
                 height  : Double          = 1.0,
                 skew    : Double          = 0.0,
    override val block   : Style<Triangle> = { _, _ -> }
) : Shape<Triangle> {
    override val size: Vector = Vector(base, 0.0, height)
    override val points: Array<Vector> = Array(0) { point.toVector() }

    init {
        val halfW = base / 2
        val halfH = height / 2
        val coords = arrayOf(
            Vector(point.x, point.y, point.z + halfH),
            Vector(point.x - halfW - skew, point.y, point.z - halfH),
            Vector(point.x + halfW - skew, point.y, point.z - halfH)
        )
        for (i in coords.indices) Line(
            coords[i].toLocation(world), coords[(i + 1) % coords.size].toLocation(world)
        ) { _, v ->
            block(this@Triangle, v)
        }
    }
}