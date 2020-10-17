package xyz.atrius.shadercube.util

import kotlin.math.max
import kotlin.math.min

val Double.radians: Double
    get() = Math.toRadians(this)

val Int.radians: Double
    get() = Math.toRadians(this.toDouble())

fun lerp(a: Double, b: Double, i: Double): Double =
    (a * (1.0 - i)) + (b * i)

fun lerp(a: Int, b: Int, i: Double): Int =
    lerp(a.toDouble(), b.toDouble(), i).toInt()

fun clamp(min: Double, max: Double, value: Double): Double =
    min(max(min, value), max)

fun clamp(min: Int, max: Int, value: Int): Int =
    clamp(min.toDouble(), max.toDouble(), value.toDouble()).toInt()
