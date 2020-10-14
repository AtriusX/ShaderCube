package xyz.atrius.shadercube

import org.bukkit.Particle
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin
import org.koin.core.context.startKoin
import org.koin.dsl.module
import xyz.atrius.shadercube.shader.animation
import xyz.atrius.shadercube.shape.Circle

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
        animation(2, 100) {
            point = player.location.apply { y = 255.0 }
            update {
                Circle(point, Particle.END_ROD, ((frameDuration - framecount)).toDouble(), 100) {
                    force(true)
                    count(3)
                    extra(0.2)
                }
            }
        }
    }
}
