@file:Suppress("unused", "MemberVisibilityCanBePrivate")
package xyz.atrius.shadercube.data

import org.bukkit.Location
import org.bukkit.util.Vector

/**
 * Coordinates are a [Vector]-based data type that are used internally within Shadercube for the
 * purpose of preserving both world-aligned vectors as well as a center/relative vector pair. This
 * allows users to apply transformations to the underlying vector seamlessly without the need to
 * apply confusing mathematics or boilerplate. At any point the underlying center and relative
 * vectors can be adjusted independently of one another to accomplish different purposes.
 *
 * @constructor Constructs an instance of [Coordinate] with underlying [Vector] data.
 *
 * @property center The underlying center point of this coordinate, relative coordinate data is
 *                  an offset to this.
 * @param    worldX The absolute X position of the relative point. [Center][center] is subtracted
 *                  from this to produce the [relative] offset.
 * @param    worldY The absolute X position of the relative point. [Center][center] is subtracted
 *                  from this to produce the [relative] offset.
 * @param    worldZ The absolute Z position of the relative point. [Center][center] is subtracted
 *                  from this to produce the [relative] offset.
 *
 * @see Vector
 */
class Coordinate(
    var center: Vector,
        worldX: Double,
        worldY: Double,
        worldZ: Double
) : Vector(worldX, worldY, worldZ) {

    /**
     * @constructor Converts the given [Vector] positions into a [Coordinate] with center
     *              and relative data.
     *
     * @param center   The underlying center point of this coordinate, relative coordinate data is
     *                 an offset to this.
     * @param relative The absolute position of the relative point. [Center][center] is subtracted
     *                 from this to produce the [relative] offset.
     *
     * @see Coordinate
     */
    constructor(center: Vector, relative: Vector) : this(center, relative.x, relative.y, relative.z)

    /**
     * @property relative The overall relative offset from [center].
     */
    val relative: Vector = Vector(x - center.x, y - center.y, z - center.z)

    /**
     * @property relX The relative X offset from [center]. This property updates with [relative].
     */
    val relX: Double
        get() = relative.x

    /**
     * @property relY The relative Y offset from [center]. This property updates with [relative].
     */
    val relY: Double
        get() = relative.y

    /**
     * @property relZ The relative Z offset from [center]. This property updates with [relative].
     */
    val relZ: Double
        get() = relative.z

    /**
     * Re-centers this coordinate on the given center point. This method can be referenced using
     * either standard or infix notation.
     *
     * @param center The new center point.
     * @return       This coordinate.
     */
    infix fun centerOn(center: Vector): Coordinate = also {
        this.center = center
    }

    /**
     * Re-centers this coordinate on the given center point. This method can be referenced using
     * either standard or infix notation.
     *
     * @param location The [Location] instance to convert into the new center point.
     * @return         This coordinate.
     */
    infix fun centerOn(location: Location): Coordinate =
        centerOn(location.toVector())

    /**
     * Rotates the X value of this coordinate around the center. This method can be referenced using
     * either standard or infix notation.
     *
     * @param angle The number of radians to rotate this coordinate by.
     * @return      This coordinate.
     */
    override infix fun rotateAroundX(angle: Double): Coordinate = also {
        relative.rotateAroundX(angle)
    }

    /**
     * Rotates the X value of this coordinate around the center. This method can be referenced using
     * either standard or infix notation.
     *
     * @param angle The number of radians to rotate this coordinate by.
     * @return      This coordinate.
     */
    override infix fun rotateAroundY(angle: Double): Coordinate = also {
        relative.rotateAroundY(angle)
    }

    /**
     * Rotates the Z value of this coordinate around the center. This method can be referenced using
     * either standard or infix notation.
     *
     * @param angle The number of radians to rotate this coordinate by.
     * @return      This coordinate.
     */
    override infix fun rotateAroundZ(angle: Double): Coordinate = also {
        relative.rotateAroundZ(angle)
    }

    /**
     * Calculates the actual position of the coordinate. This is typically called by Shadercube internally,
     * but may be used explicitly if you need to.
     *
     * @return The actual position of the coordinate.
     */
    fun get(): Vector =
        center.clone().add(relative)
}