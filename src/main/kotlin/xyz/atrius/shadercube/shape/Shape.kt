package xyz.atrius.shadercube.shape

import org.bukkit.Particle
import org.bukkit.util.Vector
import xyz.atrius.shadercube.data.*

/**
 * Shapes are a basic object that can be used in the creation of more complex
 * shaders or animations. Since the purpose of shapes is to generate physical
 * geometry rather than to render particles, this class implements a generified
 * [update] function. Implementations should **not** attempt to override this
 * function and should instead focus their efforts on generating the shape's
 * vertex list.
 *
 * @see update
 */
abstract class Shape<T> : Spatial, Updatable, Stylable<T> {

    /**
     * @property particle The particle that is used when [update] is called.
     */
    abstract var particle: Particle

    /**
     * @property size The dimensions of any shape instances.
     */
    abstract val size: Vector

    /**
     * @property vertices The vertex list which stores all the points used
     *                    for generating particles when [update] is called.
     */
    var vertices: MutableList<Coordinate> = mutableListOf()

    /**
     * This function is implemented by extensions of this class and is
     * used for generating a list of vertexes that should be stored in
     * the [vertices] vertex list.
     */
    abstract fun vertexes()

    /**
     * Rotates a shape instance on all main axis relative to the center point.
     *
     * @param x The X rotation angle.
     * @param y The Y rotation angle.
     * @param z The Z rotation angle.
     */
    fun rotate(x: Double, y: Double, z: Double) = vertices.forEach {
        it.rotateX(x).rotateY(y).rotateZ(z)
    }

    @Suppress("UNCHECKED_CAST")
    final override fun update() = vertices.forEach {
        particle(particle, it.get()) {
            style(Data(Coordinate(point, it), this@Shape as T))
        }
    }
}