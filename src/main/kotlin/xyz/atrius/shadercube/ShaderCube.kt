package xyz.atrius.shadercube

import com.destroystokyo.paper.ParticleBuilder
import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.util.Vector
import xyz.atrius.shadercube.shader.*
import kotlin.math.cos
import kotlin.math.sin

typealias KotlinPlugin =
    JavaPlugin

@Suppress("unused")
class ShaderCube : KotlinPlugin(), Listener {

    override fun onEnable() {
        server.pluginManager.registerEvents(this, this)
    }

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        shader(this) {
            setup {
                centered = true
            }
            update {
                position = event.player.location.add(0.0, 1.0, 0.0)
                val angle = (time / 500.0) % 360
                particle(Particle.REDSTONE) {
                    color(Color.AQUA)
                    location(position.toVector()
                        .rotateX(angle, 1.0)
                        .rotateY(45.0)
                    )

                }
                
                particle(Particle.REDSTONE) {
                    color(Color.AQUA)
                    location(position.toVector()
                        .rotateX(angle, 1.0)
                        .rotateY(-45.0)
                    )
                }

                particle(Particle.REDSTONE) {
                    color(Color.AQUA)
                    location(position.toVector()
                        .rotateY(angle, 1.0)
                        .rotateX(45.0)
                    )
                }

                particle(Particle.REDSTONE) {
                    color(Color.AQUA)
                    location(position.toVector()
                        .rotateY(angle, 1.0)
                        .rotateX(-45.0)
                    )
                }

                particle(Particle.REDSTONE) {
                    color(Color.AQUA)
                    location(position.toVector()
                        .rotateY(angle, 1.0)
                        .rotateZ(45.0)
                    )
                }

                particle(Particle.REDSTONE) {
                    color(Color.AQUA)
                    location(position.toVector()
                        .rotateY(angle, 1.0)
                        .rotateZ(-45.0)
                    )
                }
                particle(Particle.REDSTONE) {
                    color(Color.AQUA)
                    location(position.toVector().rotateZ(angle, 1.0))
                }
                particle(Particle.REDSTONE) {
                    color(Color.AQUA)
                    location(position.toVector().rotateX(angle, 1.0))
                }
                particle(Particle.REDSTONE) {
                    color(Color.AQUA)
                    location(position.toVector().rotateY(angle, 1.0))
                }
            }
        }
    }
}


//                val (x, _, z) = size
//                val lowerX = position.x.toInt() - 4
//                val upperX = lowerX + x
//                val lowerZ = position.z.toInt() - 4
//                val upperZ = lowerZ + z
//                for (px in lowerX until upperX)
//                    for (pz in lowerZ until upperZ)
//                        particle(Particle.REDSTONE) {
//                            location(position.world, px.toDouble(), position.y +
//                                sin(time.toDouble() / 1000 + px) + cos(time.toDouble() / 500 + pz), pz.toDouble()
//                            )
//                            color(Color.fromRGB((px - lowerX) * 25, (pz - lowerZ) * 25, 0), 1.5f)
//                        }