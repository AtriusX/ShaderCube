package xyz.atrius.shadercube.shader

import org.bukkit.Sound
import org.bukkit.SoundCategory
import org.bukkit.util.Vector
import xyz.atrius.shadercube.Spatial

typealias SoundBuilder =
    SoundData.() -> Unit

data class SoundData(
    var point   : Vector?       = null,
    var category: SoundCategory = SoundCategory.MASTER,
    var volume  : Double        = 1.0,
    var pitch   : Double        = 1.0
)

fun Spatial.sound(sound: Sound? = null, block: SoundBuilder) {
    val (point, category, volume, pitch) = SoundData().apply(block)
    world.playSound(
        point?.toLocation(world) ?: this.point, sound ?: return,
        category, volume.toFloat(), pitch.toFloat()
    )
}