package xyz.atrius.shadercube

import org.bukkit.Color
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin
import org.koin.core.context.startKoin
import org.koin.dsl.module
import xyz.atrius.shadercube.shader.Sphere
import xyz.atrius.shadercube.shader.shader

typealias KotlinPlugin =
    JavaPlugin

@Suppress("unused")
class ShaderCube : KotlinPlugin(), Listener {

    override fun onEnable() {
        server.pluginManager.registerEvents(this, this)
        val module = module {
            single { this@ShaderCube }
        }
        startKoin {
            modules(module)
        }
    }

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        shader(5) {
            setup {
                centered = true
            }
            update {
//                val player = event.player
//                val eyes = player.location
//                val looking = player.getTargetBlock(50)?.location ?: return@update
//                Line(eyes, looking, 100) {
//                    val p1 = it.p1
//                    color(Color.fromRGB(
//                        abs(p1.blockX) % 255,
//                        abs(p1.blockY) % 255,
//                        abs(p1.blockZ) % 255
//                    ))
//                }

                point = event.player.location.add(0.0, 1.0, 0.0)
                Sphere(point, 4.0) { _, _ ->
                    color(Color.YELLOW)
                }
//                Circle(point, 4.0, 100) {
//                    color(Color.YELLOW)
//                }
//                Circle(point, 4.0, 100) {
//                    color(Color.YELLOW)
//                    location(location()?.toVector()?.rotateX(Math.toRadians(90.0)) ?: return@Circle)
//                }
//                Circle(point, 4.0, 100) {
//                    color(Color.YELLOW)
//                    location(location()?.toVector()?.rotateZ(Math.toRadians(90.0)) ?: return@Circle)
//                }
//                Circle(point, 4.0, 100) {
//                    color(Color.YELLOW)
//                    location(location()?.toVector()
//                        ?.rotateZ(Math.toRadians(45.0)
//                    ) ?: return@Circle)
//                }
//                Circle(point, 4.0, 100) {
//                    color(Color.YELLOW)
//                    location(location()?.toVector()
//                        ?.rotateZ(Math.toRadians(-45.0)
//                    ) ?: return@Circle)
//                }
//                Circle(point, 4.0, 100) {
//                    color(Color.YELLOW)
//                    location(location()?.toVector()
//                            ?.rotateX(Math.toRadians(45.0)
//                            ) ?: return@Circle)
//                }
//                Circle(point, 4.0, 100) {
//                    color(Color.YELLOW)
//                    location(location()?.toVector()
//                            ?.rotateX(Math.toRadians(-45.0)
//                            ) ?: return@Circle)
//                }
//                Circle(point, 4.0, 100) {
//                    color(Color.YELLOW)
//                    location(location()?.toVector()
//                            ?.rotateX(Math.toRadians(90.0))
//                            ?.rotateY(Math.toRadians(45.0))
//                            ?: return@Circle)
//                }
//                Circle(point, 4.0, 100) {
//                    color(Color.YELLOW)
//                    location(location()?.toVector()
//                            ?.rotateX(Math.toRadians(90.0))
//                            ?.rotateY(Math.toRadians(-45.0))
//                            ?: return@Circle)
//                }
//
//                Circle(position, 2.0, 50) {
//                    val loc = location()?.toVector() ?: return@Circle
//                    val angle = Math.toRadians(time / 25.0)
//                    color(Color.fromRGB(
//                        max(0, loc.x.toInt()) % 255,
//                        max(0, loc.y.toInt()) % 255,
//                        max(0, loc.z.toInt()) % 255
//                    ))
//                    location(loc.rotateX(angle).rotateY(angle * 2).rotateZ(angle * 3))
//                }

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

//                val angle = (time / 500.0) % 360
//                particle(Particle.REDSTONE) {
//                    color(Color.AQUA)
//                    location(position.toVector()
//                        .rotateX(angle, 1.0)
//                        .rotateY(45.0)
//                    )
//
//                }
//
//                particle(Particle.REDSTONE) {
//                    color(Color.AQUA)
//                    location(position.toVector()
//                        .rotateX(angle, 1.0)
//                        .rotateY(-45.0)
//                    )
//                }
//
//                particle(Particle.REDSTONE) {
//                    color(Color.AQUA)
//                    location(position.toVector()
//                        .rotateY(angle, 1.0)
//                        .rotateX(45.0)
//                    )
//                }
//
//                particle(Particle.REDSTONE) {
//                    color(Color.AQUA)
//                    location(position.toVector()
//                        .rotateY(angle, 1.0)
//                        .rotateX(-45.0)
//                    )
//                }
//
//                particle(Particle.REDSTONE) {
//                    color(Color.AQUA)
//                    location(position.toVector()
//                        .rotateY(angle, 1.0)
//                        .rotateZ(45.0)
//                    )
//                }
//
//                particle(Particle.REDSTONE) {
//                    color(Color.AQUA)
//                    location(position.toVector()
//                        .rotateY(angle, 1.0)
//                        .rotateZ(-45.0)
//                    )
//                }
//                particle(Particle.REDSTONE) {
//                    color(Color.AQUA)
//                    location(position.toVector().rotateZ(angle, 1.0))
//                }
//                particle(Particle.REDSTONE) {
//                    color(Color.AQUA)
//                    location(position.toVector().rotateX(angle, 1.0))
//                }
//                particle(Particle.REDSTONE) {
//                    color(Color.AQUA)
//                    location(position.toVector().rotateY(angle, 1.0))
//                }