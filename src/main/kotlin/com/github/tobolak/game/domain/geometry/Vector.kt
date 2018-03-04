package com.github.tobolak.game.domain.geometry

data class Vector(val x: Int, val y: Int) {
    operator fun plus(v: Vector) = Vector(x + v.x, y + v.y)
    operator fun times(v: Vector) = Vector(x * v.x, y * v.y)
    infix fun until(v: Vector) = (x until v.x).flatMap { x -> (y until v.y).map { y -> Vector(x, y) } }
    operator fun div(i: Int) = Vector(x / i, y / i)
    operator fun div(v: Vector) = Vector(x / v.x, y / v.y)
}
