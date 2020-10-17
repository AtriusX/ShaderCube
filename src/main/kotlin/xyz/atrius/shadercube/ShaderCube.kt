package xyz.atrius.shadercube

import org.bukkit.Location
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin
import org.koin.core.context.startKoin
import org.koin.dsl.module
import xyz.atrius.shadercube.shader.shader
import xyz.atrius.shadercube.shape.square
import xyz.atrius.shadercube.util.vec
import xyz.atrius.shadercube.util.yuv
import kotlin.math.cos
import kotlin.math.sin

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
        shader {
            location = Location(player.world, 0.0, 90.0, 0.0)
            update {
                square(
                    size = 5.vec,
                    hollow = false,
                    step = 3
                ) {
                    color(yuv(sin(framecount / 10f), sin(framecount / 20f) * 5, cos(framecount / 30f) * 5))
                }
            }
        }
    }
}
