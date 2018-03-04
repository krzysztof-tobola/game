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
    private val nextMove = AtomicReference({ b: Board -> b })
    private val board = AtomicReference(Board(
            bounds,
            CharacterState(player, playerSprite, playerPos),
            CharacterState(item, getSprite("item"), itemPos),
            CharacterState(box, getSprite("box"), boxPos),
            CharacterState(Character("npc"),getSprite("npc"),npcPos)
    ))

    fun tick() {
        val updated = board.updateAndGet { oldBoard ->
            val board = nextMove.get().invoke(oldBoard)

            if (board.detectCollision(player, item)) {
                board.replaceSprite(player, playerWithItemSprite).remove(item)
            } else if (board.detectCollision(player, box)) {
                if (oldBoard.sprite(player).contains(playerWithItemSprite)) {
                    oldBoard.replaceSprite(box, getSprite("box-item"))
                            .replaceSprite(player, playerSprite)
                } else
                    oldBoard
            } else {
                board
            }
        }

        renderer.render(updated::paint)
    }

    fun moveLeft() = nextMove.set { it.moveLeft(player) }
    fun moveRight() = nextMove.set { it.moveRight(player) }
    fun moveDown() = nextMove.set { it.moveDown(player) }
    fun moveUp() = nextMove.set { it.moveUp(player) }
    fun stopMoving() = nextMove.set { it }
    private fun getSprite(name: String) = spritesRepository.byName(name)
}

