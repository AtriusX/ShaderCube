package xyz.atrius.shadercube.util

import org.bukkit.util.Vector

operator fun Vector.component1() = blockX

operator fun Vector.component2() = blockY

operator fun Vector.component3() = blockZ