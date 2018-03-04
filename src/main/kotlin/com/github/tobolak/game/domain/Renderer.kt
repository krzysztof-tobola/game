package com.github.tobolak.game.domain

interface Renderer {
    fun render(painter: (Gfx) -> Unit);
}