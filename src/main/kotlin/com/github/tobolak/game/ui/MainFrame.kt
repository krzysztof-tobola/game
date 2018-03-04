package com.github.tobolak.game.ui

import com.github.tobolak.game.domain.Renderer
import java.awt.Color
import javax.swing.JFrame

class MainFrame(private val screen: SwingRenderer) : JFrame(), Renderer by screen {
    init {
        add(screen)
        background = Color.white
        title = "Some game :)"
        defaultCloseOperation = EXIT_ON_CLOSE
        pack()
        setLocationRelativeTo(null)
        isVisible = true
    }
}