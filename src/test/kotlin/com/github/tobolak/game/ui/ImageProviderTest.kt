package com.github.tobolak.game.ui

import com.github.tobolak.game.infra.SpritesRepositoryImpl
import org.hamcrest.Matchers.hasSize
import org.junit.Assert.assertThat
import org.junit.Test

class ImageProviderTest {
    @Test
    fun hasImageForEverySprite() {
        val provider = ImageProvider()
        val sprites = SpritesRepositoryImpl().findAll()

        val result = sprites.map { provider.get(it) }

        assertThat(result, hasSize(sprites.size))
    }
}