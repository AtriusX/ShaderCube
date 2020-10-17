package xyz.atrius.shadercube.util

import org.bukkit.Color

operator fun Color.component1() = red

operator fun Color.component2() = green

operator fun Color.component3() = blue

fun rgb(r: Int, g: Int, b: Int) =
        Color.fromRGB(r, g, b)

fun hsb(hue: Float, saturation: Float, brightness: Float) = Color.fromRGB(
        java.awt.Color.HSBtoRGB(hue, saturation, brightness) and 0xffffff
)

fun Color.toHSB(): FloatArray =
        java.awt.Color.RGBtoHSB(red, green, blue, FloatArray(3))

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
    return arrayOf(*Array(points) {
        val p = (it + 1) * inc
        Color.fromRGB(lerp(red, r, p), lerp(green, g, p), lerp(blue, b, p)
        )
    }, color)

}

fun Color.plusGradient(color: Color, points: Int = 3) =
    arrayOf(this, *gradient(color, points))

fun Color.gradients(vararg colors: Color, points: Int = 3): Array<Color> {
    val cPoints = arrayOf(this, *colors)
    val values  = arrayListOf<Color>()
    for (i in 1 until cPoints.size)
        values += cPoints[i - 1].gradient(cPoints[i], points)
    return values.toTypedArray()
}

fun Color.plusGradients(vararg colors: Color, points: Int = 3) =
    arrayOf(this, *gradients(*colors, points = points))