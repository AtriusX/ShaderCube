package xyz.atrius.shadercube.shader

import xyz.atrius.shadercube.util.plugin
import xyz.atrius.shadercube.util.schedule

class Animation(
    frameDuration: Int
) : Shader() {

    override var cancel: () -> Boolean = {
        framecount >= frameDuration
    }
}

fun animation(rate: Long = 0, frames: Int = 20, shader: Animation.() -> Unit) = Animation(frames).apply {
    shader(this)        // Construct the shader script
    if (update != null) // Setup update loop
        taskId = schedule.scheduleSyncRepeatingTask(plugin, {
            if (cancel())
                schedule.cancelTask(taskId)
            update?.invoke()
            framecount++
        }, 0L, rate)
}