package xyz.atrius.shadercube

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin
import org.koin.core.context.startKoin
import org.koin.dsl.module
import xyz.atrius.shadercube.shader.shader
import xyz.atrius.shadercube.shape.circle
import xyz.atrius.shadercube.util.hsb
import kotlin.math.cos
import kotlin.math.sin

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
        shader {
            update {
                location = player.location.apply {
                    y += sin(time / 500.0) * 1.5
                }
                circle(
                    size = 4 + cos(time / 500.0) * 3,
                    vertexes = 75
                ) { (v) ->
                    location(v.rotateY(time / 1500.0))
                    color(hsb(framecount / 200f, 0.75f, 1f), 2f)
                }
            }
            cancel { !player.isOnline }
        }
    }
}
