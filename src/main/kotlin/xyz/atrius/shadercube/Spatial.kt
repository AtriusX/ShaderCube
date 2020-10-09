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
        subtract(center).add(Vector(0.0, offset, offset)).rotateAroundX(angle).add(center)

    fun Vector.rotateY(angle: Double, offset: Double = 0.0, center: Vector = point.toVector()) =
        subtract(center).add(Vector(offset, 0.0, offset)).rotateAroundY(angle).add(center)

    fun Vector.rotateZ(angle: Double, offset: Double = 0.0, center: Vector = point.toVector()) =
        subtract(center).add(Vector(offset, offset, 0.0)).rotateAroundZ(angle).add(center)

    fun particle(particle: Particle, block: ParticleBuilder.() -> Unit, position: Vector = point.toVector()) {
        val builder = ParticleBuilder(particle).location(position.toLocation(world))
        block(builder)
        builder.spawn()
    }

    fun ParticleBuilder.location(vector: Vector) =
        location(vector.toLocation(world))
}