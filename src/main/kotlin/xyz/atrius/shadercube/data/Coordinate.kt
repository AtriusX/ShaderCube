package xyz.atrius.shadercube.data

import org.bukkit.Location
import org.bukkit.util.Vector

class Coordinate(
    var center: Vector,
        worldX: Double,
        worldY: Double,
        worldZ: Double
) : Vector(worldX, worldY, worldZ) {

    constructor(center: Vector, relative: Vector) : this(center, relative.x, relative.y, relative.z)

    val relative: Vector = Vector(x - center.x, y - center.y, z - center.z)

    val relX: Double
        get() = relative.x

    val relY: Double
        get() = relative.y

    val relZ: Double
        get() = relative.z

    infix fun centerOn(center: Vector): Coordinate = also {
        this.center = center
    }

    infix fun centerOn(location: Location): Coordinate =
        centerOn(location.toVector())

    override infix fun rotateAroundX(angle: Double): Vector {
        relative.rotateAroundX(angle)
        return get()
    }

    override infix fun rotateAroundY(angle: Double): Vector {
        relative.rotateAroundY(angle)
        return get()
    }

    override infix fun rotateAroundZ(angle: Double): Vector {
        relative.rotateAroundZ(angle)
        return get()
    }

    fun get(): Vector =
        center.clone().add(relative)
}