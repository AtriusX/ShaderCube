package xyz.atrius.shadercube.data

import org.bukkit.Location
import org.bukkit.util.Vector
import xyz.atrius.shadercube.Spatial
import xyz.atrius.shadercube.shader.Shader
import xyz.atrius.shadercube.util.radians

typealias OrbitData =
    Spatial.(Data<Orbit>) -> Unit

class Orbit(
    override var location: Location,
             var size : Double    = 1.0,
             var rate : Double    = 1.0,
    private  val block: OrbitData = {}
) : Spatial, Updatable {

    var offset: Double = 0.0

    override fun update() = block(Data(Coordinate(point, point.clone()
        .rotateY(((System.currentTimeMillis() / 10) * rate + offset).radians, size)), this)
    )

    operator fun plusAssign(amt: Int) =
        plusAssign(amt.toDouble())

    operator fun plusAssign(amt: Double) {
        offset += amt
        update()
    }

    operator fun inc() = also { this += 1 }

    operator fun minusAssign(amt: Int) =
        minusAssign(amt.toDouble())

    operator fun minusAssign(amt: Double) {
        offset -= amt
        update()
    }

    operator fun dec() = also { this -= 1 }
}

fun Shader.orbit(
    point: Vector    = this.point,
    size : Double    = 1.0,
    rate : Double    = 1.0,
    block: OrbitData = {}
) = Orbit(point.toLocation(world), size, rate, block)
