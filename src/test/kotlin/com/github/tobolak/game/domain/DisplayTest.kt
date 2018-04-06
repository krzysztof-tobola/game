package com.github.tobolak.game.domain

import com.github.tobolak.game.domain.geometry.Rectangle
import com.github.tobolak.game.domain.geometry.Vector
import com.github.tobolak.game.infra.SpritesRepositoryImpl
import org.hamcrest.Matchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Test

class DisplayTest {
    private val renderer = FakeRenderer(8, 6)
    private val bounds = Vector(renderer.width, renderer.height)
    private val game = Game(
            renderer = renderer,
            spritesRepository = SpritesRepositoryImpl(),
            bounds = bounds,
            playerPos = Rectangle(Vector(0, 0), Vector(2, 2)),
            itemPos = Rectangle(bounds / 2, Vector(1, 1)),
            boxPos = Rectangle(bounds / Vector(2, 1) + Vector(-1, -2), Vector(2, 2)),
            npcPos = Rectangle(Vector(7, 2), Vector(1, 1))
    )

    @Test
    fun displayInitialBoard() {
        tick(1)

        assertBoard("""
            |PP      |
            |PP      |
            |       G|
            |    D   |
            |   BB   |
            |   BB   |
        """)
    }

    @Test
    fun nothingShouldHappenBeforeTick() {
        game.moveDown()

        assertBoard("""
            |        |
            |        |
            |        |
            |        |
            |        |
            |        |
        """)
    }


    private fun tick(n: Int) {
        repeat(n) { game.tick() }
    }

    private fun assertBoard(expected: String) {
        assertThat(renderer.toString(), equalTo(expected.trimMargin()))
    }
}
