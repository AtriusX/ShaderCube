package xyz.atrius.shadercube

import org.bukkit.Particle
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin
import org.koin.core.context.startKoin
import org.koin.dsl.module
import xyz.atrius.shadercube.shader.animation
import xyz.atrius.shadercube.shader.frame
import xyz.atrius.shadercube.shader.lightning
import xyz.atrius.shadercube.shape.Circle
import kotlin.math.min

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
        animation(2, 50) {
            location = player.location.apply { y = 128.0 }
            update {
                val remainingFrames = frameDuration - framecount
                Circle(location, Particle.END_ROD, remainingFrames * 2.5, min(120, remainingFrames * 3)) { (v) ->
                    location(v.rotateY(time / 1000.0))
                    count(5)
                    extra(remainingFrames / frameDuration.toDouble() + 0.1)
                }
                frame(50) {
                    lightning(world.getHighestBlockAt(location).location.toVector(), true)
                }
            }
        }
    }
}
