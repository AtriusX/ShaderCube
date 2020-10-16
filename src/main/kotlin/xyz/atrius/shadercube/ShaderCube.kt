package xyz.atrius.shadercube

import org.bukkit.Color
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin
import org.koin.core.context.startKoin
import org.koin.dsl.module
import xyz.atrius.shadercube.shader.shader
import xyz.atrius.shadercube.shape.circle
import xyz.atrius.shadercube.util.plusCompliments
import xyz.atrius.shadercube.util.radians

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
            val colors = Color.FUCHSIA.plusCompliments(10)
            update {
                val loc  = player.location
                location = loc.clone().apply {
                    y += (framecount.toDouble() / 4.0 % 4.25) - 1
                }
                val size = when {
                    location.y >=loc.y + 2 -> 1 - (location.y - (loc.y + 2.0))
                    location.y < loc.y     -> 1 - (loc.y - location.y)
                    else                   -> 1.0
                }
                circle(
                    vertexes = 15,
                    size     = size
                ) { (v) ->
                    location(v.rotateY(time / 250.0).apply {
                        if (player.isGliding) {
                            rotateX((90.0 + location.pitch).radians, center = player.eyeLocation)
                            rotateY(-location.yaw.toDouble().radians)
                        }
                    })
                    color(colors[(framecount / 17) % colors.size])
                }
            }
            cancel { !player.isOnline }
        }
    }
}
