package xyz.atrius.shadercube.shader

import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.map.MapFont
import org.bukkit.map.MinecraftFont
import org.bukkit.util.Vector
import xyz.atrius.shadercube.shape.Data
import xyz.atrius.shadercube.shape.Shape
import xyz.atrius.shadercube.shape.Style
import xyz.atrius.shadercube.util.indices
import xyz.atrius.shadercube.util.iterator
import xyz.atrius.shadercube.util.vec

typealias Sprite =
    MapFont.CharacterSprite

class Text(
    override var location: Location,
                 text    : String,
                 align   : Align       = Align.CENTER,
    override val size    : Vector      = 1.vec,
    override var particle: Particle    = Particle.REDSTONE,
    override val block   : Style<Text> = {}
): Shape<Text> {
    override val points: Array<Vector> = arrayOf()

    private val data    = MinecraftFont.Font
    private val sprites = if (data.isValid(text)) text
        .mapNotNull { data.getChar(it) }
        .map { it to it.width / (4.0 / size.x) }.toList()
    else throw IllegalStateException("Illegal characters in string: $text")

    private var baseOffset = if (align != Align.LEFT) {
        val base = sprites.sumByDouble { it.second }
        if (align == Align.CENTER) base / 2 else base
    } else 0.0

    init {
        var offset = 0.0
        sprites.forEach { (s, w) ->
            render(s, offset - baseOffset)
            offset += w
        }
    }

    private fun render(sprite: Sprite, offset: Double) {
        for ((y, x) in
            sprite.height.indices to sprite.width.indices
        ) if (sprite[y, x]) {
            val p = Vector(
                offset + point.x + x / (5f / size.x),
                         point.y - y / (5f / size.z),
                         point.z
            )
            particle(particle, p) {
                block(Data(p, this@Text))
            }
        }
    }
}

fun Shader.text(
    point   : Vector      = this.point,
    text    : String,
    align   : Align       = Align.CENTER,
    size    : Vector      = 1.vec,
    particle: Particle    = Particle.REDSTONE,
    block   : Style<Text> = {}
) = Text(point.toLocation(world), text, align, size, particle, block)

@Suppress("unused")
enum class Align {
    LEFT, CENTER, RIGHT
}
