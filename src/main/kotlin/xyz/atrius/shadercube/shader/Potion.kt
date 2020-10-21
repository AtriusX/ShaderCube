package xyz.atrius.shadercube.shader

import org.bukkit.entity.LivingEntity
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

/**
 * A high-order function specifically for creating and applying data to potion effects
 * before they are created. This function makes use of a custom [PotionEffectData]
 * receiver object that is later destructured and applied to a potion effect.
 */
typealias PotionBuilder =
    PotionEffectData.() -> Unit

/**
 * A constructive data type used in [potion] to create declarative potion effects before
 * they are created. All provided information in this constructor is considered optional.
 *
 * @property duration  The duration of the potion effect.
 * @property amplifier The strength of the potion effect.
 * @property ambient   Whether the potion effect is considered ambient or not.
 * @property particles Whether or not the potion effect spawns particles.
 * @property icon      Whether or not the potion effect has a HUD idcon.
 */
data class PotionEffectData(
    var duration : Int     = 10000,
    var amplifier: Int     = 1,
    var ambient  : Boolean = true,
    var particles: Boolean = true,
    var icon     : Boolean = ambient
)

/**
 * A helper function designed for use in shader scripts. This function allows potion
 * effects to be described in a declarative fashion.
 *
 * @param effect The type of the potion effect.
 * @param block  The optional potion builder to use in constructing this potion effect.
 *
 * @see PotionBuilder
 * @see PotionEffectData
 */
fun LivingEntity.potion(effect: PotionEffectType, block: PotionBuilder = {}) {
    // Destructure the data after it is created
    val (duration, amplifier, ambient, particles, icon) = PotionEffectData().apply(block)
    // Apply the potion effect with the given data
    addPotionEffect(PotionEffect(effect, duration, amplifier, ambient, particles, icon))
}
