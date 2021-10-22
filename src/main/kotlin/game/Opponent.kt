package game

import WINDOW_HEIGHT
import WINDOW_WIDTH
import engine.common.GameObject
import engine.common.GameObjectHandler
import engine.common.GameObjectState
import engine.common.findByType
import java.awt.Color

const val OPPONENT_WIDTH = 16
private const val OPPONENT_HEIGHT = OPPONENT_WIDTH * 5
private const val OPPONENT_SPEED = 4
private const val OPPONENT_INITIAL_X = WINDOW_WIDTH - OPPONENT_WIDTH
private const val OPPONENT_INITIAL_Y = WINDOW_HEIGHT / 2 - OPPONENT_HEIGHT // center of the screen

class Opponent(gameObjects: List<GameObject>) : GameObject(
    state = GameObjectState(x = OPPONENT_INITIAL_X, y = OPPONENT_INITIAL_Y, width = OPPONENT_WIDTH, height = OPPONENT_HEIGHT),
    handler = GameObjectHandler(
        onTick = { state ->
            val ball = gameObjects.findByType<Ball>()
            val newVelocityY = if (ball.state.y > state.y + OPPONENT_HEIGHT / 2) OPPONENT_SPEED else -OPPONENT_SPEED
            state.copy(y = state.y + newVelocityY, velocityY = newVelocityY)
        },
        onRender = { (graphics, state) ->
            graphics.color = Color.WHITE
            graphics.fillRect(state.x, state.y, OPPONENT_WIDTH, OPPONENT_HEIGHT)
        }
    )
)