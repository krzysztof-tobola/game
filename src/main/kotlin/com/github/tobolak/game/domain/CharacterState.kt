package com.github.tobolak.game.domain

import com.github.tobolak.game.domain.geometry.Rectangle

data class Sprite(val name: String)

data class CharacterState(
        val character: Character,
        val sprite: Sprite,
        val rectangle: Rectangle
)