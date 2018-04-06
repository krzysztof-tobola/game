package com.github.tobolak.game.domain

import com.github.tobolak.game.domain.geometry.Rectangle
import com.github.tobolak.game.domain.geometry.Vector

class FakeRenderer(val width: Int, val height: Int) : Renderer {
    val bounds = Vector(width, height)
    private var rows = Array(height) { Array(width) { ' ' } }
    private val gfx = object : Gfx {
        override val clip = Vector(width, height)
        override fun sprite(sprite: Sprite, rectangle: Rectangle) {
            val spriteChar = when (sprite.name) {
                "player" -> 'P'
                "player-item" -> 'C'
                "item" -> 'D'
                "box" -> 'B'
                "box-item" -> 'E'
                "npc" -> 'G'
                else -> throw IllegalArgumentException(sprite.name)
            }

            for (v in rectangle) {
                if (v.y < rows.size && v.x < rows[v.y].size)
                    rows[v.y][v.x] = spriteChar
            }
        }
    }

    override fun render(painter: (Gfx) -> Unit) {
        rows = Array(height) { Array(width) { ' ' } }
        painter(gfx)
    }

    override fun toString() = (rows.fold("") { x, y -> x + y.foldRight("|\n", { ch, st -> ch + st }) })
            .trimEnd()
}

