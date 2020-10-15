package xyz.atrius.shadercube.util

import org.bukkit.Color
import java.awt.Color.HSBtoRGB

val Double.radians: Double
    get() = Math.toRadians(this)

val Int.radians: Double
    get() = Math.toRadians(this.toDouble())

fun rgb(r: Int, g: Int, b: Int) =
    Color.fromRGB(r, g, b)

fun hsb(hue: Float, saturation: Float, brightness: Float) = Color.fromRGB(
    HSBtoRGB(hue, saturation, brightness) and 0xffffff
)
