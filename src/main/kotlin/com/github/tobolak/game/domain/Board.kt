package com.github.tobolak.game.domain

import com.github.tobolak.game.domain.geometry.Rectangle
import com.github.tobolak.game.domain.geometry.Vector
import io.vavr.collection.Iterator
import io.vavr.collection.Map
import io.vavr.control.Option

data class Board(
        private val positions: Map<Character, Rectangle>,
        private val sprites: Map<Character, Sprite>,
        private val bounds: Vector
) {
    constructor(bounds: Vector, vararg playerPos: CharacterState) : this(
            Iterator.ofAll(playerPos.asIterable())
                    .toMap(CharacterState::character, CharacterState::rectangle),
            Iterator.ofAll(playerPos.asIterable())
                    .toMap(CharacterState::character, CharacterState::sprite),
            bounds
    )

    fun moveRight(character: Character) = move(character, Vector(1, 0))
    fun moveLeft(character: Character) = move(character, Vector(-1, 0))
    fun moveDown(character: Character) = move(character, Vector(0, 1))
    fun moveUp(character: Character) = move(character, Vector(0, -1))
    fun remove(character: Character) = copy(positions = positions.remove(character))
    fun replaceSprite(character: Character, other: Sprite) = copy(sprites = sprites.put(character, other))

    fun paint(gfx: Gfx) = with(gfx) {
        positions.forEach { ch, rectangle ->
            sprite(sprites.get(ch).get(), (rectangle * gfx.clip) / bounds)
        }
    }

    fun detectCollision(char1: Character, char2: Character): Boolean {
        val zipped = positions[char1] zip positions[char2]
        return !zipped.isEmpty() && zipped
                .all { (x, y) -> x intersects y }
    }

    private fun move(character: Character, vector: Vector) =
            copy(positions = positions.put(character, move(positions[character].get(), vector)))

    private fun move(rectangle: Rectangle, vector: Vector) =
            if (rectangle + vector within bounds) {
                rectangle + vector
            } else {
                rectangle
            }

    fun sprite(player: Character): Option<Sprite> = sprites[player]
}
