package xyz.atrius.shadercube.data

import com.destroystokyo.paper.ParticleBuilder

/**
 * A high-order function used in the creation of particle systems. This function
 * uses a [ParticleBuilder] as the receiver and includes a [Data] binding as the
 * functional parameter.
 *
 * @param T The data type used in the supplied data binding.
 *
 * @see ParticleBuilder
 * @see Data
 */
typealias Style<T> =
    ParticleBuilder.(Data<T>) -> Unit

/**
 * Special-use class that binds a given [data] object to a [Coordinate]. This binding
 * is passed around consistently and used within [Shape][xyz.atrius.shadercube.shape.Shape]
 * objects to help in the application particle styles each time the system calls an update.
 *
 * @constructor Create a binding between a [Coordinate] value and a generic [T] value.
 *
 * @property vector The world position this data is bound to.
 * @property data   The data supplied to this binding.
 *
 * @see Coordinate
 */
data class Data<T> (
    val vector: Coordinate,
    val data  : T
)

/**
 * Descriptor for any objects that can take a [Style] attribute. This is commonly used in
 * [Shape][xyz.atrius.shadercube.shape.Shape] implementations to allow end-users to apply
 * custom particle styles to their shaders. Each style supplies a [Data] binding in its
 * parameters to allow for more complex transformations to particle systems. This also has
 * the added benefit of being able to move the particle to a new location.
 *
 * @see Style
 * @see Data
 */
interface Stylable<T> {

    /**
     * @property style The style function to execute during particle updates.
     *
     * @see Style
     */
    val style: Style<T>
}
