package xyz.atrius.shadercube.data

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

    override fun rotateAroundX(angle: Double): Vector = subtract(center).also {
        super.rotateAroundX(angle).add(center)
    }

    override fun rotateAroundY(angle: Double): Vector {
        relative.rotateAroundY(angle)
        return get()
    }

    override fun rotateAroundZ(angle: Double): Vector = subtract(center).also {
        super.rotateAroundZ(angle).add(center)
    }

    fun get(): Vector =
        center.clone().add(relative)
}