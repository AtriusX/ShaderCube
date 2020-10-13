package xyz.atrius.shadercube

import com.destroystokyo.paper.ParticleBuilder
import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.util.Vector
import xyz.atrius.shadercube.shader.*
import xyz.atrius.shadercube.shape.*
import xyz.atrius.shadercube.util.*
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

fun orbit(point: Location): Update = {
    update {
        val angle = (time / 500.0) % 360
        particle(Particle.REDSTONE) {
            color(Color.YELLOW)
            location(point.toVector()
                .rotateX(angle, 1.0)
                .rotateY(45.0)
            )
        }
        particle(Particle.REDSTONE) {
            color(Color.AQUA)
            location(point.toVector()
                .rotateX(angle, 1.0)
                .rotateY(-45.0)
            )
        }
        particle(Particle.REDSTONE) {
            color(Color.AQUA)
            location(point.toVector()
                .rotateY(angle, 1.0)
                .rotateX(45.0)
            )
        }
        particle(Particle.REDSTONE) {
            color(Color.AQUA)
            location(point.toVector()
                .rotateY(angle, 1.0)
                .rotateX(-45.0)
            )
        }
        particle(Particle.REDSTONE) {
            color(Color.AQUA)
            location(point.toVector()
                .rotateY(angle, 1.0)
                .rotateZ(45.0)
            )
        }
        particle(Particle.REDSTONE) {
            color(Color.AQUA)
            location(point.toVector()
                .rotateY(angle, 1.0)
                .rotateZ(-45.0)
            )
        }
        particle(Particle.REDSTONE) {
            color(Color.AQUA)
            location(point.toVector().rotateZ(angle, 1.0))
        }
        particle(Particle.REDSTONE) {
            color(Color.AQUA)
            location(point.toVector().rotateX(angle, 1.0))
        }
        particle(Particle.REDSTONE) {
            color(Color.AQUA)
            location(point.toVector().rotateY(angle, 1.0))
        }
    }
}

fun rayCast(player: Player): Update = {
    val eyes = player.location
    val looking = player.getTargetBlock(50)?.location ?: eyes
    Line(eyes, looking, vertexes = 100) { (_, l) ->
        val (x, y, z) = l.point.toVector()
        color(Color.fromRGB(
            abs(x) % 255, abs(y) % 255, abs(z) % 255
        ))
    }
}

fun sphere(point: Location): Update = {
    Sphere(point, size = 4.0) {
        color(Color.YELLOW)
    }
}

fun globe(point: Location): Update = {
    Circle(point, size = 4.0, vertexes =  100) {
        color(Color.YELLOW)
    }
    Circle(point, size = 4.0, vertexes =  100) {
        color(Color.YELLOW)
        location(location()?.toVector()
            ?.rotateX(Math.toRadians(90.0))
            ?: return@Circle)
    }
    Circle(point, size = 4.0, vertexes =  100) {
        color(Color.YELLOW)
        location(location()?.toVector()
            ?.rotateZ(Math.toRadians(90.0))
            ?: return@Circle)
    }
    Circle(point, size = 4.0, vertexes =  100) {
        color(Color.YELLOW)
        location(location()?.toVector()
            ?.rotateZ(Math.toRadians(45.0))
            ?: return@Circle)
    }
    Circle(point, size = 4.0, vertexes =  100) {
        color(Color.YELLOW)
        location(location()?.toVector()
            ?.rotateZ(Math.toRadians(-45.0))
            ?: return@Circle)
    }
    Circle(point, size = 4.0, vertexes =  100) {
        color(Color.YELLOW)
        location(location()?.toVector()
            ?.rotateX(Math.toRadians(45.0))
            ?: return@Circle)
    }
    Circle(point, size = 4.0, vertexes =  100) {
        color(Color.YELLOW)
        location(location()?.toVector()
            ?.rotateX(Math.toRadians(-45.0))
            ?: return@Circle)
    }
    Circle(point, size = 4.0, vertexes =  100) {
        color(Color.YELLOW)
        location(location()?.toVector()
            ?.rotateX(Math.toRadians(90.0))
            ?.rotateY(Math.toRadians(45.0))
            ?: return@Circle)
    }
    Circle(point, size = 4.0, vertexes =  100) {
        color(Color.YELLOW)
        location(location()?.toVector()
            ?.rotateX(Math.toRadians(90.0))
            ?.rotateY(Math.toRadians(-45.0))
            ?: return@Circle)
    }
}

