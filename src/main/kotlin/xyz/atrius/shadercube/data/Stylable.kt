package xyz.atrius.shadercube.data

import com.destroystokyo.paper.ParticleBuilder

typealias Style<T> =
    ParticleBuilder.(Data<T>) -> Unit

data class Data<T> (
    val vector: Coordinate,
    val data  : T
)

interface Stylable<T> {
    val style: Style<T>
}