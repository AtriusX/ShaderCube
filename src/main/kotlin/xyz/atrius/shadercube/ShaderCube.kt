package xyz.atrius.shadercube

import org.bukkit.Color
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.LightningStrike
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin
import org.koin.core.context.startKoin
import org.koin.dsl.module
import xyz.atrius.shadercube.data.orbit
import xyz.atrius.shadercube.shader.entity
import xyz.atrius.shadercube.shader.shader
import xyz.atrius.shadercube.shader.sound
import kotlin.random.Random

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
    fun onJoin(event: PlayerJoinEvent) {
        val player = event.player
        shader {
            point = player.location
            val o = orbit(point, 5.0) { (v, o) ->
                particle(Particle.REDSTONE, v) {
                    color(Color.YELLOW, 5f)
                }
                every(3) {
                    o.rate -= 0.05
                    sound(Sound.BLOCK_NOTE_BLOCK_BIT) {
                        point = v
                        pitch = Random.nextDouble() * 2
                    }
                }
                every(60) {
                    entity<LightningStrike>()
                }
            }

            update {
                point = player.location
                o.point = point
                o.update()
            }
            cancel { !player.isOnline }
        }
    }
}

