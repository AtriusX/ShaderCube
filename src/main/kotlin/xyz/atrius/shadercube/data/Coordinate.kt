package xyz.atrius.shadercube.data

import org.bukkit.util.Vector

data class Coordinate(
        var center: Vector,
        var worldX: Double,
        var worldY: Double,
        var worldZ: Double
) : Vector(worldX, worldY, worldZ) {

    constructor(center: Vector, relative: Vector) : this(center, relative.x, relative.y, relative.z)

    val relX: Double
        get() = x - center.x

    val relY: Double
        get() = y - center.y

    val relZ: Double
        get() = z - center.z

    fun relative(): Vector =
            Vector(relX, relY, relZ)
}