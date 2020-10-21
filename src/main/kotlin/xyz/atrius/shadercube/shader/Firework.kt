@file:Suppress("unused")
package xyz.atrius.shadercube.shader

import org.bukkit.FireworkEffect
import org.bukkit.entity.Firework
import org.bukkit.inventory.meta.FireworkMeta
import org.bukkit.util.Vector

/**
 * A high-order function specifically for creating and applying data to fireworks
 * before they are created. This function makes use of a [Firework] receiver object
 * that is later spawned into the game.
 */
typealias FireworkBuilder =
    Firework.() ->  Unit

/**
 * An extension of [FireworkBuilder]. This function focuses specifically on a
 * firework entity's metadata.
 */
typealias FireworkMetaBuilder =
    FireworkMeta.() -> Unit

/**
 * An extension of [FireworkBuilder]. This function focuses specifically on the visual
 * effects applied to a firework's design.
 */
typealias FireworkEffectBuilder =
    FireworkEffect.Builder.() -> Unit

/**
 * A simple DSL function and extension of [FireworkBuilder]. This is used to help solve
 * a problem with assigning metadata, as well as to keep things consistent with the API.
 *
 * @receiver   The firework to apply metadata to.
 * @param meta The metadata to apply to this firework.
 */
fun Firework.meta(meta: FireworkMetaBuilder) {
    // Metadata needs to be applied this way to work properly
    fireworkMeta = fireworkMeta.apply(meta)
}

/**
 * A simple DSL function and extension of [FireworkBuilder]. This function streamlines
 * the process of applying effects by processing a [FireworkEffectBuilder] automatically.
 *
 * @receiver     The firework metadata to apply this effect to.
 * @param effect The effect builder to process and apply.
 */
fun FireworkMeta.addEffect(effect: FireworkEffectBuilder) {
    addEffect(FireworkEffect.builder().apply(effect).build())
}

/**
 * A helper function designed for use in shader scripts. This function allows fireworks
 * to be described in a declarative fashion.
 *
 * @param point The location at which the firework is spawned at.
 * @param block The firework data (effects, meta) to apply to this firework.
 * @return      The generated firework entity.
 */
fun Shader.firework(
    point: Vector          = this.point,
    block: FireworkBuilder = {}
) = entity<Firework>(point) {
    this.apply(block)
}
