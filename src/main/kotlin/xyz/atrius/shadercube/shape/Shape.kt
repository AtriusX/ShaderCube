package xyz.atrius.shadercube.shape

import com.destroystokyo.paper.ParticleBuilder
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.util.Vector
import xyz.atrius.shadercube.Spatial

typealias Style<T> =
    ParticleBuilder.(T, Vector) -> Unit

interface Shape<T : Shape<T>> : Spatial {

    var particle: Particle

    val center: Location
        get() = point

    val size: Vector

    val points: Array<Vector>

    val block: Style<T>

    fun rotate(x: Double, y: Double, z: Double) = points.forEach {
        it.rotateX(x).rotateY(y).rotateZ(z)
    }
}