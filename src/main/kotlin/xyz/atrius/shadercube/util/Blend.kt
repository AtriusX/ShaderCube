@file:Suppress("unused")
package xyz.atrius.shadercube.util

import org.bukkit.Color
import xyz.atrius.shadercube.util.Blend.AND
import xyz.atrius.shadercube.util.Blend.OR
import xyz.atrius.shadercube.util.Blend.SHL
import xyz.atrius.shadercube.util.Blend.SHR
import xyz.atrius.shadercube.util.Blend.USHR
import xyz.atrius.shadercube.util.Blend.XOR
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

/**
 * Describes a mathematical function that blends two input numbers into an output value. This function
 * is commonly used in RGB Color blending and is based off the layer blending modes that are supported
 * in GIMP/Glimpse. See [Blend] for a list of pre-supported functions.
 */
typealias BlendFunction =
    Double.(Double) -> Double

/**
 * Similar to [BlendFunction], however this function is meant to take into account the full input colors
 * to computer an output color. See [Blend] for a list of pre-supported functions.
 */
typealias ChromaFunction =
    Color.(Color) -> Color

/**
 * Blend and Chroma functions are used in conjunction with the [blend] system to generate a variety of
 * different color modulations. Most of the included functions are emulations of the various layer blend
 * modes included in programs like GIMP or Glimpse. A set of bitwise functions [AND], [OR], [XOR], [SHL],
 * [SHR], and [USHR] are included in addition to these. It should also be noted that while this object
 * provides a collection of functions out of the box, you are not restricted from creating your own.
 *
 * @see blend
 */
object Blend {

    private const val MIN_VALUE = 1.0 / 255

    /**
     * Blends the values by adding them together.
     */
    val ADD: BlendFunction = { this + it }

    /**
     * Blends the values by subtracting the input from the receiver.
     */
    val SUBTRACT: BlendFunction = { this - it }

    /**
     * Blends the values by multiplying them together.
     */
    val MULTIPLY: BlendFunction = { this * it }

    /**
     * Blends the values by dividing the receiver by the input.
     */
    val DIVIDE: BlendFunction = { this / max(it, MIN_VALUE) }

    /**
     * Produces a color burn blend.
     */
    val COLOR_BURN: BlendFunction = { 1.0 - (1.0 - this) / max(it, MIN_VALUE) }

    /**
     * Produces a linear burn blend.
     */
    val LINEAR_BURN: BlendFunction = { this + it - 1 }

    /**
     * Produces a screen blend.
     */
    val SCREEN: BlendFunction = { 1.0 - (1.0 - it) * (1.0 - this) }

    /**
     * Produces a dodge blend.
     */
    val DODGE: BlendFunction = { this / (1.0 - it) }

    /**
     * Produces a difference blend.
     */
    val DIFFERENCE: BlendFunction = { abs(this - it) }

    /**
     * Produces an exclusion blend.
     */
    val EXCLUSION: BlendFunction = { 0.5 - 2 * (this - 0.5) * (it - 0.5 ) }

    /**
     * Blends the values bu taking the maximum input given.
     */
    val LIGHTEN_ONLY: BlendFunction = { max(this, it) }

    /**
     * Blends the values by taking the minimum input given.
     */
    val DARKEN_ONLY: BlendFunction = { min(this, it) }

    /**
     * Produces an overlay blend.
     */
    val OVERLAY: BlendFunction = { this * (this + 2 * it * (1.0 - this)) }

    /**
     * Produces a soft light blend.
     */
    val SOFT_LIGHT: BlendFunction = { ((1.0 - it) * this + SCREEN(it)) * this }

    /**
     * Produces a hard light blend.
     */
    val HARD_LIGHT: BlendFunction = {
        if (it > 0.5) 1.0 - (1.0 - 2 * (it - 0.5)) * (1.0 - this)
        else          2 * this * it
    }

    /**
     * Produces a vivid light blend.
     */
    val VIVID_LIGHT: BlendFunction = {
        if (this < 0.5) 1.0 - (1.0 - it) / (2 * this)
        else            it / (2 * (1.0 - this))
    }

    /**
     * Produces a linear light blend.
     */
    val LINEAR_LIGHT: BlendFunction = {
        it * if (it > 0.5) (this + 2 * (it - 0.5))
        else               this + 2 * it - 1.0
    }

    /**
     * Produces a pin light blend.
     */
    val PIN_LIGHT: BlendFunction = {
        if (it > 0.5) max(this, 2 * (it - 0.5))
        else          min(this, 2 * it)
    }

    /**
     * Produces a grain extract blend.
     */
    val GRAIN_EXTRACT: BlendFunction = { this - it + 0.5 }

    /**
     * Produces a grain merge blend.
     */
    val GRAIN_MERGE: BlendFunction = { this + it - 0.5 }

    /**
     * Bitwise AND blend function which takes the bits in each input and includes
     * the intersection of both bitsets in the resulting output.
     */
    val AND: BlendFunction = { ((this * 255).toInt() and (it * 255).toInt()) / 255.0 }

    /**
     * Bitwise OR blend function which takes the bits in each input and includes
     * the union of both bitsets in the resulting output.
     */
    val OR: BlendFunction = { ((this * 255).toInt() or (it * 255).toInt()) / 255.0 }

    /**
     * Bitwise XOR blend function which takes the bits in each input and includes
     * the unequal bit values in the resulting output.
     */
    val XOR: BlendFunction = { ((this * 255).toInt() xor (it * 255).toInt()) / 255.0 }

    /**
     * Bitwise left shift function that shifts the base value's bits by the given
     * modulation value.
     */
    val SHL: BlendFunction = { ((this * 255).toInt() shl (it * 255).toInt()) / 255.0 }

    /**
     * Bitwise right shift function that shifts the base value's bits by the given
     * modulation value.
     */
    val SHR: BlendFunction = { ((this * 255).toInt() shr (it * 255).toInt()) / 255.0 }

    /**
     * Bitwise right shift function that shifts the base value's bits by the given
     * modulation value. This is functionally similar to [SHR], however it is included
     * for consistency and because it could potentially have other uses that might
     * not be immediately clear.
     */
    val USHR: BlendFunction = { ((this * 255).toInt() ushr  (it * 255).toInt()) / 255.0 }

    /**
     * Chroma function that takes the input color and presupposes the modulation color's
     * hue onto the output.
     */
    val HUE: ChromaFunction = {
        val (_, b, c) = toHSB()
        val (a)       = it.toHSB()
        hsb(a, b, c)
    }

    /**
     * Chroma function that takes the input color and presupposes the modulation color's
     * saturation onto the output.
     */
    val SATURATION: ChromaFunction = {
        val (a, _, c) = toHSB()
        val (_, b)    = it.toHSB()
        hsb(a, b, c)
    }

    /**
     * Chroma function that takes the the modulation color and presupposes the input color's
     * brightness onto the output.
     */
    val COLOR: ChromaFunction = {
        val (_, _, c) = toHSB()
        val (a, b)    = it.toHSB()
        hsb(a, b, c)
    }

    /**
     * Chroma function that takes the input color and presupposes the modulation color's
     * brightness onto the output. Functionally similar to [COLOR] however done in reverse.
     */
    val LUMINOSITY: ChromaFunction = {
        val (a, b, _) = toHSB()
        val (_, _, c) = it.toHSB()
        hsb(a, b, c)
    }
}
