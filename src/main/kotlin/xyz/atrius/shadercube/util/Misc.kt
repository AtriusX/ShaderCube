package xyz.atrius.shadercube.util

import com.destroystokyo.paper.ParticleBuilder
import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.util.Vector
import xyz.atrius.shadercube.shader.Shader
import java.awt.Color.HSBtoRGB

val Double.radians: Double
    get() = Math.toRadians(this)

val Int.radians: Double
    get() = Math.toRadians(this.toDouble())

fun rgb(r: Int, g: Int, b: Int) =
    Color.fromRGB(r, g, b)

fun hsb(hue: Float, saturation: Float, brightness: Float) = Color.fromRGB(
    HSBtoRGB(hue, saturation, brightness) and 0xffffff
)

fun particle(particle: Particle = Particle.REDSTONE, position: Location, block: ParticleBuilder.() -> Unit = {}) =
    ParticleBuilder(particle).location(position).also(block).spawn()

fun Shader.particle(particle: Particle = Particle.REDSTONE, position: Vector = point, block: ParticleBuilder.() -> Unit = {}) =
        particle(particle, position.toLocation(world), block)