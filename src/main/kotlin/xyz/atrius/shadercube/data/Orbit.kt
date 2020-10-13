package xyz.atrius.shadercube.data

import org.bukkit.Location
import org.bukkit.util.Vector
import xyz.atrius.shadercube.shader.Shader
import xyz.atrius.shadercube.util.radians

class Orbit(
    override var point: Location,
    private  val size : Double                  = 1.0,
    private  val rate : Double                  = 1.0,
    private  val block: Shader.(Vector) -> Unit = {}
) : Shader() {

    var offset: Double = 0.0

    operator fun invoke() = block(
        point.clone().toVector().rotateY(((time / 10000) * rate + offset).radians, size)
    )

    operator fun plusAssign(amt: Int) =
        plusAssign(amt.toDouble())

    operator fun plusAssign(amt: Double) {
        offset += amt
        this()
    }

    operator fun inc() = also { this += 1 }

    operator fun minusAssign(amt: Int) =
        minusAssign(amt.toDouble())

    operator fun minusAssign(amt: Double) {
        offset -= amt
        this()
    }

    operator fun dec() = also { this -= 1 }
}

fun Shader.orbit(
    point: Location = this.point,
    size : Double   = 1.0,
    rate : Double   = 1.0,
    block: Shader.(Vector) -> Unit
) = Orbit(point, size, rate, block)
