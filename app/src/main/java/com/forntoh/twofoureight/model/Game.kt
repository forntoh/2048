package com.forntoh.twofoureight.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class Game(
    size: Int,
    val onScoreChange: (Int) -> Unit = {},
    var onGameWon: () -> Unit = {},
    var onGameOver: () -> Unit = {},
    val onMove: () -> Unit = {}
) {
    private val grid = Grid(size)

    var gridState by mutableStateOf(grid.grid)

    private var score = 0

    init {
        restart()
    }

    fun restart() {
        score = 0
        grid.reset()
        for (i in 0 until grid.size / 2) {
            grid.addTile()
        }
        gridState = grid.copyOf()
    }

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
                if (score + arr[i] != score) {
                    score += arr[i]
                    onScoreChange(score)
                }
            }
        }
        return arr.toTypedArray()
    }

    private fun isGameWon() {
        for (i in grid.grid.indices)
            for (j in grid.grid.indices) {
                if (grid[i][j] == 2048) {
                    onGameWon()
                }
            }
    }

    private fun isGameOver() {
        for (i in grid.grid.indices)
            for (j in grid.grid.indices) {
                if (grid[i][j] == 2048) {
                    if (grid[i][j] == 0)
                        return
                    if (i != grid.size - 1 && grid[i][j] == grid[i + 1][j])
                        return
                    if (j != grid.size - 1 && grid[i][j] == grid[i][j + 1])
                        return
                }
            }
        onGameOver()
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

        if (changed) {
            grid.addTile()
            onMove()
        }

        gridState = grid.copyOf()

        isGameWon()
        isGameOver()
    }

    enum class Swipe {
        LEFT, RIGHT, UP, DOWN
    }

}