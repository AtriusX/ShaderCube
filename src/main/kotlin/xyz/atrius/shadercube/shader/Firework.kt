package xyz.atrius.shadercube.shader

import org.bukkit.FireworkEffect
import org.bukkit.entity.Firework
import org.bukkit.inventory.meta.FireworkMeta
import org.bukkit.util.Vector

typealias FireworkBuilder =
    Firework.() ->  Unit

typealias FireworkMetaBuilder =
    FireworkMeta.() -> Unit

typealias FireworkEffectBuilder =
    FireworkEffect.Builder.() -> Unit

fun Firework.meta(meta: FireworkMetaBuilder) {
    // Metadata needs to be applied this way to work properly
    fireworkMeta = fireworkMeta.apply(meta)
}

fun FireworkMeta.addEffect(effect: FireworkEffectBuilder) {
    addEffect(FireworkEffect.builder().apply(effect).build())
}

fun Shader.firework(
    point: Vector          = this.point.toVector(),
    block: FireworkBuilder = {}
) = entity<Firework>(point) {
    this.apply(block)
}
