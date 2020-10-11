package xyz.atrius.shadercube

import org.bukkit.Color
import org.bukkit.Particle
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.java.JavaPlugin
import org.koin.core.context.startKoin
import org.koin.dsl.module
import xyz.atrius.shadercube.shader.Shader
import xyz.atrius.shadercube.shader.shader
import xyz.atrius.shadercube.shape.Polygon
import xyz.atrius.shadercube.util.radians

typealias KotlinPlugin =
    JavaPlugin

@Suppress("unused")
class ShaderCube : KotlinPlugin(), Listener {

    private val players = hashMapOf<Player, Shader>()

    override fun onEnable() {
        server.pluginManager.registerEvents(this, this)
        // Send plugin class to dependency injector
        startKoin {
            modules(module { single { this@ShaderCube } })
        }
    }

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        players[event.player] = shader {
            update {
                point = event.player.location.add(0.0, 1.0, 0.0)
                Polygon(point, Particle.REDSTONE, 3.0) { p, v ->
                    location(v.rotateY(time / 1500.0))
                    color(Color.YELLOW, 0.5f)
                }
                Polygon(point, Particle.REDSTONE, 3.0) { _, v ->
                    location(v.rotateY(40.radians + time / 1500.0))
                    color(Color.AQUA, 0.5f)
                }
                Polygon(point, Particle.REDSTONE, 3.0) { _, v ->
                    location(v.rotateY(80.radians + time / 1500.0))
                    color(Color.RED, 0.5f)
                }
                Polygon(point, Particle.REDSTONE, 1.0, 6) { _, v ->
                    location(v.rotateY(time / 1500.0))
                    color(Color.LIME, 0.5f)
                }
            }
        }
    }

    @EventHandler
    fun onLeave(event: PlayerQuitEvent) =
        players[event.player]?.cancel()
}


