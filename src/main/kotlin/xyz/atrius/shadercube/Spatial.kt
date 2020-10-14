package xyz.atrius.shadercube

import com.destroystokyo.paper.ParticleBuilder
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.World
import org.bukkit.util.Vector

interface Spatial {

    var location: Location

    val point: Vector
        get() = location.toVector()

    val world: World
        get() = location.world

    fun Vector.rotateX(angle: Double, offset: Double = 0.0, center: Vector = point) =
        subtract(center).add(Vector(0.0, offset, 0.0)).rotateAroundX(angle).add(center)

    fun Vector.rotateX(angle: Double, offset: Double = 0.0, center: Location) =
        rotateX(angle, offset, center.toVector())

    fun Vector.rotateY(angle: Double, offset: Double = 0.0, center: Vector = point) =
        subtract(center).add(Vector(offset, 0.0, 0.0)).rotateAroundY(angle).add(center)

    fun Vector.rotateY(angle: Double, offset: Double = 0.0, center: Location) =
        rotateY(angle, offset, center.toVector())

    fun Vector.rotateZ(angle: Double, offset: Double = 0.0, center: Vector = point) =
        subtract(center).add(Vector(0.0, offset, 0.0)).rotateAroundZ(angle).add(center)

    fun Vector.rotateZ(angle: Double, offset: Double = 0.0, center: Location) =
        rotateZ(angle, offset, center.toVector())

    fun particle(particle: Particle, position: Location, block: ParticleBuilder.() -> Unit) =
        ParticleBuilder(particle).location(position).also(block).spawn()

    fun particle(particle: Particle, position: Vector = point, block: ParticleBuilder.() -> Unit) =
        particle(particle, position.toLocation(world), block)

    fun ParticleBuilder.location(vector: Vector) =
        location(vector.toLocation(world))
}

