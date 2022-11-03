package com.forntoh.twofoureight.model

class Game(
    val size: Int,
    private var score: Int = 0,
    val state: List<IntArray> = emptyList(),
    val onScoreChange: (Int) -> Unit = {},
    var onGameWon: () -> Unit = {},
    var onGameOver: () -> Unit = {},
    val onMove: () -> Unit = {}
) {
    private val _grid = Grid(size, state.ifEmpty { List(size) { IntArray(size) { 0 } } })
    val grid = _grid as List<IntArray>

    init {
        onScoreChange(score)
    }

    fun restart() {
        score = 0
        _grid.clear()
        repeat(size) { _grid.add(IntArray(size) { 0 }) }
        repeat(size / 2) { _grid.addTile() }
    }

    fun setValues(values: List<IntArray>) {
        _grid.clear()
        _grid.addAll(values)
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
        for (i in grid.indices)
            for (j in grid.indices) {
                if (grid[i][j] == 2048) {
                    onGameWon()
                }
            }
    }

    private fun isGameOver() {
        for (i in grid.indices)
            for (j in grid.indices) {
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
                _grid.transpose()
                _grid.flip()
                transposed = true
                flipped = true
            }
            Swipe.RIGHT -> {
                _grid.transpose()
                transposed = true
            }
            Swipe.UP -> {
                _grid.flip()
                flipped = true
            }
            else -> Unit
        }

        val past = _grid.copyOf()

        for (i in 0 until _grid.size) {
            _grid[i] = operate(grid[i].asList()).toIntArray()
        }

        val changed = !_grid.isEqualTo(past)

        if (flipped) _grid.flip()
        if (transposed) _grid.transpose()
        if (changed) _grid.addTile()

        onMove()

        isGameWon()
        isGameOver()
    }

    enum class Swipe {
        LEFT, RIGHT, UP, DOWN
    }

}