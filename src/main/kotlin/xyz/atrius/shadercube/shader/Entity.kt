package xyz.atrius.shadercube.shader

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Entity
import org.bukkit.entity.FallingBlock
import org.bukkit.util.Vector

inline fun <reified T : Entity> Shader.entity(
    point         : Vector       = this.point.toVector(),
    effect        : Boolean      = false,
    noinline block: T.() -> Unit = {}
) = try {
    world.spawn(point.toLocation(world), T::class.java, block)
} catch (e: Exception) {
    Bukkit.getLogger().warning(e.message)
    null
}

fun Shader.lightning(
    point : Vector  = this.point.toVector(),
    effect: Boolean = false
) = if (effect)
    world.strikeLightningEffect(point.toLocation(world))
else
    world.strikeLightning(point.toLocation(world))

fun Shader.fallingBlock(
    material: Material             = Material.SAND,
    point: Vector                  = this.point.toVector(),
    block: FallingBlock.() -> Unit = {}
) {
    world.spawnFallingBlock(
        point.toLocation(world), material.createBlockData()
    ).apply(block)
}