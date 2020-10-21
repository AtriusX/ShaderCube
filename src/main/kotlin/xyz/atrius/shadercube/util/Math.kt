package xyz.atrius.shadercube.util

import kotlin.math.max
import kotlin.math.min

/**
 * @property radians Gets the radial value for this number.
 */
val Double.radians: Double
    get() = Math.toRadians(this)

/**
 * @property radians Gets the radial value for this number.
 */
val Int.radians: Double
    get() = Math.toRadians(this.toDouble())

/**
 * Helper function for interpolating between two points.
 *
 * @param a The base value of the interpolation.
 * @param b The ending value of the interpolation.
 * @param i The interpolation point.
 * @return  The value at the calculated interpolation point.
 */
fun lerp(a: Double, b: Double, i: Double): Double =
    (a * (1.0 - i)) + (b * i)

/**
 * Helper function for interpolating between two points.
 *
 * @param a The base value of the interpolation.
 * @param b The ending value of the interpolation.
 * @param i The interpolation point.
 * @return  The value at the calculated interpolation point.
 */
fun lerp(a: Int, b: Int, i: Double): Int =
    lerp(a.toDouble(), b.toDouble(), i).toInt()

/**
 * This function clamps a value to be within a given range. Any
 * numbers above or below the range limits will return the range
 * limit for their respective sides.
 *
 * @param min   The minimum value allowed.
 * @param max   The maximum value allowed.
 * @param value The real value given.
 * @return      The value cropped to the allowed range.
 */
fun clamp(min: Double, max: Double, value: Double): Double =
    min(max(min, value), max)

/**
 * This function clamps a value to be within a given range. Any
 * numbers above or below the range limits will return the range
 * limit for their respective sides.
 *
 * @param min   The minimum value allowed.
 * @param max   The maximum value allowed.
 * @param value The real value given.
 * @return      The value cropped to the allowed range.
 */
fun clamp(min: Int, max: Int, value: Int): Int =
    clamp(min.toDouble(), max.toDouble(), value.toDouble()).toInt()
