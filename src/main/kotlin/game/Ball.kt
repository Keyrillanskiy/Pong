package game

import WINDOW_HEIGHT
import WINDOW_WIDTH
import engine.common.*
import java.awt.Color
import java.util.*
import kotlin.math.absoluteValue

private const val BALL_WIDTH = 16
private const val BALL_HEIGHT = BALL_WIDTH
private const val BALL_INITIAL_X = WINDOW_WIDTH / 2 - BALL_WIDTH
private const val BALL_INITIAL_Y = WINDOW_HEIGHT / 2 - BALL_HEIGHT
private const val BALL_INITIAL_X_SPEED = 4
private val ballYSpeedValues = arrayOf(4,5,6)
private val random by lazy { Random() }

//TODO: refactor somehow
val ballHitLeftSideSubscribers = LinkedList<Action>()
val ballHitRightSideSubscribers = LinkedList<Action>()

class Ball(gameObjects: List<GameObject>) : GameObject(
    state = GameObjectState(
        x = BALL_INITIAL_X,
        y = BALL_INITIAL_Y,
        velocityX = randomVelocityDirection(BALL_INITIAL_X_SPEED),
        velocityY = randomVelocityDirection(ballYSpeedValues.random()),
        width = BALL_WIDTH,
        height = BALL_HEIGHT
    ),
    handler = GameObjectHandler(
        onTick = { state ->
            var newX = state.x + state.velocityX
            var newY = state.y + state.velocityY

            val (newXVelocity, newYVelocity) = when {
                gameObjects.map { it.state }.collides(state) -> {
                    val newVelocityX = state.velocityX.unaryMinus()
                    val newVelocityXAccelerated = if (newVelocityX > 0) newVelocityX + 1 else newVelocityX - 1
                    val newVelocityY = if (state.velocityY > 0) ballYSpeedValues.random() else ballYSpeedValues.random().unaryMinus()
                    newX = state.x + newVelocityXAccelerated
                    newVelocityXAccelerated to newVelocityY
                }
                collidesWindowVerticalLine(newX, state.velocityX) -> {
                    val newVelocityX = randomVelocityDirection(BALL_INITIAL_X_SPEED)
                    val newVelocityY = randomVelocityDirection(ballYSpeedValues.random())
                    if (newX <= PLAYER_WIDTH) {
                        ballHitLeftSideSubscribers.forEach { subscriber -> subscriber.invoke() }
                    } else {
                        ballHitRightSideSubscribers.forEach { subscriber -> subscriber.invoke() }
                    }
                    newX = BALL_INITIAL_X
                    newY = BALL_INITIAL_Y
                    newVelocityX to newVelocityY
                }
                collidesWindowHorizontalLine(newY) -> {
                    val newVelocityY = state.velocityY.unaryMinus()
                    newY = state.y + newVelocityY
                    state.velocityX to newVelocityY
                }
                else -> state.velocityX to state.velocityY
            }

            state.copy(x = newX, y = newY, velocityX = newXVelocity, velocityY = newYVelocity)
        },
        onRender = { (graphics, state) ->
            graphics.color = Color.WHITE
            graphics.fillOval(state.x, state.y, BALL_WIDTH, BALL_HEIGHT)
        }
    )
)

private fun collidesWindowHorizontalLine(y: Int): Boolean {
    return y !in 0..WINDOW_HEIGHT - BALL_HEIGHT
}
private fun collidesWindowVerticalLine(x: Int, horizontalSpeed: Int): Boolean {
    val leftBorder = PLAYER_WIDTH - horizontalSpeed.absoluteValue
    val rightBorder = WINDOW_WIDTH - OPPONENT_WIDTH - BALL_WIDTH + horizontalSpeed.absoluteValue
    return x !in leftBorder..rightBorder
}

private fun randomVelocityDirection(previousVelocity: Int): Int {
    val previousVelocityValue = previousVelocity.absoluteValue
    return if (random.nextBoolean()) previousVelocityValue else previousVelocityValue.unaryMinus()
}