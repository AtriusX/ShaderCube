package xyz.atrius.shadercube.shape

import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.util.Vector

class Square(
    override var point   : Location,
    override var particle: Particle      = Particle.REDSTONE,
    override val size    : Vector        = Vector(1.0, 0.0, 1.0),
                 step    : Int           = 5,
                 hollow  : Boolean       = true,
                 center  : Boolean       = true,
    override val block   : Style<Square> = { _, _ -> }
) : Shape<Square> {
    override val points: Array<Vector> = Array(size.blockX * size.blockZ) { point.toVector() }

    init {
        val w = size.x
        val h = size.z
        val wSize = (w * step).toInt()
        val hSize = (h * step).toInt()
        for (x in 0..wSize)
            for (z in 0..hSize)
                if (!hollow || (x in listOf(0, wSize) || z in listOf(0, hSize))) {
                    val pos = Vector(
                        point.x + x.toDouble() / step - if (center) w / 2 else 0.0,
                        point.y,
                        point.z + z.toDouble() / step - if (center) h / 2 else 0.0
                    )
                    particle(particle, pos) {
                        block(this@Square, pos)
                    }
                }
    }
}