fun dynamo(point: Location): Update = {
    update {
        Circle(point, size = 2.0, vertexes =  50) {
            val loc = location()?.toVector() ?: return@Circle
            val (x, y, z) = loc
            val angle = Math.toRadians(time / 25.0)
            color(Color.fromRGB(
                abs(x) % 255, abs(y) % 255, abs(z) % 255
            ))
            location(loc.rotateX(angle).rotateY(angle * 2).rotateZ(angle * 3))
        }
    }
}

fun flower(point: Location): Update = {
    Square(point, size = Vector(6.0 + sin(time / 1250.0) * 3, 0.0, 6.0)) { (v) ->
        location(v.rotateY(time / 1500.0))
        color(Color.YELLOW)
    }
    Square(point, size = Vector(6.0, 0.0, 6.0 + sin(time / 1500.0) * 3)) { (v) ->
        location(v.rotateY(45.radians + time / 1500.0 + sin(time / 3000.0)))
        color(Color.AQUA)
    }
    Sphere(point, size = 1.5, vertexes = 12) { (v) ->
        location(v
            .rotateX(time / 300.0)
            .rotateZ(time / 250.0)
            .add(Vector(0.0, 1.0, 0.0))
        )
        color(Color.RED)
    }
    Circle(point, size = sin(time / 500.0) * 10, vertexes = 8) { (v) ->
        location(v.rotateY(time / 1000.0).add(Vector(0.0, abs(sin(time / 500.0)) * 3, 0.0)))
        color(Color.FUCHSIA, 5f)
    }
}

fun cubes(point: Location): Update = {
    val angle = time / 7500.0
    Cube(point, size = 3.vec, step = 3) { (v) ->
        location(v.rotateX(angle * 2).rotateY(angle).rotateZ(-angle * 3))
        color(Color.YELLOW, 0.5f)
    }
    Cube(point, size = 3.vec, step = 3) { (v) ->
        location(v.rotateX(angle * 3).rotateY(-angle * 2).rotateZ(angle))
        color(Color.AQUA, 0.5f)
    }
    Cube(point, size = 3.vec, step = 3) { (v) ->
        location(v.rotateX(-angle * 2).rotateY(angle).rotateZ(-angle * 1.5))
        color(Color.RED, 0.5f)
    }
    Cube(point, size = 3.vec, step = 3) { (v) ->
        location(v.rotateX(-angle * 3).rotateY(-angle * 1.5).rotateZ(angle))
        color(Color.LIME, 0.5f)
    }
}

fun floorMarkings(point: Location): Update = {
    val data = listOf(
        Color.YELLOW, Color.AQUA, Color.RED
    )
    data.forEachIndexed { i, c ->
        Polygon(point, Particle.REDSTONE, 3.0) { (v) ->
            location(v.rotateY((40 * i).radians + time / 1500.0))
            color(c, 0.5f)
        }
    }
    Polygon(point, Particle.REDSTONE, 1.0, 6) { (v) ->
        location(v.rotateY(time / 1500.0))
        color(Color.LIME, 0.5f)
    }
}

fun triangles(point: Location): Update = {
    val data = listOf(
        Color.YELLOW, Color.AQUA, Color.LIME, Color.RED
    )
    data.forEachIndexed { i, c ->
        Triangle(point, Particle.REDSTONE,
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
        point = player.location.add(0.0, 9.0, 0.0)
        fun ParticleBuilder.update(player: Player, v: Vector) {
            color(Color.YELLOW)
            location(v.rotateX((90.0 + point.pitch).radians,
                    center = player.eyeLocation.toVector())
                    .rotateY(-point.yaw.toDouble().radians)
            )
        }
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

fun frameAnimation(player: Player) = animation(frames = 80) {
    point = player.location.add(0.0, 1.0, 0.0)
    update {
        before(40) {
            Circle(point, size = 2.0) {
                color(Color.YELLOW)
            }
        }
        between(20..60) {
            Cube(point, size = 2.vec) {
                color(Color.LIME)
            }
        }
        after(40) {
            Star(point, size = 2.0) {
                color(Color.AQUA)
            }
        }
    }
}