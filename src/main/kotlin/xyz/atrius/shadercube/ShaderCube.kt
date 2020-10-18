package xyz.atrius.shadercube

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin
import org.koin.core.context.startKoin
import org.koin.dsl.module
import xyz.atrius.shadercube.shader.Text
import xyz.atrius.shadercube.shader.shader
import xyz.atrius.shadercube.util.hsb
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
        shader(3) {
            location = player.location
            update {
                Text(location, "Hello World!", size = 0.75.vec) { (v) ->
                    location(v.rotateY(time / 8000.0))
                    color(hsb(framecount / 400f, 1f, 1f))
                }
            }
            cancel { !player.isOnline }
        }

    }
}
