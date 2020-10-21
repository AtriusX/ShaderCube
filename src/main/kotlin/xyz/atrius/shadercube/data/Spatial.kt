package xyz.atrius.shadercube.data

import com.destroystokyo.paper.ParticleBuilder
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.World
import org.bukkit.util.Vector

/**
 * This interface represents one of the most basic building blocks of the Shadercube API. The use of
 * this object underlies most implementations of [Shape][xyz.atrius.shadercube.shape.Shape], as well
 * as [Shader][xyz.atrius.shadercube.shader.Shader]. The primary purpose of this interface is to provide
 * an entrypoint for basic shader information, as well as provide a basic collection of DSL functions
 * to developers for use in shader development.
 *
 * @see xyz.atrius.shadercube.shader.Shader
 * @see xyz.atrius.shadercube.shape.Shape
 */
interface Spatial {

    /**
     * @property location The physical position of this object.
     */
    var location: Location

    /**
     * @property point Converts the physical position to a vector.
     */
    val point: Vector
        get() = location.toVector()

    /**
     * @property world Reference to the world of the physical location.
     */
    val world: World
        get() = location.world

    /**
     * Rotates a provided [Vector] object on the X axis relative to a given
     * center point. This defaults to the most recent [Spatial] context in
     * shader scripts.
     *
     * @receiver     The provided vector to rotate.
     * @param angle  The angle to rotate the vector at.
     * @param offset The offset from the given center point to rotate at.
     * @param center The center point of the rotation.
     * @return       The rotated vector.
     */
    fun Vector.rotateX(angle: Double, offset: Double = 0.0, center: Vector = point) =
        subtract(center).add(Vector(0.0, offset, 0.0)).rotateAroundX(angle).add(center)

    /**
     * Rotates a provided [Vector] object on the X axis relative to a given
     * center point.
     *
     * @receiver     The provided vector to rotate.
     * @param angle  The angle to rotate the vector at.
     * @param offset The offset from the given center point to rotate at.
     * @param center The center point of the rotation.
     * @return       The rotated vector.
     */
    fun Vector.rotateX(angle: Double, offset: Double = 0.0, center: Location) =
        rotateX(angle, offset, center.toVector())

    /**
     * Rotates a provided [Vector] object on the Z axis relative to a given
     * center point. This defaults to the most recent [Spatial] context in
     * shader scripts.
     *
     * @receiver     The provided vector to rotate.
     * @param angle  The angle to rotate the vector at.
     * @param offset The offset from the given center point to rotate at.
     * @param center The center point of the rotation.
     * @return       The rotated vector.
     */
    fun Vector.rotateY(angle: Double, offset: Double = 0.0, center: Vector = point) =
        subtract(center).add(Vector(offset, 0.0, 0.0)).rotateAroundY(angle).add(center)

    /**
     * Rotates a provided [Vector] object on the Y axis relative to a given
     * center point.
     *
     * @receiver     The provided vector to rotate.
     * @param angle  The angle to rotate the vector at.
     * @param offset The offset from the given center point to rotate at.
     * @param center The center point of the rotation.
     * @return       The rotated vector.
     */
    fun Vector.rotateY(angle: Double, offset: Double = 0.0, center: Location) =
        rotateY(angle, offset, center.toVector())

    /**
     * Rotates a provided [Vector] object on the Z axis relative to a given
     * center point. This defaults to the most recent [Spatial] context in
     * shader scripts.
     *
     * @receiver     The provided vector to rotate.
     * @param angle  The angle to rotate the vector at.
     * @param offset The offset from the given center point to rotate at.
     * @param center The center point of the rotation.
     * @return       The rotated vector.
     */
    fun Vector.rotateZ(angle: Double, offset: Double = 0.0, center: Vector = point) =
        subtract(center).add(Vector(0.0, offset, 0.0)).rotateAroundZ(angle).add(center)

    /**
     * Rotates a provided [Vector] object on the Z axis relative to a given
     * center point.
     *
     * @receiver     The provided vector to rotate.
     * @param angle  The angle to rotate the vector at.
     * @param offset The offset from the given center point to rotate at.
     * @param center The center point of the rotation.
     * @return       The rotated vector.
     */
    fun Vector.rotateZ(angle: Double, offset: Double = 0.0, center: Location) =
        rotateZ(angle, offset, center.toVector())

    /**
     * Spawns a particle at a specific location with provided particle metadata.
     *
     * @param particle The particle to spawn.
     * @param position The position of the spawned particle.
     * @param block    The metadata supplied to this particle.
     * @return         The provided particle builder.
     */
    fun particle(
        particle: Particle                   = Particle.REDSTONE,
        position: Location,
        block   : ParticleBuilder.() -> Unit = {}
    ) = ParticleBuilder(particle).location(position).also(block).spawn()

    /**
     * Spawns a particle at a specific location with provided particle metadata.
     *
     * @param particle The particle to spawn.
     * @param position The position of the spawned particle.
     * @param block    The metadata supplied to this particle.
     * @return         The provided particle builder.
     */
    fun particle(
        particle: Particle                   = Particle.REDSTONE,
        position: Vector                     = point,
        block   : ParticleBuilder.() -> Unit = {}
    ) = particle(particle, position.toLocation(world), block)

    /**
     * Sets the location of a particle to the given vector position.
     *
     * @receiver         The particle builder to set the location for.
     * @param coordinate The position to set as the location.
     * @return           The provided particle builder.
     */
    fun ParticleBuilder.location(coordinate: Coordinate) =
        location(coordinate.get())

    /**
     * Sets the location of a particle to the given vector position.
     *
     * @receiver     The particle builder to set the location for.
     * @param vector The position to set as the location.
     * @return       The provided particle builder.
     */
    fun ParticleBuilder.location(vector: Vector) =
        location(vector.toLocation(world))
}
