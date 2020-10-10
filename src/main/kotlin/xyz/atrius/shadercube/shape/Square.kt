package xyz.atrius.shadercube.shape

import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.util.Vector
import xyz.atrius.shadercube.util.vec2d

class Square(
    override var point   : Location,
    override var particle: Particle      = Particle.REDSTONE,
    override val size    : Vector        = 1.vec2d,
                 step    : Int           = 5,
                 hollow  : Boolean       = true,
                 center  : Boolean       = true,
                 corners : Boolean       = false,
    override val block   : Style<Square> = { _, _ -> }
) : Shape<Square> {
    override val points: Array<Vector> = Array(size.blockX * size.blockZ) { point.toVector() }

    init {
        val w = size.x
        val h = size.z
        val wSize = (w * step).toInt()
        val hSize = (h * step).toInt()
        for (x in 0..wSize step if (corners) wSize else 1)
            for (z in 0..hSize step if (corners) hSize else 1)
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