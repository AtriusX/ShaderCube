@file:Suppress("unused")
package xyz.atrius.shadercube.util

import net.md_5.bungee.api.ChatColor
import org.bukkit.Color
import java.awt.Color.HSBtoRGB
import java.awt.Color.RGBtoHSB

/**
 * Helper function which enables destructuring of [Color] objects.
 *
 * @receiver The color to destructure.
 * @return   The color's red component.
 */
operator fun Color.component1(): Int = red

/**
 * Helper function which enables destructuring of [Color] objects.
 *
 * @receiver The color to destructure.
 * @return   The color's green component.
 */
operator fun Color.component2(): Int = green

/**
 * Helper function which enables destructuring of [Color] objects.
 *
 * @receiver The color to destructure.
 * @return   The color's blue component.
 */
operator fun Color.component3(): Int = blue

/**
 * Shorthand helper method that offers a simpler way to generate
 * colors. This can be used in shader scripts to quickly and easily
 * generate colors.
 *
 * @param r The red component of the color ranging from 0-255.
 * @param g The green component of the color ranging from 0-255.
 * @param b The blue component of the color ranging from 0-255.
 * @return  The resulting color.
 */
fun rgb(r: Int, g: Int, b: Int): Color =
    Color.fromRGB(r, g, b)

/**
 * Shorthand helper method that offers a simpler way to generate
 * colors. This can be used in shader scripts to quickly and easily
 * generate colors.
 *
 * @param hex The int hex code of the color ranging from 0x0-0xFFFFFF
 * @return    The resulting color.
 */
fun rgb(hex: Int): Color =
    Color.fromRGB(hex)

/**
 * Shorthand helper method that adds support for HSB color generation.
 *
 * @param hue        The hue of the color. Can be any value.
 * @param saturation The color's saturation component ranging from 0-1.
 * @param brightness The color's brightness component ranging from 0-1.
 * @return           The resulting color.
 */
fun hsb(hue: Float, saturation: Float, brightness: Float): Color = Color.fromRGB(
    HSBtoRGB(hue, saturation, brightness) and 0xffffff
)

/**
 * Helper function that converts a color into it's corresponding HSB
 * color components.
 *
 * @receiver The color to break down into HSB components.
 * @return   A 3-index float array containing the Hue, Saturation,
 *           and Brightness components in that order.
 */
fun Color.toHSB(): FloatArray =
    RGBtoHSB(red, green, blue, FloatArray(3))

/**
 * Helper function that enables the generation of colors in the YUV
 * colorspace. This function does not do the conversion itself, rather
 * it calls the conversion after remapping the input numbers from 0-255
 * to their expected ranges.
 *
 * @param y The Y (Brightness) component of the color.
 * @param u The cR (Red) component of the color.
 * @param v The cB (Blue) component of the color.
 * @return  The resulting color.
 */
fun yuv(y: Int, u: Int, v: Int): Color = yuv(
    y / 255f, (u - 128) / 51f, (v - 128) / 51f
)

/**
 * Helper function that enables the generation of colors in the YUV
 * colorspace. The YUV (YcRcB) colorspace is a colorspace that generates
 * colors that have separate brightness and color components. The Y
 * component ranges from 0-1, while the UV components both have a range
 * of -5-5.
 *
 * @param y The Y (Brightness) component of the color.
 * @param u The cR (Red) component of the color.
 * @param v The cB (Blue) component of the color.
 * @return  The resulting color.
 */
fun yuv(y: Float, u: Float, v: Float): Color {
    val r = ((y + 0.000 * u + 1.140 * v) * 255)
    val g = ((y - 0.396 * u - 0.581 * v) * 255)
    val b = ((y + 2.029 * u + 0.000 * v) * 255)
    return Color.fromRGB(
        clamp(0, 255, r.toInt()),
        clamp(0, 255, g.toInt()),
        clamp(0, 255, b.toInt())
    )
}

/**
 * Helper function that converts a color into it's corresponding UV
 * color components in standard 0-255 notation.
 *
 * @receiver The color to break down into YUV components.
 * @return   A 3-index int array containing the Brightness,
 *           Red, and Blue components in that order.
 *
 */
fun Color.toYUV(): IntArray {
    val y  = (0.257f * red)  + (0.504f * green) + (0.098f * blue) + 16
    val cR = (0.439f * red)  - (0.368f * green) - (0.071f * blue) + 128
    val cB = -(0.148f * red) - (0.291f * green) + (0.439f * blue) + 128
    return intArrayOf(y.toInt(), cR.toInt(), cB.toInt())
}

/**
 * @property compliment The color which has a hue opposite of the receiver.
 *
 * @receiver The input color.
 * @return   The complimentary color of the input.
 */
val Color.compliment: Color
    get() = compliments()[0]

/**
 * Generates a set of complimentary colors based off the receiver. This
 * function does not include the receiver in its output. If you need the
 * receiver, please use [plusCompliments].
 *
 * @receiver    The base color to receive the compliments of.
 * @param count The number of compliments to generate. The number given
 *              will also determine how distinct each compliment is from
 *              each other.
 * @return      An array containing all the requested compliments.
 */
