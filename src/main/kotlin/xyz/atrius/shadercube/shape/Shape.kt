package xyz.atrius.shadercube.shape

import org.bukkit.Location
import org.bukkit.util.Vector
import xyz.atrius.shadercube.Spatial

interface Shape : Spatial {

    val center: Location
        get() = point

    val size: Vector

    val points: Array<Vector>

    fun rotate(x: Double, y: Double, z: Double) = points.forEach {
        it.rotateX(x, 0.0).rotateY(y, 0.0).rotateZ(z, 0.0)
    }
}