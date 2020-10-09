package xyz.atrius.shadercube.shape

import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.util.Vector
import xyz.atrius.shadercube.Spatial

interface Shape : Spatial {

    var particle: Particle

    val center: Location
        get() = point

    val size: Vector

    val points: Array<Vector>

    fun rotate(x: Double, y: Double, z: Double) = points.forEach {
        it.rotateX(x).rotateY(y).rotateZ(z)
    }
}