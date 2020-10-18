package xyz.atrius.shadercube.shader

import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.map.MapFont
import org.bukkit.map.MinecraftFont

class Text(
    val location: Location,
    message : String,
) {
    val data = MinecraftFont.Font
    val point = location.toVector()

    init {
        var offset = 0.0
        val sprites = if (data.isValid(message))
            message.mapNotNull { data.getChar(it) } else null
        sprites?.forEachIndexed { _, s ->
            render(s, offset)
            offset += s.width / 4.0
        }
    }

    private fun render(sprite: MapFont.CharacterSprite, offset: Double) {
        for (y in 0 until sprite.height)
            for (x in 0 until sprite.width) {
                if (sprite[y, x]) location.world.spawnParticle(
                    Particle.REDSTONE, offset + location.x + x / 5f, location.y - y / 5f, 0.0, 1, Particle.DustOptions(Color.RED, 1f)
                )
            }
    }
}

fun Shader.text(text: String) {

}