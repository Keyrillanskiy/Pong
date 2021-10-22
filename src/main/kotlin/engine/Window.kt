package engine

import java.awt.Canvas
import java.awt.Dimension
import java.awt.Frame
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import kotlin.system.exitProcess

private const val BUFFER_SIZE = 3

fun createAndShowWindow(width: Int, height: Int, title: String): Canvas {
    val canvas = Canvas().apply { size = Dimension(width, height) }

    Frame(title).apply {
        size = Dimension(width, height)
        setLocationRelativeTo(null) // move window to center of the screen
        isResizable = false
        add(canvas)
        addWindowListener(object: WindowAdapter() {
            override fun windowClosing(e: WindowEvent?) = exitProcess(0)
        })
        pack()

        isVisible = true // show window on the screen
    }

    canvas.createBufferStrategy(BUFFER_SIZE)

    return canvas
}
