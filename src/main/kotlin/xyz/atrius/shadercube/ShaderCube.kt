package xyz.atrius.shadercube

import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin
import xyz.atrius.shadercube.shader.*
import kotlin.math.cos
import kotlin.math.sin

typealias KotlinPlugin =
    JavaPlugin

@Suppress("unused")
class ShaderCube : KotlinPlugin(), Listener {

    override fun onEnable() {
        server.pluginManager.registerEvents(this, this)
    }

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        val shader = shader {
            setup {
                centered = true
                size = 10 by 10
                event.player.sendMessage("HELLO WORLD!")
            }
            update {
                position = event.player.location
                val (x, _, z) = size
                val lowerX = position.x.toInt() - 4
                val upperX = lowerX + x
                val lowerZ = position.z.toInt() - 4
                val upperZ = lowerZ + z
                for (px in lowerX until upperX)
                    for (pz in lowerZ until upperZ) position.world?.spawnParticle(
                        Particle.REDSTONE, Location(position.world, px.toDouble(),
                                position.y + sin(time.toDouble() / 1000 + px) + cos(time.toDouble() / 500 + pz),
                                pz.toDouble()
                        ), 1, Particle.DustOptions(
                            Color.fromRGB((px - lowerX) * 25, (pz - lowerZ) * 25, 0), 1.5f
                        )
                    )
            }
        }
        shader.setup()
        server.scheduler.scheduleSyncRepeatingTask(this, {
            shader.update()
        }, 1L, 1L)
    }
}