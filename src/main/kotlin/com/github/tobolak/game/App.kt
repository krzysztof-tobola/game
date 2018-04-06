package com.github.tobolak.game

import com.github.tobolak.game.domain.Game
import com.github.tobolak.game.domain.geometry.Rectangle
import com.github.tobolak.game.domain.geometry.Vector
import com.github.tobolak.game.infra.SpritesRepositoryImpl
import com.github.tobolak.game.ui.ImageProvider
import com.github.tobolak.game.ui.MainFrame
import com.github.tobolak.game.ui.SwingRenderer
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.awt.event.KeyEvent.*
import java.util.*
import javax.swing.SwingUtilities.invokeLater

object App {
    @JvmStatic
    fun main(args: Array<String>) = invokeLater {
        val frame = MainFrame(SwingRenderer(ImageProvider()))
        val game = Game(
                renderer = frame,
                spritesRepository = SpritesRepositoryImpl(),
                bounds = Vector(160, 90),
                playerPos = Rectangle(Vector(0, 0), Vector(14, 14)),
                itemPos = Rectangle(Vector(80, 45), Vector(6, 6)),
                boxPos = Rectangle(Vector(70, 70), Vector(20, 20)),
                npcPos = Rectangle(Vector(100, 30), Vector(10, 10))
        )

        frame.addKeyListener(object : KeyAdapter() {
            override fun keyPressed(event: KeyEvent?) {
                when (event?.keyCode) {
                    VK_UP,
                    VK_F1, VK_F2, VK_F3, VK_F4, VK_F5, VK_F6,
                    VK_F7, VK_F8, VK_F10, VK_F11, VK_F12,
                    VK_4, VK_5, VK_6, VK_7 -> game.moveUp()

                    VK_LEFT,
                    VK_1, VK_2, VK_3,
                    VK_Q, VK_W, VK_E, VK_R, VK_T,
                    VK_A, VK_S, VK_D, VK_F,
                    VK_Z, VK_X, VK_C
                    -> game.moveLeft()

                    VK_RIGHT,
                    VK_8, VK_9, VK_0,
                    VK_Y, VK_U, VK_I, VK_O, VK_P,
                    VK_G, VK_H, VK_J, VK_K, VK_L,
                    VK_N, VK_M -> game.moveRight()
                    VK_V, VK_B, VK_DOWN, VK_SPACE -> game.moveDown()
                }
            }

            override fun keyReleased(e: KeyEvent?) = game.stopMoving()
        })

        start(game)
    }

    private fun start(game: Game) = Timer().scheduleAtFixedRate(
            object : TimerTask() {
                override fun run() = game.tick()
            },
            0,
            (1000L / 24)
    )
}
