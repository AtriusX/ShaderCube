package xyz.atrius.shadercube.util

import kotlin.math.max

typealias BlendFunction =
    Double.(Double) -> Double

object Blend {

    private const val MIN_VALUE = 1.0 / 255

    val ADD: BlendFunction = { this + it }

    val SUBTRACT: BlendFunction = { this - it }

    val MULTIPLY: BlendFunction = { this * it }

    val DIVIDE: BlendFunction = { this / max(it, MIN_VALUE) }

    val COLOR_BURN: BlendFunction = { 1.0 - (1.0 - this) / max(it, MIN_VALUE) }

    val LINEAR_BURN: BlendFunction = { this + it - 1 }

    val SCREEN: BlendFunction = { 1.0 - (1.0 - this) * (1.0 - it) }

    val DODGE: BlendFunction = { this / max(1.0 - it, MIN_VALUE) }
}