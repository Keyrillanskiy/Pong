package game

import WINDOW_WIDTH
import engine.common.GameObject
import engine.common.GameObjectHandler
import engine.common.GameObjectState
import java.awt.Color

private const val COUNTER_HEIGHT = 200
private const val PLAYER_COUNTER_X = 30
private const val PLAYER_COUNTER_Y = 30
private const val OPPONENT_COUNTER_X = 600
private const val OPPONENT_COUNTER_Y = PLAYER_COUNTER_Y
private const val COUNTER_FONT_SIZE = 22.0f

//TODO: create state storage
private var playersPoints: Int = 0
private var opponentsPoints: Int = 0

class Counter : GameObject(
    state = GameObjectState(x = 0, y = 0, width = WINDOW_WIDTH, height = COUNTER_HEIGHT, hasCollision = false),
    handler = GameObjectHandler(
        onRender = { (graphics, _) ->
            with(graphics) {
                font = graphics.font.deriveFont(COUNTER_FONT_SIZE)
                color = Color.WHITE
                drawString(playersPoints.toString(), PLAYER_COUNTER_X, PLAYER_COUNTER_Y)
                drawString(opponentsPoints.toString(), OPPONENT_COUNTER_X, OPPONENT_COUNTER_Y)
            }
        },
        onTick = { state -> state }
    )
) {
    init {
        ballHitLeftSideSubscribers.add { opponentsPoints++ }
        ballHitRightSideSubscribers.add { playersPoints++ }
    }
}