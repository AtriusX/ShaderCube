package xyz.atrius.shadercube.shape

import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.util.Vector
import xyz.atrius.shadercube.util.component1
import xyz.atrius.shadercube.util.component2
import xyz.atrius.shadercube.util.vec

class Cube(
    override var point   : Location,
    override var particle: Particle    = Particle.REDSTONE,
    override val size    : Vector      = 1.vec,
                 step    : Int         = 5,
                 hollow  : Boolean     = true,
                 center  : Boolean     = true,
    override val block   : Style<Square> = {}
) : Shape<Square> {
    override val points: Array<Vector> = arrayOf()

    init {
        val (_, h) = size
        val hSize = h * step
        val p = point.clone()
        if (center) p.subtract(0.0, h / 2.0, 0.0)
        for (y in 0..hSize) Square(
            p, particle, size, step, if (y in listOf(0, hSize)) hollow else true, center, y !in listOf(0, hSize) && hollow
        ) { (v, s) ->
            block(Data(v, s))
        }.also {
            p.add(0.0, 1.0 / step, 0.0)
        }
    }
}