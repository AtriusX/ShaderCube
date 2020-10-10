package xyz.atrius.shadercube

import org.bukkit.Color
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.util.Vector
import org.koin.core.context.startKoin
import org.koin.dsl.module
import xyz.atrius.shadercube.shader.Shader
import xyz.atrius.shadercube.shader.shader
import xyz.atrius.shadercube.shape.Circle
import xyz.atrius.shadercube.shape.Sphere
import xyz.atrius.shadercube.shape.Square
import xyz.atrius.shadercube.util.radians
import kotlin.math.sin

typealias KotlinPlugin =
    JavaPlugin

@Suppress("unused")
class ShaderCube : KotlinPlugin(), Listener {

    val players = hashMapOf<Player, Shader>()

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
                point = event.player.location
                Square(point, size = Vector(6.0, 0.0, 6.0)) { _, v ->
                    location(v.rotateY(time / 1500.0))
                    color(Color.YELLOW)
                }
                Square(point, size = Vector(6.0, 0.0, 6.0)) { _, v ->
                    location(v.rotateY(45.radians + time / 1500.0))
                    color(Color.AQUA)
                }
                Sphere(point, size = 1.5, vertexes = 12) { _, v ->
                    location(v
                        .rotateX(time / 300.0)
                        .rotateZ(time / 250.0)
                        .add(Vector(0.0, 1.0, 0.0))
                    )
                    color(Color.RED)
                }
                Circle(point, size = sin(time / 500.0) * 10, vertexes = 8) { _, v ->
                    location(v.rotateY(time / 1000.0))
                    color(Color.PURPLE, 5f)
                }
            }
        }
    }

    @EventHandler
    fun onLeave(event: PlayerQuitEvent) =
        players[event.player]?.cancel()
}


