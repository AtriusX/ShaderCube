package xyz.atrius.shadercube.util

import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitScheduler
import org.koin.java.KoinJavaComponent.inject
import xyz.atrius.shadercube.KotlinPlugin
import xyz.atrius.shadercube.ShaderCube

val plugin: KotlinPlugin by inject(ShaderCube::class.java)

val schedule: BukkitScheduler
    get() = Bukkit.getScheduler()