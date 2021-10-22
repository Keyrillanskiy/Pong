package engine.common

import java.awt.Graphics
import java.awt.Rectangle
import java.awt.event.KeyEvent

data class GameObjectState(
    val x: Int,
    val y: Int,
    val velocityX: Int = 0,
    val velocityY: Int = 0,
    val width: Int = 0,
    val height: Int = 0,
    val hasCollision: Boolean = true
)

fun GameObjectState.collisionMask() = Rectangle(x, y, width, height)

fun List<GameObjectState>.collides(otherGameObjectState: GameObjectState): Boolean {
    return filter { it != otherGameObjectState }
        .filter { it.hasCollision }
        .any { it.collisionMask().intersects(otherGameObjectState.collisionMask()) }
}

data class GameObjectHandler(
    val onRender: Consumer<Pair<Graphics, GameObjectState>>,
    val onTick: Mapper<GameObjectState>,
    val onKeyPressed: ((GameObjectState, KeyEvent) -> GameObjectState)? = null,
    val onKeyReleased: ((GameObjectState, KeyEvent) -> GameObjectState)? = null
)

open class GameObject(open var state: GameObjectState, open val handler: GameObjectHandler)

inline fun <reified T> List<GameObject>.findByType(): GameObject {
    return find { it is T } ?: throw IllegalStateException("${T::class.java.simpleName} game object not found")
}
