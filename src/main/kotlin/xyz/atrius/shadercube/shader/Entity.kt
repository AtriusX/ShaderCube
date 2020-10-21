@file:Suppress("unused")
package xyz.atrius.shadercube.shader

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Entity
import org.bukkit.entity.FallingBlock
import org.bukkit.util.Vector
import xyz.atrius.shadercube.util.plugin
import xyz.atrius.shadercube.util.schedule

/**
 * Builder function for easily spawning entities with custom mob
 * data. This function can be used to more closely manage mobs in
 * the context of a shader script.
 *
 * @receiver             The shader script this entity is attached to.
 * @param    T           The entity type to spawn.
 * @param    point       The position the entity will be spawned at.
 * @param    removeAfter The number of ticks to wait before removing
 *                       this entity, or -1 for no time limit.
 * @param    block       The data builder for this entity.
 * @return               The newly spawned entity or null if unsuccessful.
 */
inline fun <reified T : Entity> Shader.entity(
    point         : Vector,
    removeAfter   : Long         = -1,
    noinline block: T.() -> Unit = {}
) = entity(point.toLocation(world), removeAfter, block)

/**
 * Builder function for easily spawning entities with custom mob
 * data. This function can be used to more closely manage mobs in
 * the context of a shader script.
 *
 * @receiver          The shader script this entity is attached to.
 * @param T           The entity type to spawn.
 * @param location    The position the entity will be spawned at.
 * @param removeAfter The number of ticks to wait before removing
 *                    this entity, or -1 for no time limit.
 * @param block       The data builder for this entity.
 * @return            The newly spawned entity or null if unsuccessful.
 */
inline fun <reified T : Entity> Shader.entity(
    location      : Location     = this.location,
    removeAfter   : Long         = -1,
    noinline block: T.() -> Unit = {}
) = try {
    // Spawn the entity at the given location with the given spawn function
    world.spawn(location, T::class.java, block).also {
        // Set up a timer if the removeAfter value is not default
        if (removeAfter != -1L) schedule.scheduleSyncDelayedTask(
            plugin, { it.remove() }, removeAfter
        )
    }
} catch (e: Exception) {
    Bukkit.getLogger().warning(e.message)
    null
}

/**
 * Spawns lightning at the given world position.
 *
 * @receiver     The shader script this lightning bolt is attached to.
 * @param point  The position the lighting will spawn at.
 * @param effect Whether or not this lightning bolt can cause damage to
 *               nearby entities.
 * @return       The lightning strike.
 */
fun Shader.lightning(
    point : Vector,
    effect: Boolean = false
) = lightning(point.toLocation(world), effect)

/**
 * Spawns lightning at the given world position.
 *
 * @receiver       The shader script this lightning bolt is attached to.
 * @param location The position the lighting will spawn at.
 * @param effect   Whether or not this lightning bolt can cause damage to
 *                 nearby entities.
 * @return         The lightning strike.
 */
fun Shader.lightning(
    location: Location = this.location,
    effect  : Boolean  = false
) = if (effect)
    world.strikeLightningEffect(location)
else
    world.strikeLightning(location)

/**
 * Spawns a falling block at the given world position.
 *
 * @receiver       The shader script this block is attached to.
 * @param material The material of the falling block.
 * @param point    The position of the falling block.
 * @param block    The block metadata builder script.
 * @return         The falling block entity.
 */
fun Shader.fallingBlock(
    material: Material                = Material.SAND,
    point   : Vector,
    block   : FallingBlock.() -> Unit = {}
) = fallingBlock(material, point.toLocation(world), block)

/**
 * Spawns a falling block at the given world position.
 *
 * @receiver       The shader script this block is attached to.
 * @param material The material of the falling block.
 * @param location The position of the falling block.
 * @param block    The block metadata builder script.
 * @return         The falling block entity.
 */
fun Shader.fallingBlock(
    material: Material                = Material.SAND,
    location: Location                = this.location,
    block   : FallingBlock.() -> Unit = {}
) = world.spawnFallingBlock(location, material.createBlockData()).apply(block)
