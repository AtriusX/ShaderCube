package xyz.atrius.shadercube.shader

import org.bukkit.entity.LivingEntity
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

typealias PotionBuilder =
    PotionEffectData.() -> Unit

data class PotionEffectData(
    var duration : Int     = 10000,
    var amplifier: Int     = 1,
    var ambient  : Boolean = true,
    var particles: Boolean = ambient,
    var icon     : Boolean = ambient
)

fun LivingEntity.potion(effect: PotionEffectType, block: PotionBuilder = {}) {
    val (duration, amplifier, ambient, particles, icon) = PotionEffectData().apply(block)
    addPotionEffect(PotionEffect(effect, duration, amplifier, ambient, particles, icon))
}
