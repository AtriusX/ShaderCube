package xyz.atrius.shadercube

import com.destroystokyo.paper.ParticleBuilder
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.entity.Spider
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.potion.PotionEffectType
import org.bukkit.util.Vector
import xyz.atrius.shadercube.data.orbit
import xyz.atrius.shadercube.shader.*
import xyz.atrius.shadercube.shape.*
import xyz.atrius.shadercube.util.*
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin
import kotlin.random.Random

fun orbit(): Update = {
    update {
        val angle = (time / 500.0) % 360
        particle(Particle.REDSTONE) {
            color(Color.YELLOW)
            location(point
                .rotateX(angle, 1.0)
                .rotateY(45.0)
            )
        }
        particle(Particle.REDSTONE) {
            color(Color.AQUA)
            location(point
                .rotateX(angle, 1.0)
                .rotateY(-45.0)
            )
        }
        particle(Particle.REDSTONE) {
            color(Color.AQUA)
            location(point
                .rotateY(angle, 1.0)
                .rotateX(45.0)
            )
        }
        particle(Particle.REDSTONE) {
            color(Color.AQUA)
            location(point
                .rotateY(angle, 1.0)
                .rotateX(-45.0)
            )
        }
        particle(Particle.REDSTONE) {
            color(Color.AQUA)
            location(point
                .rotateY(angle, 1.0)
                .rotateZ(45.0)
            )
        }
        particle(Particle.REDSTONE) {
            color(Color.AQUA)
            location(point
                .rotateY(angle, 1.0)
                .rotateZ(-45.0)
            )
        }
        particle(Particle.REDSTONE) {
            color(Color.AQUA)
            location(point.rotateZ(angle, 1.0))
        }
        particle(Particle.REDSTONE) {
            color(Color.AQUA)
            location(point.rotateX(angle, 1.0))
        }
        particle(Particle.REDSTONE) {
            color(Color.AQUA)
            location(point.rotateY(angle, 1.0))
        }
    }
}

fun rayCast(player: Player): Update = {
    val eyes = player.location
    val looking = player.getTargetBlock(50)?.location ?: eyes
    Line(eyes, looking, vertexes = 100) { (_, l) ->
        val (x, y, z) = l.point
        color(Color.fromRGB(
            abs(x) % 255, abs(y) % 255, abs(z) % 255
        ))
    }
}

fun sphere(): Update = {
    Sphere(location, size = 4.0) {
        color(Color.YELLOW)
    }
}

fun globe(): Update = {
    Circle(location, size = 4.0, vertexes =  100) {
        color(Color.YELLOW)
    }
    Circle(location, size = 4.0, vertexes =  100) { (v) ->
        color(Color.YELLOW)
        location(v.rotateX(Math.toRadians(90.0)))
    }
    Circle(location, size = 4.0, vertexes =  100) { (v) ->
        color(Color.YELLOW)
        location(v.rotateZ(Math.toRadians(90.0)))
    }
    Circle(location, size = 4.0, vertexes =  100) { (v) ->
        color(Color.YELLOW)
        location(v.rotateZ(Math.toRadians(45.0)))
    }
    Circle(location, size = 4.0, vertexes =  100) { (v) ->
        color(Color.YELLOW)
        location(v.rotateZ(Math.toRadians(-45.0)))
    }
    Circle(location, size = 4.0, vertexes =  100) { (v) ->
        color(Color.YELLOW)
        location(v.rotateX(Math.toRadians(45.0)))
    }
    Circle(location, size = 4.0, vertexes =  100) { (v) ->
        color(Color.YELLOW)
        location(v.rotateX(Math.toRadians(-45.0)))
    }
    Circle(location, size = 4.0, vertexes =  100) { (v) ->
        color(Color.YELLOW)
        location(v.rotateX(Math.toRadians(90.0)).rotateY(Math.toRadians(45.0)))
    }
    Circle(location, size = 4.0, vertexes =  100) { (v) ->
        color(Color.YELLOW)
        location(v.rotateX(Math.toRadians(90.0)).rotateY(Math.toRadians(-45.0)))
    }
}

fun dynamo(): Update = {
    update {
        Circle(location, size = 2.0, vertexes =  50) { (v) ->
            val (x, y, z) = v
            val angle = Math.toRadians(time / 25.0)
            color(Color.fromRGB(
                abs(x) % 255, abs(y) % 255, abs(z) % 255
            ))
            location(v.rotateX(angle).rotateY(angle * 2).rotateZ(angle * 3))
        }
    }
}

fun flower(): Update = {
    Square(location, size = Vector(6.0 + sin(time / 1250.0) * 3, 0.0, 6.0)) { (v) ->
        location(v.rotateY(time / 1500.0))
        color(Color.YELLOW)
    }
    Square(location, size = Vector(6.0, 0.0, 6.0 + sin(time / 1500.0) * 3)) { (v) ->
        location(v.rotateY(45.radians + time / 1500.0 + sin(time / 3000.0)))
        color(Color.AQUA)
    }
    Sphere(location, size = 1.5, rings = 12) { (v) ->
        location(v
            .rotateX(time / 300.0)
            .rotateZ(time / 250.0)
            .add(Vector(0.0, 1.0, 0.0))
        )
        color(Color.RED)
    }
    Circle(location, size = sin(time / 500.0) * 10, vertexes = 8) { (v) ->
        location(v.rotateY(time / 1000.0).add(Vector(0.0, abs(sin(time / 500.0)) * 3, 0.0)))
        color(Color.FUCHSIA, 5f)
    }
}

