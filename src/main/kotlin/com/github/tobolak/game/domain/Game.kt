package com.github.tobolak.game.domain

import com.github.tobolak.game.domain.geometry.Rectangle
import com.github.tobolak.game.domain.geometry.Vector
import java.util.concurrent.atomic.AtomicReference

data class Character(val name: String)

class Game(
        private val renderer: Renderer,
        private val spritesRepository: SpritesRepository,
        bounds: Vector,
        playerPos: Rectangle,
        itemPos: Rectangle,
        boxPos: Rectangle,
        npcPos: Rectangle
) {
    private val playerSprite = getSprite("player")
    private val playerWithItemSprite = getSprite("player-item")
    private val player = Character("player")
    private val item = Character("item")
    private val box = Character("box")
    private val nextMove = AtomicReference { b: Board -> b }
    private val board = AtomicReference(Board(
            bounds,
            CharacterState(player, playerSprite, playerPos),
            CharacterState(item, getSprite("item"), itemPos),
            CharacterState(box, getSprite("box"), boxPos),
            CharacterState(Character("npc"), getSprite("npc"), npcPos)
    ))

    fun tick() {
        val updated = board.updateAndGet { oldBoard ->
            val newBoard = nextMove.get()(oldBoard)
            handlePlayerMove(newBoard, oldBoard)
        }

        renderer.render(updated::paint)
    }

    private fun handlePlayerMove(newBoard: Board, oldBoard: Board): Board {
        return if (newBoard.detectCollision(player, item)) {
            newBoard.replaceSprite(player, playerWithItemSprite).remove(item)
        } else if (newBoard.detectCollision(player, box)) {
            if (oldBoard.sprite(player).contains(playerWithItemSprite)) {
                oldBoard.replaceSprite(box, getSprite("box-item"))
                        .replaceSprite(player, playerSprite)
            } else {
                oldBoard
            }
        } else {
            newBoard
        }
    }

    fun moveLeft() = movePlayer { it::moveLeft }
    fun moveRight() = movePlayer { it::moveRight }
    fun moveDown() = movePlayer { it::moveDown }
    fun moveUp() = movePlayer { it::moveUp }
    fun stopMoving() = nextMove.set { it }

    private fun movePlayer(getMove: (Board) -> ((Character) -> Board)) = nextMove.set { board ->
        getMove(board)(player)
    }

    private fun getSprite(name: String) = spritesRepository.byName(name)
}
