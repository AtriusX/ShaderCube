package xyz.atrius.shadercube.data

/**
 * Functional description of an object that can be executed automatically by Shadercube's internal
 * [Shader][xyz.atrius.shadercube.shader.Shader] system. This is typically used for handling particle
 * generation in the [Shape][xyz.atrius.shadercube.shape.Shape] system. If your design requires you
 * to extend [Shape][xyz.atrius.shadercube.shape.Shape], you **do not** need to implement this on
 * your own, rather you should not.
 *
 * This interface may be used functionally; similar to how lambdas and high-order functions behave.
 *
 * @see xyz.atrius.shadercube.shader.Shader.update
 * @see xyz.atrius.shadercube.shape.Shape.update
 */
fun interface Updatable {

    /**
     * The function executed each time an update is requested by the system or the user. This
     * function can be used to create blocks of code that execute alongside the internal system's
     * main update cycle.
     */
    fun update()
}
