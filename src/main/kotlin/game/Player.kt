package game

import WINDOW_HEIGHT
import engine.common.GameObject
import engine.common.GameObjectHandler
import engine.common.GameObjectState
import java.awt.Color
import java.awt.event.KeyEvent

const val PLAYER_WIDTH = 16
private const val PLAYER_HEIGHT = PLAYER_WIDTH * 5
private const val PLAYER_SPEED = 4
private const val PLAYER_INITIAL_X = 0
private const val PLAYER_INITIAL_Y = WINDOW_HEIGHT / 2 - PLAYER_HEIGHT // center of the screen

class Player : GameObject(
    GameObjectState(x = PLAYER_INITIAL_X, y = PLAYER_INITIAL_Y, width = PLAYER_WIDTH, height = PLAYER_HEIGHT),
    GameObjectHandler(
        onRender = { (graphics, state) ->
            graphics.color = Color.WHITE
            graphics.fillRect(state.x, state.y, PLAYER_WIDTH, PLAYER_HEIGHT)
        },
        onTick = { state ->
            val newY = (state.y + state.velocityY).coerceIn(minimumValue = 0, maximumValue = WINDOW_HEIGHT - PLAYER_HEIGHT)
            state.copy(y = newY)
        },
        onKeyPressed = { state, keyEvent ->
            when (keyEvent.keyCode) {
                KeyEvent.VK_W -> state.copy(velocityY = -PLAYER_SPEED)
                KeyEvent.VK_S -> state.copy(velocityY = PLAYER_SPEED)
                else -> state
            }
        },
        onKeyReleased = { state, keyEvent ->
            when (keyEvent.keyCode) {
                KeyEvent.VK_W -> state.copy(velocityY = 0)
                KeyEvent.VK_S -> state.copy(velocityY = 0)
                else -> state
            }
        }
    )
)