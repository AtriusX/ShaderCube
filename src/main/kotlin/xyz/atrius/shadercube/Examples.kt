package xyz.atrius.shadercube

import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.entity.Player
import org.bukkit.util.Vector
import xyz.atrius.shadercube.shader.Update
import xyz.atrius.shadercube.shape.Circle
import xyz.atrius.shadercube.shape.Line
import xyz.atrius.shadercube.shape.Sphere
import xyz.atrius.shadercube.shape.Square
import xyz.atrius.shadercube.util.component1
import xyz.atrius.shadercube.util.component2
import xyz.atrius.shadercube.util.component3
import xyz.atrius.shadercube.util.radians
import kotlin.math.abs
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
    update {
        val eyes = player.location
        val looking = player.getTargetBlock(50)?.location ?: return@update
        Line(eyes, looking, vertexes = 100) { l, _ ->
            val (x, y, z) = l.point.toVector()
            color(Color.fromRGB(
                abs(x) % 255, abs(y) % 255, abs(z) % 255
            ))
        }
    }
}

fun sphere(point: Location): Update = {
    update {
        Sphere(point, size = 4.0) { _, _ ->
            color(Color.YELLOW)
        }
    }
}

fun globe(point: Location): Update = {
    Circle(point, size = 4.0, vertexes =  100) { _, _ ->
        color(Color.YELLOW)
    }
    Circle(point, size = 4.0, vertexes =  100) { _, _ ->
        color(Color.YELLOW)
        location(location()?.toVector()
            ?.rotateX(Math.toRadians(90.0))
            ?: return@Circle)
    }
    Circle(point, size = 4.0, vertexes =  100) { _, _ ->
        color(Color.YELLOW)
        location(location()?.toVector()
            ?.rotateZ(Math.toRadians(90.0))
            ?: return@Circle)
    }
    Circle(point, size = 4.0, vertexes =  100) { _, _ ->
        color(Color.YELLOW)
        location(location()?.toVector()
            ?.rotateZ(Math.toRadians(45.0))
            ?: return@Circle)
    }
    Circle(point, size = 4.0, vertexes =  100) { _, _ ->
        color(Color.YELLOW)
        location(location()?.toVector()
            ?.rotateZ(Math.toRadians(-45.0))
            ?: return@Circle)
    }
    Circle(point, size = 4.0, vertexes =  100) { _, _ ->
        color(Color.YELLOW)
        location(location()?.toVector()
            ?.rotateX(Math.toRadians(45.0))
            ?: return@Circle)
    }
    Circle(point, size = 4.0, vertexes =  100) { _, _ ->
        color(Color.YELLOW)
        location(location()?.toVector()
            ?.rotateX(Math.toRadians(-45.0))
            ?: return@Circle)
    }
    Circle(point, size = 4.0, vertexes =  100) { _, _ ->
        color(Color.YELLOW)
        location(location()?.toVector()
            ?.rotateX(Math.toRadians(90.0))
            ?.rotateY(Math.toRadians(45.0))
            ?: return@Circle)
    }
    Circle(point, size = 4.0, vertexes =  100) { _, _ ->
        color(Color.YELLOW)
        location(location()?.toVector()
            ?.rotateX(Math.toRadians(90.0))
            ?.rotateY(Math.toRadians(-45.0))
            ?: return@Circle)
    }
}

fun dynamo(point: Location): Update = {
    update {
        Circle(point, size = 2.0, vertexes =  50) { _, _ ->
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
    Square(point, size = Vector(6.0 + sin(time / 1250.0) * 3, 0.0, 6.0)) { _, v ->
        location(v.rotateY(time / 1500.0))
        color(Color.YELLOW)
    }
    Square(point, size = Vector(6.0, 0.0, 6.0 + sin(time / 1500.0) * 3)) { _, v ->
        location(v.rotateY(45.radians + time / 1500.0 + sin(time / 3000.0)))
        color(Color.AQUA)
    }
    Sphere(point, size = 1.5, vertexes = 12) { _, v ->
        location(v
                .rotateX(time / 300.0)
                .rotateZ(time / 250.0)
                .add(Vector(0.0, 1.0, 0.0))
        )
        color(Color.RED)
    }
    Circle(point, size = sin(time / 500.0) * 10, vertexes = 8) { _, v ->
        location(v.rotateY(time / 1000.0).add(Vector(0.0, abs(sin(time / 500.0)) * 3, 0.0)))
        color(Color.FUCHSIA, 5f)
    }
}