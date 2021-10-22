package engine

import engine.common.GameObject
import java.awt.Color
import java.awt.Graphics
import java.awt.Toolkit
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.awt.image.BufferStrategy
import java.util.*

val gameObjects by lazy { LinkedList<GameObject>() }

val keyListener by lazy {
    object : KeyAdapter() {
        override fun keyPressed(keyEvent: KeyEvent?) {
            if (keyEvent == null) return

            gameObjects.filter { it.handler.onKeyPressed != null }
                .forEach { gameObject ->
                    gameObject.handler.onKeyPressed?.invoke(gameObject.state, keyEvent)?.let { newState -> gameObject.state = newState }
                }
        }

        override fun keyReleased(keyEvent: KeyEvent?) {
            if (keyEvent == null) return

            gameObjects.filter { it.handler.onKeyPressed != null }
                .forEach { gameObject ->
                    gameObject.handler.onKeyReleased?.invoke(gameObject.state, keyEvent)?.let { newState -> gameObject.state = newState }
                }
        }
    }
}

fun tick() {
    gameObjects.forEach { gameObject -> gameObject.state = gameObject.handler.onTick.invoke(gameObject.state) }
}

fun render(graphics: Graphics, bufferStrategy: BufferStrategy, backgroundColor: Color = Color.BLACK) {
    graphics.clearWindow(backgroundColor)
    gameObjects.forEach { gameObject -> gameObject.handler.onRender.invoke(graphics to gameObject.state) }
    graphics.dispose()
    bufferStrategy.show()
}

fun addGameObject(gameObject: GameObject) {
    gameObjects.add(gameObject)
}

private fun Graphics.clearWindow(color: Color) {
    this.color = color
    val screenSize = Toolkit.getDefaultToolkit().screenSize
    fillRect(0, 0, screenSize.width, screenSize.height)
}