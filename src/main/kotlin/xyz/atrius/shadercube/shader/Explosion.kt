package xyz.atrius.shadercube.shader

import org.bukkit.entity.Entity
import org.bukkit.util.Vector

/**
 * A high-order function specifically for creating and applying data to explosions
 * before they are created. This function makes use of a custom [ExplosionData]
 * receiver object that is later destructured and applied to a explosion event.
 */
typealias ExplosionBuilder =
    ExplosionData.() -> Unit

/**
 * A constructive data type used in [explosion] to create declarative explosions before
 * they are created. All provided information in this constructor is considered optional.
 *
 * @property power       The power of the explosion.
 * @property setFire     Whether or not this explosion creates fire.
 * @property breakBlocks Whether or not this explosion can break blocks.
 * @property source      The entity that caused the explosion.
 */
data class ExplosionData(
    var power      : Double  = 1.0,
    var setFire    : Boolean = true,
    var breakBlocks: Boolean = true,
    var source     : Entity? = null
)

/**
 * A helper function designed for use in shader scripts. This function allows explosions
 * to be described in a declarative fashion.
 *
 * @param point The position of the explosion.
 * @param data  The optional explosion builder to use in constructing this explosion.
 *
 * @see ExplosionBuilder
 * @see ExplosionData
 */
fun Shader.explosion(
    point: Vector           = this.point,
    data : ExplosionBuilder = {}
) {
    // Destructure the data after it has been created
    val (power, setFire, breakBlocks, source) = ExplosionData().apply(data)
    // Create an explosion with the given data
    world.createExplosion(
        point.toLocation(world), power.toFloat(), setFire, breakBlocks, source
    )
}
