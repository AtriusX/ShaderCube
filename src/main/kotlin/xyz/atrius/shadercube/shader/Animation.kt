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

fun Animation.before(frame: Int, block: Update) =
    between(0..frame, block)

fun Animation.after(frame: Int, block: Update) =
    between(frame..frameDuration, block)

fun Animation.frame(frame: Int, block: Update) =
    between(frame..frame, block)

fun Animation.between(frames: IntRange, block: Update) {
    if (framecount in frames) block()
}

fun animation(rate: Long = 0, frames: Int = 20, shader: Animation.() -> Unit) = Animation(frames).apply {
    shader(this)        // Construct the shader script
    if (update != null) // Setup update loop
        taskId = schedule.scheduleSyncRepeatingTask(plugin, {
            if (cancel())
                schedule.cancelTask(taskId)
            update?.invoke(this)
            framecount++
        }, 0L, rate)
}