package com.github.tobolak.game.ui

import com.github.tobolak.game.domain.Sprite
import io.vavr.control.Option
import java.awt.Image
import javax.swing.ImageIcon

class ImageProvider {

    private val cache = mutableMapOf<String, Image>()

    fun get(sprite: Sprite): Image = cache.getOrPut(sprite.name) {
        load("/sprites/${sprite.name}.png")
    }

    private fun load(file: String): Image = Option.of(javaClass.getResource(file))
            .map(::ImageIcon)
            .map { it.image }
            .getOrElseThrow { NoSuchElementException(file) }
}