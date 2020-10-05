package xyz.atrius.shadercube.shader

import org.bukkit.Location
import org.bukkit.Rotation
import org.bukkit.util.Vector
import java.awt.Dimension

interface Shader {

    val time: Long
        get() = System.currentTimeMillis()

    var position: Location

    var rotation: Vector

    var size: Vector

    var centered: Boolean

    var setup: () -> Unit

    var update: () -> Unit
}

infix fun Int.by(other: Int) =
    Vector(this, 0, other)

infix fun Vector.by(y: Int) = also {
    this.y = y.toDouble()
}

operator fun Vector.component1() = blockX

operator fun Vector.component2() = blockY

operator fun Vector.component3() = blockZ

fun shader(shader: Shader.() -> Unit): Shader {
    val shade = object : Shader {
        override var position: Location =
            Location(null, 0.0, 0.0, 0.0)
        override var rotation: Vector = 0 by 0 by 0
        override var size: Vector = 5 by 5
        override var centered: Boolean = false
        override var setup  = {}
        override var update = {}
    }
    shader(shade)
    return shade
}

fun Shader.setup(block: () -> Unit) {
    setup = block
}

fun Shader.update(block: () -> Unit) {
    update = block
}