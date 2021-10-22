import engine.*
import game.Ball
import game.Counter
import game.Opponent
import game.Player

const val WINDOW_WIDTH = 640
const val WINDOW_HEIGHT = 480
private const val WINDOW_TITLE = "Pong"

fun main() {
    val window = createAndShowWindow(WINDOW_WIDTH, WINDOW_HEIGHT, WINDOW_TITLE)

    val ball = Ball(gameObjects)
    addGameObject(ball)
    val player = Player()
    addGameObject(player)
    val opponent = Opponent(gameObjects)
    addGameObject(opponent)
    val counter = Counter()
    addGameObject(counter)

    startGameLoop(
        window = window,
        onTick = ::tick,
        onRender = { (graphics, bufferStrategy) -> render(graphics, bufferStrategy)},
        keyListener = keyListener
    )
}