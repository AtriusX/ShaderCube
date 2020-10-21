package xyz.atrius.shadercube.util

import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitScheduler
import org.koin.java.KoinJavaComponent.inject
import xyz.atrius.shadercube.KotlinPlugin
import xyz.atrius.shadercube.ShaderCube

/**
 * @property plugin Dependency-injected instance of the Shadercube plugin.
 */
val plugin: KotlinPlugin by inject(ShaderCube::class.java)

/**
 * @property schedule Reference to the server scheduler system.
 */
val schedule: BukkitScheduler
    get() = Bukkit.getScheduler()
