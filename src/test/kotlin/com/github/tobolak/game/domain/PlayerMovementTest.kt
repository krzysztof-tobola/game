package com.github.tobolak.game.domain

import com.github.tobolak.game.domain.geometry.Rectangle
import com.github.tobolak.game.domain.geometry.Vector
import com.github.tobolak.game.infra.SpritesRepositoryImpl
import org.hamcrest.Matchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Test

class PlayerMovementTest {
    private val h = 6
    private val w = 8
    private val renderer = FakeRenderer(w, h)
    private val bounds = Vector(w, h)
    private val outOfBounds = Rectangle(bounds, Vector(1, 1))

    private val game = Game(
            renderer = renderer,
            spritesRepository = SpritesRepositoryImpl(),
            bounds = bounds,
            playerPos = Rectangle(Vector(0, 0), Vector(2, 2)),
            itemPos = outOfBounds,
            boxPos = outOfBounds,
            npcPos = outOfBounds
    )

    @Test
    fun moveDown() {
        game.moveDown()
        tick(1)

        assertBoard("""
            |        |
            |PP      |
            |PP      |
            |        |
            |        |
            |        |
        """)
    }

    @Test
    fun moveRight() {
        game.moveRight()
        tick(1)

        assertBoard("""
            | PP     |
            | PP     |
            |        |
            |        |
            |        |
            |        |
        """)
    }

    @Test
    fun moveRightTwice() {
        game.moveRight()
        tick(2)

        assertBoard("""
            |  PP    |
            |  PP    |
            |        |
            |        |
            |        |
            |        |
        """)
    }

    @Test
    fun moveRightLeft() {
        game.moveRight()
        tick(2)
        game.moveLeft()
        tick(1)


        assertBoard("""
            | PP     |
            | PP     |
            |        |
            |        |
            |        |
            |        |
        """)
    }

    @Test
    fun moveToBottomRightBounds() {
        game.moveRight()
        tick(w + 1)
        game.moveDown()
        tick(h + 1)


        assertBoard("""
            |        |
            |        |
            |        |
            |        |
            |      PP|
            |      PP|
        """)
    }


    @Test
    fun moveToUpperLeftBounds() {
        game.moveLeft()
        tick(w + 1)
        game.moveUp()
        tick(h + 1)


        assertBoard("""
            |PP      |
            |PP      |
            |        |
            |        |
            |        |
            |        |
        """)
    }


    @Test
    fun stopMoving() {
        game.moveRight()
        tick(1)
        game.stopMoving()
        tick(1)


        assertBoard("""
            | PP     |
            | PP     |
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
