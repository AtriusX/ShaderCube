package xyz.atrius.shadercube.util

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

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

    val SCREEN: BlendFunction = { 1.0 - (1.0 - it) * (1.0 - this) }

    val DODGE: BlendFunction = { this / max(1.0 - it, MIN_VALUE) }

    val DIFFERENCE: BlendFunction = { abs(this - it) }

    val LIGHTEN_ONLY: BlendFunction = { max(this, it) }

    val DARKEN_ONLY: BlendFunction = { min(this, it) }

    val OVERLAY: BlendFunction = { this * (this + 2 * it * (1.0 - this)) }

    val SOFT_LIGHT: BlendFunction = { ((1.0 - it) * this + SCREEN(it)) * this }

    val HARD_LIGHT: BlendFunction = {
        if (it > 0.5) 1.0 - (1.0 - 2 * (it - 0.5)) * (1.0 - this)
        else          2 * this * it
    }

    val GRAIN_EXTRACT: BlendFunction = { this - it + 0.5 }

    val GRAIN_MERGE: BlendFunction = { this + it - 0.5 }
}
