package com.github.tobolak.game.domain

import com.github.tobolak.game.domain.geometry.Rectangle
import com.github.tobolak.game.domain.geometry.Vector

interface Gfx {
    fun sprite(sprite: Sprite, rectangle: Rectangle)
    val clip: Vector
}
