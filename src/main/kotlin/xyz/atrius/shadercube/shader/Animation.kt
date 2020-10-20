package xyz.atrius.shadercube.shader

/**
 * Animations are a special [Shader] type that only run for a specified number of
 * updates. By the nature of the [Scheduler][org.bukkit.scheduler.BukkitScheduler]
 * system, the animations will run at a max of 20 frames per second. This number
 * will fluctuate with the rate at which updates are set to occur at, however the
 * animation will always run until it is either cancelled or reaches the max amount
 * of frame updates.
 *
 * @constructor Constructs a new animation which runs for a given [frameDuration].
 *              This may **not** be called directly, please use [animation] to build.
 *
 * @property frameDuration The number of frames to run this animation for.
 *
 * @see Shader
 */
class Animation private constructor(
    val frameDuration: Int
) : Shader() {

    private val endAnimation: Boolean
        get() = framecount >= frameDuration

    /**
     * @property cancel Defines the cancel conditions for this animation. Will always cancel
     *                  if the [framecount] exceeds the given [frameDuration].
     */
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

/**
 * A DSL function for use in [Animation] scripts. This function will execute a section
 * of code only if the current frame is less than the given max frame.
 *
 * @receiver The animation this subscript is applied to.
 *
 * @param frame The cutoff frame for this subsection.
 * @param block The update function to run during this subscript's valid execution time.
 */
fun Animation.before(frame: Int, block: Update) =
    between(0..frame, block)

/**
 * A DSL function for use in [Animation] scripts. This function will execute a section
 * of code only if the current frame is greater than or equal to the given minimum frame.
 *
 * @receiver The animation this subscript is applied to.
 *
 * @param frame The start frame for this subsection.
 * @param block The update function to run during this subscript's valid execution time.
 */
fun Animation.after(frame: Int, block: Update) =
    between(frame..frameDuration, block)

/**
 * A DSL function for use in [Animation] scripts. This function will execute a section
 * of code only if the current frame is equal to the given frame.
 *
 * @receiver The animation this subscript is applied to.
 *
 * @param frame The frame of execution for this subsection.
 * @param block The update function to run during this subscript's valid execution frame.
 */
fun Animation.frame(frame: Int, block: Update) =
    between(frame..frame, block)

/**
 * A DSL function for use in [Animation] scripts. This function will execute a section
 * of code only if the current frame is within the given frame range.
 *
 * @receiver The animation this subscript is applied to.
 *
 * @param frames The valid frame range for this subsection.
 * @param block  The update function to run during this subscript's valid execution time.
 */
fun Animation.between(frames: IntRange, block: Update) {
    if (framecount in frames) block()
}

/**
 * This functions as the user-facing constructor for [Animation] objects. For an in-depth
 * explanation to the animation system, please check the constructor documentation.
 *
 * @param rate   The number of ticks to wait between running frame updates.
 * @param frames The frame duration of the animation.
 * @param shader The shader script to run for this animation.
 *
 * @see Animation
 */
fun animation(rate: Long = 0, frames: Int = 20, shader: Animation.() -> Unit) =
    Animation.start(rate, frames, shader)
