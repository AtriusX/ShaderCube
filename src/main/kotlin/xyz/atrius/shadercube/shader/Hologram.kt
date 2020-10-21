@file:Suppress("unused")
package xyz.atrius.shadercube.shader

import org.bukkit.entity.ArmorStand
import org.bukkit.util.Vector

/**
 * Special designation for armor stands which get used as
 * hologram markers.
 */
typealias Hologram =
    ArmorStand

/**
 * Special designation for a collection of hologram armor
 * stands.
 *
 * @see Hologram
 */
typealias MultilineHologram =
    Array<Hologram>

/**
 * Creates an invisible marker armor stand with a custom name.
 * This armor stand cannot be removed unless killed or removed
 * through the entity timer.
 *
 * @param point       The location that the hologram is spawned at.
 * @param text        The message to display on the hologram.
 * @param removeAfter The number of ticks to wait before removing
 *                    this hologram, or -1 if no timer.
 */
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

/**
 * Creates a series of holograms using invisible marker armor stands.
 * This armor stand cannot be removed unless killed or removed through
 * the entity timer.
 *
 * @param point       The location that the hologram is spawned at.
 * @param text        The messages to display on the hologram.
 * @param removeAfter The number of ticks to wait before removing
 *                    this hologram, or -1 if no timer.
 * @param space       The amount of space between each holograms.
 */
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

/**
 * Removes a series of holograms from the world.
 *
 * @receiver The [MultilineHologram] set to remove.
 */
fun MultilineHologram.remove(): Unit =
    forEach(Hologram::remove)
