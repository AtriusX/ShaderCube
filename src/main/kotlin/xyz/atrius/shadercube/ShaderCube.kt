package xyz.atrius.shadercube

import org.bukkit.Particle
import org.bukkit.entity.Dolphin
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin
import org.koin.core.context.startKoin
import org.koin.dsl.module
import xyz.atrius.shadercube.shader.entity
import xyz.atrius.shadercube.shader.shader
import xyz.atrius.shadercube.shape.Circle
import xyz.atrius.shadercube.util.vec

typealias KotlinPlugin =
    JavaPlugin

@Suppress("unused")
class ShaderCube : KotlinPlugin(), Listener {

    override fun onEnable() {
        server.pluginManager.registerEvents(this, this)
        // Send plugin class to dependency injector
        startKoin {
            modules(module { single { this@ShaderCube } })
        }
    }

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        val player = event.player
        // SKY DOLPHINS
        repeat(10) {
            shader {
                location = player.location.add(0.0, 30.0, 0.0)
                val dolphin = entity<Dolphin>(point)  {
                    removeWhenFarAway = false
                    isInvulnerable = true
                } ?: return@shader
                update {
                    dolphin.velocity = dolphin.location.direction.divide(10.vec)
                    every(20) {
                        Circle(dolphin.location, Particle.END_ROD, size = 2.5, vertexes = 50) {
                            extra(0.0)
                        }
                    }
                }
                cancel { !dolphin.isValid }
            }
        }
    }
}
