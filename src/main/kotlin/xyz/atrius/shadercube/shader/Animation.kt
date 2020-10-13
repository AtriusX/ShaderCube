package xyz.atrius.shadercube.shader

import xyz.atrius.shadercube.util.plugin
import xyz.atrius.shadercube.util.schedule

class Animation(
    val frameDuration: Int
) : Shader() {

    private val endAnimation: Boolean
        get() = framecount >= frameDuration

    override var cancel: Cancel = {
        endAnimation
    }

    override fun cancel(block: Cancel) {
        cancel = { endAnimation || block() }
    }
}

fun Animation.beforeFrame(frame: Int, block: Update) =
    frame(0..frame, block)

fun Animation.afterFrame(frame: Int, block: Update) =
    frame(frame..frameDuration, block)

fun Animation.frame(frame: Int, block: Update) =
    frame(frame..frame, block)

fun Animation.frame(frames: IntRange, block: Update) {
    if (framecount in frames) block()
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