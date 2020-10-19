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
        val text = """
            |Hello World!
            |How are you today?
        """.trimMargin()
        shader {
            location = player.location
            val txt  = text(
                text = text,
                size = 0.3.vec
            ) { (v) ->
                v.center = player.location.toVector()
                location(v
                    .rotateAroundX((-player.location.pitch.toDouble()).radians)
                    .rotateAroundY((180 - player.location.yaw.toDouble()).radians)
                )
                color(hsb(framecount / 400f, 1f, 1f), 0.3f)
            }
            update(*txt)
        }
    }
}
