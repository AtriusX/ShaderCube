package xyz.atrius.shadercube

import com.destroystokyo.paper.ParticleBuilder
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.World
import org.bukkit.util.Vector

interface Spatial {

    var point: Location

    val world: World
        get() = point.world

    fun Vector.rotateX(angle: Double, offset: Double = 0.0, center: Vector = point.toVector()) =
        subtract(center).add(Vector(0.0, offset, 0.0)).rotateAroundX(angle).add(center)

    fun Vector.rotateY(angle: Double, offset: Double = 0.0, center: Vector = point.toVector()) =
        subtract(center).add(Vector(offset, 0.0, 0.0)).rotateAroundY(angle).add(center)

    fun Vector.rotateZ(angle: Double, offset: Double = 0.0, center: Vector = point.toVector()) =
        subtract(center).add(Vector(0.0, offset, 0.0)).rotateAroundZ(angle).add(center)

    fun particle(particle: Particle, position: Vector = point.toVector(), block: ParticleBuilder.() -> Unit) {
        val builder = ParticleBuilder(particle).location(position.toLocation(world))
        block(builder)
        builder.spawn()
    }

    fun ParticleBuilder.location(vector: Vector) =
        location(vector.toLocation(world))
}