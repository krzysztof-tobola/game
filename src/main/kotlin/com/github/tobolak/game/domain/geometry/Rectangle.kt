package com.github.tobolak.game.domain.geometry

import kotlin.math.max
import kotlin.math.min

data class Rectangle(val origin: Vector, val d: Vector) : Iterable<Vector> {
    operator fun div(v: Vector) = Rectangle(Vector(origin.x / v.x, origin.y / v.y), Vector(d.x / v.x, d.y / v.y))
    operator fun times(v: Vector) = Rectangle(origin * v, d * v)
    operator fun plus(v: Vector) = copy(origin = origin + v)
    infix fun within(v: Vector) = origin == Vector(max(min(origin.x, v.x - d.x), 0), max(min(origin.y, v.y - d.y), 0))
    override fun iterator() = (origin until origin + d).iterator()
    infix fun intersects(other: Rectangle): Boolean {
        return toAwt().intersects(other.toAwt())
    }

    private fun toAwt(): java.awt.Rectangle {
        return java.awt.Rectangle(origin.x, origin.y, d.x, d.y)
    }
}
