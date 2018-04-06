package com.github.tobolak.game.domain

import com.github.tobolak.game.domain.geometry.Rectangle
import com.github.tobolak.game.domain.geometry.Vector
import com.github.tobolak.game.infra.SpritesRepositoryImpl
import org.hamcrest.Matchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Test

class CaptureTest {
    private val renderer = FakeRenderer(8, 6)
    private val bounds = Vector(renderer.width, renderer.height)
    private val game = Game(
            renderer = renderer,
            spritesRepository = SpritesRepositoryImpl(),
            bounds = bounds,
            playerPos = Rectangle(Vector(0, 0), Vector(2, 2)),
            itemPos = Rectangle(bounds / 2, Vector(1, 1)),
            boxPos = Rectangle(bounds / Vector(2, 1) + Vector(-1, -2), Vector(2, 2)),
            npcPos = Rectangle(bounds, Vector(1, 1))
    )

    @Test
    fun approachItem() {
        game.moveDown()
        tick(2)
        game.moveRight()
        tick(2)

        assertBoard("""
            |        |
            |        |
            |  PP    |
            |  PPD   |
            |   BB   |
            |   BB   |
        """)
    }

    @Test
    fun captureItemFromLeft() {
        game.moveDown()
        tick(2)
        game.moveRight()
        tick(3)

        assertBoard("""
            |        |
            |        |
            |   CC   |
            |   CC   |
            |   BB   |
            |   BB   |
        """)
    }

    @Test
    fun captureItemAndPutIntoBox() {
        game.moveDown()
        tick(2)
        game.moveRight()
        tick(3)
        game.moveDown()
        tick(1)

        assertBoard("""
            |        |
            |        |
            |   PP   |
            |   PP   |
            |   EE   |
            |   EE   |
        """)
    }

    @Test
    fun stopWhenCollisionWithTheBox() {
        game.moveDown()
        tick(3)
        game.moveRight()
        tick(3)

        assertBoard("""
            |        |
            |        |
            |        |
            | PP D   |
            | PPBB   |
            |   BB   |
        """)
    }


    @Test
    fun moveAfterCapture() {
        game.moveDown()
        tick(2)
        game.moveRight()
        tick(4)
        game.moveUp()
        tick(2)

        assertBoard("""
            |    CC  |
            |    CC  |
            |        |
            |        |
            |   BB   |
            |   BB   |
        """)
    }


    private fun tick(n: Int) {
        repeat(n) { game.tick() }
    }

    private fun assertBoard(expected: String) {
        assertThat(renderer.toString(), equalTo(expected.trimMargin()))
    }
}
