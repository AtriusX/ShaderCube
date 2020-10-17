package xyz.atrius.shadercube

import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.util.Vector
import org.koin.core.context.startKoin
import org.koin.dsl.module
import xyz.atrius.shadercube.shader.shader
import xyz.atrius.shadercube.shape.line
import xyz.atrius.shadercube.util.Blend
import xyz.atrius.shadercube.util.blend
import xyz.atrius.shadercube.util.plusGradients

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
            location = Location(player.world, 0.0, 90.0, 0.0)
            val gradients = Color.RED.plusGradients(Color.LIME, Color.WHITE, points = 15)
            val blends = arrayOf(Blend.ADD, Blend.SUBTRACT, Blend.MULTIPLY, Blend.DIVIDE)
            update {
                var g = 0
                line(
                    point2   = point.add(Vector(10.0, 0.0, 10.0)),
                    vertexes = gradients.size
                ) {
                    color(gradients[g++].blend(Color.ORANGE, mode = blends[framecount / 60 % blends.size]))
                }
            }
        }
    }
}
