package engine

import engine.common.Action
import engine.common.Consumer
import java.awt.Canvas
import java.awt.Graphics
import java.awt.event.KeyListener
import java.awt.image.BufferStrategy

private const val DESIRED_FPS = 60
private const val NANOSECONDS_IN_SECOND = 1_000_000_000
private const val renderTime = NANOSECONDS_IN_SECOND / DESIRED_FPS

fun startGameLoop(
    window: Canvas,
    onTick: Action? = null,
    onRender: Consumer<Pair<Graphics, BufferStrategy>>? = null,
    keyListener: KeyListener? = null
) {
    window.addKeyListener(keyListener)

    var previous = System.nanoTime()
    var lag = 0.0
    val bufferStrategy = window.bufferStrategy

    window.requestFocus()
    while (true) {
        val current = System.nanoTime()
        val elapsed = current - previous
        previous = current
        lag += elapsed
        while (lag >= renderTime) {
            onTick?.invoke()
            lag -= renderTime
        }
        val graphics = window.bufferStrategy.drawGraphics
        onRender?.invoke(graphics to bufferStrategy)
    }
}