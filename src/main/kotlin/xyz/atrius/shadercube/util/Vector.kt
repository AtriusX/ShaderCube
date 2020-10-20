@file:Suppress("unused")
package xyz.atrius.shadercube.util

import org.bukkit.util.Vector
import kotlin.math.sign

/**
 * Helper function for helping destructure vectors.
 *
 * @receiver The vector to destructure.
 * @return   The x position of this vector.
 */
operator fun Vector.component1() = x

/**
 * Helper function for helping destructure vectors.
 *
 * @receiver The vector to destructure.
 * @return   The y position of this vector.
 */
operator fun Vector.component2() = y

/**
 * Helper function for helping destructure vectors.
 *
 * @receiver The vector to destructure.
 * @return   The z position of this vector.
 */
operator fun Vector.component3() = z

/**
 * Creates a vector containing the sign digits for each component
 * in the receiver.
 *
 * @receiver The vector to retrieve sign digits for.
 * @return   A vector of the sign digits for each vector component.
 */
private fun Vector.signs(): Vector {
    return Vector(x.sign, y.sign, z.sign)
}

/**
 * @receiver     The number to store in all of the vector's components.
 * @property vec Creates a vector with all components set to the value
 *               stored in the receiver.
 */
val Double.vec: Vector
    get() = Vector(this, this, this)

/**
 * @receiver     The number to store in all of the vector's components.
 * @property vec Creates a vector with all components set to the value
 *               stored in the receiver.
 */
val Int.vec: Vector
    get() = toDouble().vec

/**
 * @receiver     The number to store in the X/Z vector components.
 * @property vec Creates a vector with X and Z components set to
 *               the value stored in the receiver.
 */
val Double.vec2d: Vector
    get() = Vector(this, 0.0, this)

/**
 * @receiver     The number to store in the X/Z vector components.
 * @property vec Creates a vector with X and Z components set to
 *               the value stored in the receiver.
 */
val Int.vec2d: Vector
    get() = toDouble().vec2d
