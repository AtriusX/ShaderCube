package xyz.atrius.shadercube

import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.entity.Player
import xyz.atrius.shadercube.shader.Line
import xyz.atrius.shadercube.shader.Sphere
import xyz.atrius.shadercube.shader.shader
import xyz.atrius.shadercube.shape.Circle
import xyz.atrius.shadercube.util.component1
import xyz.atrius.shadercube.util.component2
import xyz.atrius.shadercube.util.component3
import kotlin.math.abs

fun orbit(point: Location) = shader {
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

fun rayCast(player: Player) = shader {
    update {
        val eyes = player.location
        val looking = player.getTargetBlock(50)?.location ?: return@update
        Line(eyes, looking, vertexes = 100) {
            val (x, y, z) = it.point.toVector()
            color(Color.fromRGB(
                abs(x) % 255, abs(y) % 255, abs(z) % 255
            ))
        }
    }
}

fun sphere(point: Location) = shader {
    update {
        Sphere(point, size = 4.0) { _, _ ->
            color(Color.YELLOW)
        }
    }
}

fun globe(point: Location) = shader {
    update {
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
}

fun dynamo(point: Location) = shader {
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