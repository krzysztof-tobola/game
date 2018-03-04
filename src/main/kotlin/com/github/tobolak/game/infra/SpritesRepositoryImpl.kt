package com.github.tobolak.game.infra

import com.github.tobolak.game.domain.Sprite
import com.github.tobolak.game.domain.SpritesRepository
import io.vavr.API
import java.util.*

class SpritesRepositoryImpl : SpritesRepository {
    private val sprites = API.List("player", "player-item", "item", "box", "box-item", "npc")
            .toMap { API.Tuple(it, Sprite(it)) }

    override fun byName(name: String): Sprite = sprites[name].getOrElseThrow { NoSuchElementException(name) }
    override fun findAll(): Collection<Sprite> = sprites.values().asJava()
}