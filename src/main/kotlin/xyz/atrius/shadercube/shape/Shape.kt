package xyz.atrius.shadercube.shape

import com.destroystokyo.paper.ParticleBuilder
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.util.Vector
import xyz.atrius.shadercube.Spatial

typealias Style<T> =
    ParticleBuilder.(Data<T>) -> Unit

data class Data<T>(
    val data  : T,
    val vector: Vector
)

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