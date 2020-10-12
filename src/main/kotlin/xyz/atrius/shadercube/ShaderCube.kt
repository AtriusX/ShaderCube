package xyz.atrius.shadercube

import com.destroystokyo.paper.ParticleBuilder
import org.bukkit.Color
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.util.Vector
import org.koin.core.context.startKoin
import org.koin.dsl.module
import xyz.atrius.shadercube.shader.shader
import xyz.atrius.shadercube.shape.Circle
import xyz.atrius.shadercube.shape.Star
import xyz.atrius.shadercube.util.radians
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
        val player = event.player
        if (!player.isGliding) {
            return
        }
        shader {
            fun ParticleBuilder.update(player: Player, v: Vector) {
                color(Color.YELLOW)
                location(v.rotateX((90.0 + point.pitch).radians,
                        center = player.eyeLocation.toVector())
                        .rotateY(-point.yaw.toDouble().radians)
                )
            }
            point = player.location.add(0.0, 9.0, 0.0)
            val size = 4.0
            Circle(point,
                size     = size,
                vertexes = 100
            ) { (v) -> update(player, v) }
            Star(point,
                size   = size,
                points = 3 + abs(sin(time / 2000.0) * 9).toInt(),
                jump   = 1 + abs(sin(time / 500.0) * 3).toInt()
            ) { (v) -> update(player, v) }
        }
    }
}


