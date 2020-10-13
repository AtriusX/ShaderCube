package xyz.atrius.shadercube.shader

import org.bukkit.entity.Entity
import org.bukkit.util.Vector

typealias ExplosionBuilder =
    ExplosionData.() -> Unit

data class ExplosionData(
    var power      : Double  = 1.0,
    var setFire    : Boolean = true,
    var breakBlocks: Boolean = true,
    var source     : Entity? = null
)

fun Shader.explosion(
    point: Vector           = this.point.toVector(),
    data : ExplosionBuilder = {}
) {
    val (power, setFire, breakBlocks, source) = ExplosionData().apply(data)
    world.createExplosion(
        point.toLocation(world), power.toFloat(), setFire, breakBlocks, source
    )
}
