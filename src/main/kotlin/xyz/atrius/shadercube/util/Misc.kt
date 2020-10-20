package xyz.atrius.shadercube.util

/**
 * Generates a range of indices for use in iterations.
 *
 * @sample xyz.atrius.shadercube.shader.Text.render
 *
 * @receiver The upper range of the generated indices.
 * @return   The generated indices.
 */
val Int.indices: IntRange
    get() = 0 until this

/**
 * Generates an iterator for [IntProgression] pairs. This can be used to flatten
 * 2-dimensional loop into a single loop.
 *
 * @sample xyz.atrius.shadercube.shape.Square.vertexes
 *
 * @receiver The [IntProgression] pair to generate iterations for.
 * @return   The generated iterator of number pairs.
 */
operator fun Pair<IntProgression, IntProgression>.iterator(): Iterator<Pair<Int, Int>> {
    val (first, second) = this
    val data = mutableListOf<Pair<Int, Int>>()
    for (i in first) for (j in second)
        data += i to j
    return data.iterator()
}