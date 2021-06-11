package com.forntoh.twofoureight.model

import androidx.compose.runtime.*

class Game(
    size: Int
) {
    private val grid = Grid(size)

    var gridState by mutableStateOf(grid.grid)

    init {
        for (i in 0 until grid.size / 2) {
            grid.addTile()
        }
    }

    private var score = 0

    private fun slide(row: List<Int>): Array<Int> {
        val arr = row.filter { it > 0 }.toMutableList()
        val missing = List(grid.size - arr.size) { 0 }
        arr.addAll(0, missing)
        return arr.toTypedArray()
    }

    private fun combine(row: List<Int>): Array<Int> {
        val arr = row.toMutableList()
        for (i in grid.size - 1 downTo 1) {
            val a = row[i]
            val b = row[i - 1]
            if (a == b) {
                arr[i] = a + b
                arr[i - 1] = 0
                score += arr[i]
            }
        }
        return arr.toTypedArray()
    }

    private fun operate(row: List<Int>): Array<Int> {
        var arr = slide(row)
        arr = combine(arr.toList())
        arr = slide(arr.toList())
        return arr
    }

    fun swipe(direction: Swipe) {
        var flipped = false
        var transposed = false

        when (direction) {
            Swipe.LEFT -> {
                grid.transpose()
                grid.flip()
                transposed = true
                flipped = true
            }
            Swipe.RIGHT -> {
                grid.transpose()
                transposed = true
            }
            Swipe.UP -> {
                grid.flip()
                flipped = true
            }
            else -> Unit
        }

        val past = grid.copyOf()

        for (i in 0 until grid.size) {
            grid[i] = operate(grid[i].asList())
        }

        val changed = !grid.isEqualTo(past)

        if (flipped) grid.flip()
        if (transposed) grid.transpose()

        if (changed) grid.addTile()

        gridState = grid.copyOf()
    }

    enum class Swipe {
        LEFT, RIGHT, UP, DOWN
    }

}