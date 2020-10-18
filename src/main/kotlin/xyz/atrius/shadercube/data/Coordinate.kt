package xyz.atrius.shadercube.data

import org.bukkit.util.Vector

class Coordinate(
    var center: Vector,
        worldX: Double,
        worldY: Double,
        worldZ: Double
) : Vector(worldX, worldY, worldZ) {

    constructor(center: Vector, relative: Vector) : this(center, relative.x, relative.y, relative.z)

    val relX: Double
        get() = x - center.x

    val relY: Double
        get() = y - center.y

    val relZ: Double
        get() = z - center.z

    override fun rotateAroundX(angle: Double): Vector = subtract(center).also {
        super.rotateAroundX(angle).add(center)
    }

    override fun rotateAroundY(angle: Double): Vector = subtract(center).also {
        super.rotateAroundY(angle).add(center)
    }

    override fun rotateAroundZ(angle: Double): Vector = subtract(center).also {
        super.rotateAroundZ(angle).add(center)
    }

    fun relative(): Vector =
        Vector(relX, relY, relZ)
}