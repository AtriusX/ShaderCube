package xyz.atrius.shadercube.shape

import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.util.Vector
import xyz.atrius.shadercube.data.Spatial
import xyz.atrius.shadercube.data.Coordinate
import xyz.atrius.shadercube.data.Data
import xyz.atrius.shadercube.data.Stylable
import xyz.atrius.shadercube.data.Updatable

abstract class Shape<T> : Spatial, Updatable, Stylable<T> {

    abstract var particle: Particle

    val center: Location
        get() = location

    abstract val size: Vector

    var vertices: MutableList<Coordinate> = mutableListOf()

    fun rotate(x: Double, y: Double, z: Double) = vertices.forEach {
        it.rotateX(x).rotateY(y).rotateZ(z)
    }

    @Suppress("UNCHECKED_CAST")
    final override fun update() = vertices.forEach {
        particle(particle, it.get()) {
            style(Data(Coordinate(point, it), this@Shape as T))
        }
    }

    abstract fun vertexes()
}