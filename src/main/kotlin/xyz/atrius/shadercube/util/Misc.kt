package xyz.atrius.shadercube.util

val Int.indices: IntRange
    get() = 0 until this

operator fun Pair<IntProgression, IntProgression>.iterator(): Iterator<Pair<Int, Int>> {
    val (first, second) = this
    val data = mutableListOf<Pair<Int, Int>>()
    for (i in first) for (j in second)
        data += i to j
    return data.iterator()
}