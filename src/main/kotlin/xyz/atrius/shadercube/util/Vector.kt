package xyz.atrius.shadercube.util

import org.bukkit.util.Vector

operator fun Vector.component1() = blockX

operator fun Vector.component2() = blockY

operator fun Vector.component3() = blockZ

val Double.vec: Vector
    get() = Vector(this, this, this)

val Int.vec: Vector
    get() = toDouble().vec

val Double.vec2d: Vector
    get() = Vector(this, 0.0, this)

val Int.vec2d: Vector
    get() = toDouble().vec2d