package xyz.atrius.shadercube.shader
class Animation(
    val frameDuration: Int
) : Shader() {

    private val endAnimation: Boolean
        get() = framecount >= frameDuration

    override var cancel: Cancel = { endAnimation }
        set(value) {
            field = { endAnimation || value() }
        }

    companion object {

        internal fun start(rate: Long, frames: Int, shader: Animation.() -> Unit) = Animation(frames).apply {
            shader(this) // Construct the shader script
            update(rate) // Update the shader at the given rate
        }
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

fun animation(rate: Long = 0, frames: Int = 20, shader: Animation.() -> Unit) =
    Animation.start(rate, frames, shader)