fun Color.compliments(count: Int = 1): Array<Color> = toHSB().run {
    val angle = 1f / (count + 1)
    Array(count) { hsb(this[0] + angle * (it + 1), this[1], this[2]) }
}


/**
 * Generates a set of complimentary colors based off the receiver. This
 * function includes the receiver in its output. If you do not need the
 * receiver, please use [compliments].
 *
 * @receiver    The base color to receive the compliments of.
 * @param count The number of compliments to generate. The number given
 *              will also determine how distinct each compliment is from
 *              each other.
 * @return      An array containing all the receiver plus requested compliments.
 */
fun Color.plusCompliments(count: Int = 1): Array<Color> =
    arrayOf(this, *compliments(count))

/**
 * Helper function that generates a linear gradient from the receiver
 * to another specified color.
 *
 * @receiver     The beginning color of the gradient.
 * @param color  The end color of the gradient.
 * @param points The number of color points to generate between the beginning
 *               and end of the gradient.
 * @return       An array containing the beginning, end, and all intermediary
 *               color points.
 */
fun Color.gradient(color: Color, points: Int = 3): Array<Color> {
    val (r, g, b) = color
    val inc = 1.0 / points
    return arrayOf(this, *Array(points) {
        val p = (it + 1) * inc
        Color.fromRGB(lerp(red, r, p), lerp(green, g, p), lerp(blue, b, p)
        )
    }, color)
}

/**
 * Helper function that generates a series of linear gradients from one color
 * to the next color in line.
 *
 * @receiver     The beginning color of the gradient.
 * @param colors The color set for each block of the gradient.
 * @param points The number of color points to generate for each gradient block.
 * @return       An array containing the beginning, end, and all intermediary
 *               color points.
 */
fun Color.gradient(vararg colors: Color, points: Int = 3): Array<Color> {
    val cPoints = arrayOf(this, *colors)
    val values  = arrayListOf<Color>()
    for (i in 1 until cPoints.size)
        values += cPoints[i - 1].gradient(cPoints[i], points)
    return arrayOf(this, *values.toTypedArray())
}

/**
 * Helper function that generates a series of linear gradients from one color
 * to the next color in line. This method allows you to generate gradient sets
 * with a dynamic number of color points for each gradient block.
 *
 * @receiver   The beginning color of the gradient.
 * @param data The color set and point count for each block of the gradient.
 * @return     An array containing the beginning, end, and all intermediary
 *             color points.
 */
fun Color.gradient(vararg data: Pair<Color, Int>): Array<Color> =
    data.flatMap { gradient(it.first, it.second).toList() }.distinct().toTypedArray()

/**
 * Generates a color corresponding to the inverted values of the receiver.
 *
 * @receiver The color to invert.
 * @return   The inverted color.
 */
fun Color.invert(): Color = Color.fromRGB(
    255 - red, 255 - green, 255 - blue
)

/**
 * Blends an input color together with a modulating color based on the formula supplied
 * by [mode]. This function focuses on the application of [BlendFunctions][BlendFunction].
 * You can supply your own or use one of the provided functions. To see a list of these,
 * check the [Blend] object.
 *
 * @receiver     The color to apply blending to.
 * @param other  The modulation color for the blend function.
 * @param amount The amount of blending to apply (this interpolates between the
 *               original and blended color values).
 * @param mode   The blend mode to apply to the receiver.
 *
 * @see Blend
 */
fun Color.blend(other: Color, amount: Double = 1.0, mode: BlendFunction): Color = Color.fromRGB(
    standardizeAndCalc(red, other.red, amount, mode),
    standardizeAndCalc(green, other.green, amount, mode),
    standardizeAndCalc(blue, other.blue, amount, mode)
)

/**
 * Blends an input color together with a modulating color based on the formula supplied
 * by [mode]. This function focuses on the application of [ChromaFunctions][ChromaFunction].
 * You can supply your own or use one of the provided functions. To see a list of these,
 * check the [Blend] object.
 *
 * @receiver     The color to apply blending to.
 * @param other  The modulation color for the blend function.
 * @param amount The amount of blending to apply (this interpolates between the
 *               original and blended color values).
 * @param mode   The blend mode to apply to the receiver.
 *
 * @see Blend
 */
@JvmName("blendChroma")
fun Color.blend(other: Color, amount: Double = 1.0, mode: ChromaFunction): Color = mode(other).also {
    red = lerp(this.red, it.red, amount)
    green = lerp(this.green, it.green, amount)
    blue = lerp(this.blue, it.blue, amount)
}

/**
 * @suppress
 * Used internally to convert between standard 0-255 notation and 0-1 notation. The value in this function
 * is blended and interpolated before being converted back to 0-255 notation.
 */
private fun standardizeAndCalc(value: Int, other: Int, amount: Double, mode: BlendFunction): Int =
    (clamp(0.0, 1.0, lerp(value.toDouble(), mode(value / 255.0, other / 255.0), amount)) * 255).toInt()

/**
 * @property chat Converts this color into its equivalent [ChatColor] value.
 */
val Color.chat: ChatColor
    get() = ChatColor.of(java.awt.Color(this.asRGB()))