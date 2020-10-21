@file:Suppress("unused")
package xyz.atrius.shadercube.shader

import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.map.MapFont
import org.bukkit.map.MinecraftFont
import org.bukkit.util.Vector
import xyz.atrius.shadercube.data.Coordinate
import xyz.atrius.shadercube.data.Style
import xyz.atrius.shadercube.shape.Shape
import xyz.atrius.shadercube.util.indices
import xyz.atrius.shadercube.util.iterator
import xyz.atrius.shadercube.util.vec

/**
 * @suppress Maps the Minecraft font's character data to a simplified type.
 */
typealias Sprite =
    MapFont.CharacterSprite

/**
 * This is a complex shape type that generates coordinates based off the internally
 * provided Minecraft font. This in effect allows us to create particle systems that
 * match the look of text in-game.
 *
 * @constructor       Constructs a physical text object made up of particles.
 * @property location The center point that this text object is mapped to.
 * @param    text     The text to in the coordinate array.
 * @param    align    The alignment used for this text. Can be either [Align.LEFT],
 *                    [Align.RIGHT] or [Align.CENTER]. Alignment also determines
 *                    the origin point for text is located at.
 * @property size     The size used for the text scale. X controls width while Z
 *                    controls height. Y is ignored.
 * @property particle The particle for which this object will use during rendering cycles.
 * @property style    The style applied to this object.
 *
 * @see Style
 */
class Text(
    override var location: Location,
                 text    : String,
                 align   : Align       = Align.CENTER,
    override val size    : Vector      = 1.vec,
    override var particle: Particle    = Particle.REDSTONE,
    override val style   : Style<Text> = {}
): Shape<Text>() {

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
        vertexes()
        update()
    }

    override fun vertexes() {
        var offset = 0.0
        sprites.forEach { (s, w) ->
            vertices.addAll(render(s, offset - baseOffset))
            offset += w
        }
    }

    private fun render(sprite: Sprite, offset: Double): List<Coordinate> {
        val data = mutableListOf<Coordinate>()
        for ((y, x) in
            sprite.height.indices to sprite.width.indices
        ) if (sprite[y, x]) {
            val p = Vector(
                offset + point.x + x / (5f / size.x),
                         point.y - y / (5f / size.z),
                         point.z
            )
            data += Coordinate(point, p)
        }
        return data
    }
}

/**
 * This is a simple method to create text systems for use in shader scripts. An included
 * benefit of this method is the inclusion of multi-line text support. For a full rundown
 * on this object, please view [Text].
 *
 * @property point    The center point that this text object is mapped to.
 * @param    text     The text to in the coordinate array.
 * @param    align    The alignment used for this text. Can be either [Align.LEFT],
 *                    [Align.RIGHT] or [Align.CENTER]. Alignment also determines
 *                    the origin point for text is located at.
 * @property size     The size used for the text scale. X controls width while Z
 *                    controls height. Y is ignored.
 * @property particle The particle for which this object will use during rendering cycles.
 * @property block    The style applied to this object.
 * @return            An array of all generated text objects.
 *
 * @see Text
 */
fun Shader.text(
    point      : Vector      = this.point,
    text       : String,
    align      : Align       = Align.CENTER,
    size       : Vector      = 1.vec,
    lineSpacing: Double      = 1.25,
    particle   : Particle    = Particle.REDSTONE,
    block      : Style<Text> = {}
) = text.split("\n").mapIndexed { i, it ->
    Text(point.toLocation(world).subtract(0.0, (size.z * lineSpacing) * i, 0.0), it, align, size, particle, block)
}.toTypedArray()

/**
 * This object is used for determining the alignment of a text object.
 * Setting this will realign the text margin and origin points for the
 * given [Text] object.
 */
enum class Align {

    /**
     * @property LEFT Aligns the text margin to the left and sets the
     *                origin point to the left side.
     */
    LEFT,

    /**
     * @property CENTER Aligns the text margin to the center and sets the
     *                  origin point to the center.
     */
    CENTER,

    /**
     * @property RIGHT Aligns the text margin to the right and sets the
     *                 origin point to the right side.
     */
    RIGHT
}
