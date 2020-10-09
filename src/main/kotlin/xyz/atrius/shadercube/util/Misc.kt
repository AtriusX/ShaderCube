package xyz.atrius.shadercube.util

val Double.radians: Double
    get() = Math.toRadians(this)

val Int.radians: Double
    get() = Math.toRadians(this.toDouble())