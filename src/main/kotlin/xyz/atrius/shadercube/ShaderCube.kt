package xyz.atrius.shadercube

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin
import org.koin.core.context.startKoin
import org.koin.dsl.module
import xyz.atrius.shadercube.shader.shader
import xyz.atrius.shadercube.shader.text
import xyz.atrius.shadercube.util.hsb
import xyz.atrius.shadercube.util.radians
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
        shader {
            location = player.location
            update {
                text(point, """Hello World!
                    |How are you today?
                """.trimMargin(), size = 0.3.vec) { (v, t) ->
                    location(v
                        .rotateX((-player.location.pitch.toDouble()).radians, center = t.point)
                        .rotateY((180 - player.location.yaw.toDouble()).radians))
                    color(hsb(framecount / 400f, 1f, 1f), 0.3f)
                }
            }
            cancel { !player.isOnline }
        }
    }
}
