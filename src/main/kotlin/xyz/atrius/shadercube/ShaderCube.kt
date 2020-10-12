package xyz.atrius.shadercube

import org.bukkit.Color
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.plugin.java.JavaPlugin
import org.koin.core.context.startKoin
import org.koin.dsl.module
import xyz.atrius.shadercube.shader.shader
import xyz.atrius.shadercube.shape.Circle
import xyz.atrius.shadercube.shape.Star
import kotlin.math.abs
import kotlin.math.sin

typealias KotlinPlugin  =
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
    fun onJoin(event: PlayerMoveEvent) {
        shader {
            point = event.player.location.add(0.0, 1.0, 0.0)
            Circle(point,
                size     = 4.0,
                vertexes = 100
            ) { (v) ->
                color(Color.RED)
                location(v.rotateY(time / 2000.0).rotateX(time / 1500.0).rotateZ(time / 3000.0))
            }
            Star(point,
                size   = 4.0,
                points = 3 + abs(sin(time / 4000.0) * 9).toInt(),
                jump   = 1 + abs(sin(time / 2500.0) * 3).toInt()
            ) { (v) ->
                color(Color.YELLOW)
                location(v.rotateY(time / 2000.0).rotateX(time / 1500.0).rotateZ(time / 3000.0))
            }
        }
    }

}