fun cubes(): Update = {
    val angle = time / 7500.0
    Cube(location, size = 3.vec, step = 3) { (v) ->
        location(v.rotateX(angle * 2).rotateY(angle).rotateZ(-angle * 3))
        color(Color.YELLOW, 0.5f)
    }
    Cube(location, size = 3.vec, step = 3) { (v) ->
        location(v.rotateX(angle * 3).rotateY(-angle * 2).rotateZ(angle))
        color(Color.AQUA, 0.5f)
    }
    Cube(location, size = 3.vec, step = 3) { (v) ->
        location(v.rotateX(-angle * 2).rotateY(angle).rotateZ(-angle * 1.5))
        color(Color.RED, 0.5f)
    }
    Cube(location, size = 3.vec, step = 3) { (v) ->
        location(v.rotateX(-angle * 3).rotateY(-angle * 1.5).rotateZ(angle))
        color(Color.LIME, 0.5f)
    }
}

fun floorMarkings(): Update = {
    val data = listOf(
        Color.YELLOW, Color.AQUA, Color.RED
    )
    data.forEachIndexed { i, c ->
        Polygon(location, Particle.REDSTONE, 3.0) { (v) ->
            location(v.rotateY((40 * i).radians + time / 1500.0))
            color(c, 0.5f)
        }
    }
    Polygon(location, Particle.REDSTONE, 1.0, 6) { (v) ->
        location(v.rotateY(time / 1500.0))
        color(Color.LIME, 0.5f)
    }
}

fun triangles(): Update = {
    val data = listOf(
        Color.YELLOW, Color.AQUA, Color.LIME, Color.RED
    )
    data.forEachIndexed { i, c ->
        Triangle(location, Particle.REDSTONE,
            2 + abs(sin(time / 1000.0) * 10),
            2 + abs(sin(time / 1500.0) * 6),
            sin(time / 3000.0) * 3
        ) { (v) ->
            location(v.rotateY((90 * i).radians + cos(time / 2500.0)))
            color(c)
        }
    }
}

private var start = 0L

fun flyStar(event: PlayerMoveEvent) {
    val player = event.player
    if (!player.isGliding) {
        start = Long.MAX_VALUE
        return
    }
    val launch = System.currentTimeMillis()
    if (start > launch)
        start = launch
    if (System.currentTimeMillis() < start + 1000)
        return

    shader {
        location = player.location.add(0.0, 9.0, 0.0)
        fun ParticleBuilder.update(player: Player, v: Vector) {
            color(Color.YELLOW)
            location(v
                .rotateX((90.0 + location.pitch).radians, center = player.eyeLocation)
                .rotateY(-location.yaw.toDouble().radians)
            )
        }
        val size = 4.0
        Circle(location,
            size     = size,
            vertexes = 100
        ) { (v) -> update(player, v) }
        Star(location,
            size   = size,
            points = 3 + abs(sin(time / 2000.0) * 9).toInt(),
            jump   = 1 + abs(sin(time / 500.0) * 3).toInt()
        ) { (v) -> update(player, v) }
    }
}

fun frameAnimation(player: Player) = animation(frames = 80) {
    location = player.location.add(0.0, 1.0, 0.0)
    update {
        before(40) {
            Circle(location, size = 2.0) {
                color(Color.YELLOW)
            }
        }
        between(20..60) {
            Cube(location, size = 2.vec) {
                color(Color.LIME)
            }
        }
        after(40) {
            Star(location, size = 2.0) {
                color(Color.AQUA)
            }
        }
    }
}

fun apiTest(player: Player) {
    shader {
        location = player.location
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
        }
        update(o) {
            location = player.location
            o.location = location
            every(60) {
                fallingBlock(Material.OBSIDIAN) {
                    velocity = Vector(0.0, 3.0, 0.0)
                }
                lightning()
                explosion()
                entity<Spider> {
                    potion(PotionEffectType.INVISIBILITY)
                }
                firework {
                    velocity = 1.vec2d
                    isShotAtAngle = true
                    meta {
                        addEffect {
                            withColor(Color.AQUA, Color.YELLOW)
                            withFlicker()
                            withTrail()
                        }
                    }
                }
            }
        }
        cancel { !player.isOnline }
    }
}

fun sonar(player: Player) {
    animation(2, 50) {
        location = player.location.apply { y = 128.0 }
        update {
            val remainingFrames = frameDuration - framecount
            Circle(location, Particle.END_ROD, remainingFrames * 2.5, min(120, remainingFrames * 3)) { (v) ->
                location(v.rotateY(time / 1000.0))
                count(5)
                extra(remainingFrames / frameDuration.toDouble() + 0.1)
            }
            frame(50) {
                lightning(world.getHighestBlockAt(location).location, true)
            }
        }
    }
}

fun hsbTest(player: Player) {
    shader {
        update {
            location = player.location.apply {
                y += sin(time / 500.0) * 1.5
            }
            circle(
                size = 4 + cos(time / 500.0) * 3,
                vertexes = 15
            ) { (v) ->
                location(v.rotateY(time / 1000.0))
                color(hsb(framecount / 200f, 0.75f, 1f), 2f)
            }
            star(
                size = 10.0,
                vertexes = 100,
                points = 9,
                jump = 5
            ) { (v) ->
                location(v.rotateY(time / 1500.0).add(Vector(0.0, 10.0, 0.0)))
                color(hsb(framecount / 400f, 0.75f, 1f))
            }
        }
        cancel { !player.isOnline }
    }
}