package com.github.tobolak.game.ui

import com.github.tobolak.game.domain.Gfx
import com.github.tobolak.game.domain.geometry.Rectangle
import com.github.tobolak.game.domain.Renderer
import com.github.tobolak.game.domain.Sprite
import com.github.tobolak.game.domain.geometry.Vector
import java.awt.*
import javax.swing.JComponent


class SwingRenderer(private val images: ImageProvider) : JComponent(), Renderer {
    private var painter: (Gfx) -> Unit = {}

    override fun render(painter: (Gfx) -> Unit) {
        this.painter = painter
        repaint()
    }

    init {
        val scale = 50
        isDoubleBuffered = true
        preferredSize = Dimension(16 * scale, 9 * scale)
        background = Color.white
        isOpaque = true
    }

    override fun paintComponent(gfx: Graphics?) {
        super.paintComponent(gfx)
        if (gfx != null && gfx is Graphics2D) {
            painter(toGfx(gfx))
            Toolkit.getDefaultToolkit().sync()
        }
    }

    private fun toGfx(gfx: Graphics2D): Gfx {
        return object : Gfx {
            override val clip = Vector(gfx.clipBounds.width, gfx.clipBounds.height)

            override fun sprite(sprite: Sprite, rectangle: Rectangle) = draw(images.get(sprite), rectangle)

            private fun draw(image: Image, r: Rectangle) {
                gfx.drawImage(image, r.origin.x, r.origin.y, r.d.x, r.d.y, null)
            }
        }
    }
}