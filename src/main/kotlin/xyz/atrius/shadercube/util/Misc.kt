package xyz.atrius.shadercube.util

import org.bukkit.Color
import java.awt.Color.HSBtoRGB
import java.awt.Color.RGBtoHSB

val Double.radians: Double
    get() = Math.toRadians(this)

val Int.radians: Double
    get() = Math.toRadians(this.toDouble())

fun rgb(r: Int, g: Int, b: Int) =
    Color.fromRGB(r, g, b)

fun hsb(hue: Float, saturation: Float, brightness: Float) = Color.fromRGB(
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