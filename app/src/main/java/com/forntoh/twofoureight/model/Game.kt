package com.forntoh.twofoureight.model

class Game(
    val size: Int,
    var score: Int = 0,
    val state: List<IntArray> = emptyList(),
    val onScoreChange: (Int) -> Unit = {},
    var onGameWon: () -> Unit = {},
    var onGameOver: () -> Unit = {},
    val onMove: (Grid) -> Unit = {}
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

    private fun slide(row: IntArray): IntArray {
        val arr = row.filter { it > 0 }.toMutableList()
        val missing = List(grid.size - arr.size) { 0 }
        arr.addAll(0, missing)
        return arr.toIntArray()
    }

    private fun combine(row: IntArray): IntArray {
        var arr = row
        for (i in grid.size - 1 downTo 1) {
            val a = arr[i]
            val b = arr[i - 1]
            if (a == b && a != 0) {
                arr[i] = a + b
                arr[i - 1] = 0
                arr = slide(arr)
                if (score + arr[i] != score) {
                    score += arr[i]
                    onScoreChange(score)
                }
            }
        }
        return arr
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

    private fun operate(row: IntArray): IntArray = with(row) {
        slide(this)
        combine(this)
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
            _grid[i] = operate(grid[i])
        }

        val changed = !_grid.isEqualTo(past)

        if (flipped) _grid.flip()
        if (transposed) _grid.transpose()
        if (changed) _grid.addTile()

        onMove(_grid)

        isGameWon()
        isGameOver()
    }

    enum class Swipe {
        LEFT, RIGHT, UP, DOWN
    }

}