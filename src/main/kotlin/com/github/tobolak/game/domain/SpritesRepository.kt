package com.github.tobolak.game.domain

interface SpritesRepository {
    fun byName(name: String): Sprite
    fun findAll(): Collection<Sprite>
}

