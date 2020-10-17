package xyz.atrius.shadercube.util

import org.bukkit.Color
import java.awt.Color.HSBtoRGB
import java.awt.Color.RGBtoHSB

operator fun Color.component1(): Int = red

operator fun Color.component2(): Int = green

operator fun Color.component3(): Int = blue

fun rgb(r: Int, g: Int, b: Int): Color =
    Color.fromRGB(r, g, b)

fun rgb(hex: Int): Color =
    Color.fromRGB(hex)

fun hsb(hue: Float, saturation: Float, brightness: Float): Color = Color.fromRGB(
    HSBtoRGB(hue, saturation, brightness) and 0xffffff
)

fun Color.toHSB(): FloatArray =
    RGBtoHSB(red, green, blue, FloatArray(3))

val Color.compliment: Color
    get() = compliments()[0]

fun Color.compliments(count: Int = 1): Array<Color> = toHSB().run {
    val angle = 1f / (count + 1)
    Array(count) { hsb(this[0] + angle * (it + 1), this[1], this[2]) }
}

fun Color.plusCompliments(count: Int = 1): Array<Color> =
    arrayOf(this, *compliments(count))

fun Color.gradient(color: Color, points: Int = 3): Array<Color> {
    val (r, g, b) = color
    val inc = 1.0 / points
    return arrayOf(this, *Array(points) {
        val p = (it + 1) * inc
        Color.fromRGB(lerp(red, r, p), lerp(green, g, p), lerp(blue, b, p)
        )
    }, color)
}

fun Color.gradient(vararg colors: Color, points: Int = 3): Array<Color> {
    val cPoints = arrayOf(this, *colors)
    val values  = arrayListOf<Color>()
    for (i in 1 until cPoints.size)
        values += cPoints[i - 1].gradient(cPoints[i], points)
    return arrayOf(this, *values.toTypedArray())
}

fun Color.gradient(vararg data: Pair<Color, Int>): Array<Color> =
    data.flatMap { gradient(it.first, it.second).toList() }.distinct().toTypedArray()

fun Color.invert(): Color = Color.fromRGB(
    255 - red, 255 - green, 255 - blue
)

fun Color.blend(other: Color, amount: Double = 1.0, mode: BlendFunction): Color = Color.fromRGB(
    standardizeAndCalc(red, other.red, amount, mode),
    standardizeAndCalc(green, other.green, amount, mode),
    standardizeAndCalc(blue, other.blue, amount, mode)
)

private fun standardizeAndCalc(red: Int, r: Int, amount: Double, mode: BlendFunction): Int =
    (clamp(0.0, 1.0, lerp(red.toDouble(), mode(red / 255.0, r / 255.0), amount)) * 255).toInt()