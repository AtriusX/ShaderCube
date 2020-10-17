package xyz.atrius.shadercube.util

val Double.radians: Double
    get() = Math.toRadians(this)

val Int.radians: Double
    get() = Math.toRadians(this.toDouble())

fun lerp(a: Double, b: Double, i: Double): Double =
    (a * (1.0 - i)) + (b * i)

fun lerp(a: Int, b: Int, i: Double): Int =
    lerp(a.toDouble(), b.toDouble(), i).toInt()