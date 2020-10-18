package xyz.atrius.shadercube.shape

import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.util.Vector
import xyz.atrius.shadercube.Spatial
import xyz.atrius.shadercube.data.Coordinate
import xyz.atrius.shadercube.data.Data
import xyz.atrius.shadercube.data.Stylable
import xyz.atrius.shadercube.data.Updatable

interface Shape<T> : Spatial, Updatable, Stylable<T> {

    var particle: Particle

    val center: Location
        get() = location

    val size: Vector

    val vertices: Array<Coordinate>
        get() = vertexes()

    fun rotate(x: Double, y: Double, z: Double) = vertices.forEach {
        it.rotateX(x).rotateY(y).rotateZ(z)
    }

    @Suppress("UNCHECKED_CAST")
    override fun update() = vertices.forEach {
        particle(particle, it) {
            style(Data(Coordinate(point, it), this@Shape as T))
        }
    }

    fun vertexes(): Array<Coordinate>
}