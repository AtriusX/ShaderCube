package xyz.atrius.shadercube.shader

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Entity
import org.bukkit.entity.FallingBlock
import org.bukkit.util.Vector

inline fun <reified T : Entity> Shader.entity(
    point         : Vector       = this.point,
    noinline block: T.() -> Unit = {}
) = entity(point.toLocation(world), block)

inline fun <reified T : Entity> Shader.entity(
    location         : Location  = this.location,
    noinline block: T.() -> Unit = {}
) = try {
    world.spawn(location, T::class.java, block)
} catch (e: Exception) {
    Bukkit.getLogger().warning(e.message)
    null
}

fun Shader.lightning(
    point : Vector  = this.point,
    effect: Boolean = false
) = lightning(point.toLocation(world), effect)

fun Shader.lightning(
    location: Location = this.location,
    effect  : Boolean  = false
) = if (effect)
    world.strikeLightningEffect(location)
else
    world.strikeLightning(location)

fun Shader.fallingBlock(
    material: Material                = Material.SAND,
    point   : Vector                  = this.point,
    block   : FallingBlock.() -> Unit = {}
) = fallingBlock(material, point.toLocation(world), block)

fun Shader.fallingBlock(
    material: Material             = Material.SAND,
    location: Location             = this.location,
    block: FallingBlock.() -> Unit = {}
) = world.spawnFallingBlock(location, material.createBlockData()).apply(block)
