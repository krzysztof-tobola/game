package com.github.tobolak.game.domain

import com.github.tobolak.game.domain.geometry.Rectangle
import com.github.tobolak.game.domain.geometry.Vector
import com.github.tobolak.game.infra.SpritesRepositoryImpl
import org.hamcrest.Matchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Test

class MovementTest {
    private val h = 6
    private val w = 8
    private val renderer = FakeRenderer(h, w)
    private val bounds = Vector(w, h)
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

    @Test
    fun moveDown() {
        game.moveDown()
        tick(1)

        assertBoard("""
            |        |
            |PP      |
            |PP     G|
            |    D   |
            |   BB   |
            |   BB   |
        """)
    }

    @Test
    fun moveRight() {
        game.moveRight()
        tick(1)

        assertBoard("""
            | PP     |
            | PP     |
            |       G|
            |    D   |
            |   BB   |
            |   BB   |
        """)
    }

    @Test
    fun moveRightTwice() {
        game.moveRight()
        tick(2)

        assertBoard("""
            |  PP    |
            |  PP    |
            |       G|
            |    D   |
            |   BB   |
            |   BB   |
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
            |       G|
            |    D   |
            |   BB   |
            |   BB   |
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
            |       G|
            |    D   |
            |   BB PP|
            |   BB PP|
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
            |       G|
            |    D   |
            |   BB   |
            |   BB   |
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
            |       G|
            |    D   |
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
