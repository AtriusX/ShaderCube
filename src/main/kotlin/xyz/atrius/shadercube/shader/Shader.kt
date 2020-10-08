package xyz.atrius.shadercube.shader

import com.destroystokyo.paper.ParticleBuilder
import org.bukkit.*
import org.bukkit.util.Vector
import xyz.atrius.shadercube.KotlinPlugin

interface Shader {

    val time: Long
        get() = System.currentTimeMillis()

    var position: Location

    var rotation: Vector

    var centered: Boolean

    var setup: () -> Unit

    var update: () -> Unit

    fun ParticleBuilder.location(vector: Vector) =
        location(vector.toLocation(position.world))

    fun Vector.rotateX(angle: Double, offset: Double = 0.0) =
        rotateX(angle, offset, position.toVector())

    fun Vector.rotateY(angle: Double, offset: Double = 0.0) =
        rotateY(angle, offset, position.toVector())

    fun Vector.rotateZ(angle: Double, offset: Double = 0.0) =
        rotateZ(angle, offset, position.toVector())

    fun particle(particle: Particle, block: ParticleBuilder.() -> Unit) {
        val builder = ParticleBuilder(particle).location(position)
        block(builder)
        builder.spawn()
    }
}

fun Vector.rotateX(angle: Double, offset: Double = 0.0, center: Vector) =
    subtract(center).add(Vector(0.0, offset, offset)).rotateAroundX(angle).add(center)

fun Vector.rotateY(angle: Double, offset: Double = 0.0, center: Vector) =
    subtract(center).add(Vector(offset, 0.0, offset)).rotateAroundY(angle).add(center)

fun Vector.rotateZ(angle: Double, offset: Double = 0.0, center: Vector) =
    subtract(center).add(Vector(offset, offset, 0.0)).rotateAroundZ(angle).add(center)

operator fun Vector.component1() = blockX

operator fun Vector.component2() = blockY

operator fun Vector.component3() = blockZ

fun shader(plugin: KotlinPlugin, rate: Long = 1, shader: Shader.() -> Unit): Shader {
    val shade = object : Shader {
        override var position: Location =
            Location(null, 0.0, 0.0, 0.0)
        override var rotation: Vector = Vector(0.0, 0.0, 0.0)
        override var centered: Boolean = false
        override var setup  = {}
        override var update = {}
    }
    shader(shade)
    shade.setup()
    Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, {
        shade.update()
    }, 0L, rate)
    return shade
}

fun Shader.setup(block: () -> Unit) {
    setup = block
}

fun Shader.update(block: () -> Unit) {
    update = block
}

fun particle(particle: Particle, block: ParticleBuilder.() -> Unit, position: Location) {
    val builder = ParticleBuilder(particle).location(position)
    block(builder)
    builder.spawn()
}

interface Shape {
    val center: Location
    val size: Vector
    val points: Array<Vector>

    fun run(particle: Particle, block: ParticleBuilder.() -> Unit) = points.forEach {
        particle(particle, block, it.toLocation(center.world))
    }

    fun rotate(x: Double, y: Double, z: Double) = points.forEach {
        it.rotateX(x, 0.0).rotateY(y, 0.0).rotateZ(z, 0.0)
    }

    fun Vector.rotateX(angle: Double, offset: Double) =
        rotateX(angle, offset, center.toVector())

    fun Vector.rotateY(angle: Double, offset: Double) =
        rotateY(angle, offset, center.toVector())

    fun Vector.rotateZ(angle: Double, offset: Double) =
        rotateZ(angle, offset, center.toVector())
}

class Circle(
    override val center  : Location,
                 size    : Double,
                 vertexes: Int,
    private  val block   : ParticleBuilder.(Circle) -> Unit = {}
) : Shape {
    override val size  : Vector        = Vector(0.0, size, 0.0)
    override val points: Array<Vector> = Array(vertexes) { center.toVector() }

    init {
        val angle = Math.toRadians(360.0 / vertexes.toDouble())
        points.forEachIndexed { i, point ->
            point.rotateY(angle * i, this.size.y)
            particle(Particle.REDSTONE, {
                block(this@Circle)
            }, point.toLocation(center.world))
        }
    }
}

class Line(
    p1: Location,
    p2: Location,
    vertexes: Int
) {
    init {
        val difference = p1.toVector()
            .subtract(p2.toVector())
            .multiply(-1.0 / vertexes)
        for (i in 1..vertexes)
            particle(Particle.REDSTONE, {
                color(Color.AQUA, 2f)
            }, p1.add(difference))
    }
}