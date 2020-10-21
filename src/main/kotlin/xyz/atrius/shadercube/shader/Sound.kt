@file:Suppress("unused")
package xyz.atrius.shadercube.shader

import org.bukkit.Sound
import org.bukkit.SoundCategory
import org.bukkit.util.Vector
import xyz.atrius.shadercube.data.Spatial

/**
 * A high-order function specifically for creating and applying data to
 * audio before it is played. This function makes use of a custom [SoundData]
 * receiver object that is later destructured and applied to a playsound event.
 *
 * @see SoundData
 */
typealias SoundBuilder =
    SoundData.() -> Unit

/**
 * A constructive data type used in [sound] to create declarative sounds before
 * they are played to nearby entities. All provided information in this constructor
 * is considered optional.
 *
 * @property point    The location at which the sound is played.
 * @property category The volume category this sound is sent to.
 * @property volume   The volume that this sound will be played at.
 * @property pitch    The pitch that this sound will be played at.
 */
data class SoundData(
    var point   : Vector?       = null,
    var category: SoundCategory = SoundCategory.MASTER,
    var volume  : Double        = 1.0,
    var pitch   : Double        = 1.0
)

/**
 * A helper function designed for use in shader scripts. This function allows sounds
 * to be described in a declarative fashion.
 *
 * @param sound The sound to be played.
 * @param block The optional sound builder to use in constructing this sound.
 *
 * @see SoundBuilder
 * @see SoundData
 */
fun Spatial.sound(sound: Sound, block: SoundBuilder = {}) {
    // Destructure the data after it has been created
    val (point, category, volume, pitch) = SoundData().apply(block)
    // Play the sound with the given sound data
    world.playSound(
        point?.toLocation(world) ?: this.location, sound,
        category, volume.toFloat(), pitch.toFloat()
    )
}
