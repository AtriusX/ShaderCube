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
import xyz.atrius.shadercube.shape.Cube

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
                val angle = time / 7500.0
                Cube(point, size = Vector(3.0, 3.0, 3.0), step = 3) { _, v ->
                    location(v.rotateX(angle * 2).rotateY(angle).rotateZ(-angle * 3))
                    color(Color.YELLOW, 0.5f)
                }
                Cube(point, size = Vector(3.0, 3.0, 3.0), step = 3) { _, v ->
                    location(v.rotateX(angle * 3).rotateY(-angle * 2).rotateZ(angle))
                    color(Color.AQUA, 0.5f)
                }
                Cube(point, size = Vector(3.0, 3.0, 3.0), step = 3) { _, v ->
                    location(v.rotateX(-angle * 2).rotateY(angle).rotateZ(-angle * 1.5))
                    color(Color.RED, 0.5f)
                }
                Cube(point, size = Vector(3.0, 3.0, 3.0), step = 3) { _, v ->
                    location(v.rotateX(-angle * 3).rotateY(-angle * 1.5).rotateZ(angle))
                    color(Color.LIME, 0.5f)
                }
            }
        }
    }

    @EventHandler
    fun onLeave(event: PlayerQuitEvent) =
        players[event.player]?.cancel()
}


