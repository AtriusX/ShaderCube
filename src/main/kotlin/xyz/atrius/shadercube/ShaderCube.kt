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
import xyz.atrius.shadercube.shape.Triangle
import xyz.atrius.shadercube.util.radians
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

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
            val data = hashMapOf(
                Color.YELLOW to 0,
                Color.AQUA to 90,
                Color.LIME to 180,
                Color.RED to 270
            )
            update {
                data.forEach { (c, a) ->
                    point = event.player.location.add(0.0, 1.0, 0.0)
                    Triangle(point, Particle.REDSTONE,
                        2 + abs(sin(time / 1000.0) * 10), 2 + abs(sin(time / 1500.0) * 6), // sin(time / 3000.0) * 3
                    ) { _, v ->
                        location(v.rotateY(a.radians + abs(cos(time / 10000.0))))
                        color(c)
                    }
                }

            }
        }
    }

    @EventHandler
    fun onLeave(event: PlayerQuitEvent) =
        players[event.player]?.cancel()
}


