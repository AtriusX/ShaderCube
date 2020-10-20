@file:Suppress("unused", "MemberVisibilityCanBePrivate")
package xyz.atrius.shadercube.data

import org.bukkit.Location
import org.bukkit.util.Vector
import xyz.atrius.shadercube.Spatial
import xyz.atrius.shadercube.shader.Shader
import xyz.atrius.shadercube.util.radians

/**
 * A high-order function used to specify orbital data. This supplies the [Spatial]
 * objects for the center and the current orbital position, as well as a reference
 * to this orbit object.
 *
 * @see Spatial
 * @see Data
 */
typealias OrbitData =
    Spatial.(Data<Orbit>) -> Unit

/**
 * Orbits are a special data object that do not generate any particle systems on
 * their own. Their purpose is to generate a [Coordinate] point which circles around
 * a given center [Location]. The generated point can be used as an anchor point for
 * other shapes or objects.
 *
 * @constructor Constructs an orbit object.
 *
 * @property location The center point that the orbit rotates around.
 * @property size     The distance away from the center point that the orbit sits at.
 * @property rate     The speed at which the orbit rotates around the center at.
 * @property block    The [OrbitData] function to run each time the orbit gets updated.
 */
class Orbit(
    override var location: Location,
             var size    : Double    = 1.0,
             var rate    : Double    = 1.0,
    private  val block   : OrbitData = {}
) : Spatial, Updatable {

    /**
     * @property offset The current angle of the orbit around [location].
     */
    var offset: Double = 0.0

    /**
     * Increments the offset by a single point and implicitly calls [update].
     */
    operator fun inc() = also { this += 1 }

    /**
     * Adds a specified amount to the current offset and then implicitly calls [update].
     *
     * @param amt The amount to add to the offset.
     */
    operator fun plusAssign(amt: Int) =
        plusAssign(amt.toDouble())

    /**
     * Adds a specified amount to the current offset and then calls [update].
     *
     * @param amt The amount to add to the offset.
     */
    operator fun plusAssign(amt: Double) {
        offset += amt
        update()
    }

    /**
     * Decrements the offset by a single point and implicitly calls [update].
     */
    operator fun dec() = also { this -= 1 }

    /**
     * Subtracts a specified amount from the current offset and then implicitly calls [update].
     *
     * @param amt The amount to subtract from the offset.
     */
    operator fun minusAssign(amt: Int) =
        minusAssign(amt.toDouble())

    /**
     * Subtracts a specified amount from the current offset and then calls [update].
     *
     * @param amt The amount to subtract from the offset.
     */
    operator fun minusAssign(amt: Double) {
        offset -= amt
        update()
    }

    override fun update() = block(Data(Coordinate(point, point.clone()
        .rotateY(((System.currentTimeMillis() / 10) * rate + offset).radians, size)), this)
    )
}

/**
 * @param point The center point that the orbit rotates around.
 * @param size  The distance away from the center point that the orbit sits at.
 * @param rate  The speed at which the orbit rotates around the center at.
 * @param block The [OrbitData] function to run each time the orbit gets updated.
 *
 * @see Orbit
 */
fun Shader.orbit(
    point: Vector    = this.point,
    size : Double    = 1.0,
    rate : Double    = 1.0,
    block: OrbitData = {}
) = Orbit(point.toLocation(world), size, rate, block)
