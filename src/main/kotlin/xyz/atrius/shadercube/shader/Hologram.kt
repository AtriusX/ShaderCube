package xyz.atrius.shadercube.shader

import net.md_5.bungee.api.ChatColor
import org.bukkit.Color
import org.bukkit.entity.ArmorStand
import org.bukkit.util.Vector

typealias Hologram =
    ArmorStand

typealias MultilineHologram =
    Array<Hologram>

val Color.chat: ChatColor
    get() = ChatColor.of(java.awt.Color(this.asRGB()))

fun Shader.hologram(
    point      : Vector = this.point,
    text       : String,
    removeAfter: Long   = -1
) = entity<Hologram>(point, removeAfter) {
    isVisible = false
    isMarker = true
    customName = text
    isCustomNameVisible = true
}

fun Shader.hologram(
           point      : Vector = this.point,
    vararg text       : String,
           removeAfter: Long   = -1,
           space      : Double = 0.3
): MultilineHologram = text.also {
    point.y += space * text.size
}.mapNotNull {
    hologram(point.subtract(Vector(0.0, space, 0.0)), it, removeAfter)
}.toTypedArray()

fun Hologram.hologram(text: String) {
    customName = text
}

fun MultilineHologram.remove() = forEach { it.remove() }
