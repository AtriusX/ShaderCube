package xyz.atrius.shadercube.shader

import org.bukkit.Bukkit
import org.bukkit.entity.Entity
import org.bukkit.util.Vector

inline fun <reified T : Entity> Shader.entity(
    point         : Vector       = this.point.toVector(),
    noinline block: T.() -> Unit = {}
) = try {
    // I absolutely hate this but there doesn't appear to be a better way
    if (T::class.simpleName == "LightningStrike") {
        val l = world.strikeLightning(point.toLocation(world))
    } else {
        world.spawn(point.toLocation(world), T::class.java, block)
    }
} catch (e: Exception) {
    Bukkit.getLogger().warning(e.message)
}