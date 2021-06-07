package com.forntoh.twofoureight.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.forntoh.twofoureight.flip
import com.forntoh.twofoureight.isEqualTo
import com.forntoh.twofoureight.transpose
import kotlin.random.Random

data class Board(
    val size: Int = 4
) {
    var score = 0

    fun isGameOver(): Boolean {
        for (i in 0 until size) {
            for (j in 0 until size) {
                if (tiles[i][j] == 0)
                    return false
                if (i != 3 && tiles[i][j] == tiles[i + 1][j])
                    return false
                if (j != 3 && tiles[i][j] == tiles[i][j + 1])
                    return false
            }
        }
        return true
    }

    private var tiles = Array(size) { Array(size) { 0 } }

    private val _tilesO = MutableLiveData<Array<Array<Int>>>()
    val tilesO = _tilesO as LiveData<Array<Array<Int>>>

    init {
        generateTiles()
    }

    private fun generateTiles() {
        addTile()
        addTile()
        _tilesO.postValue(tiles)
    }

    private fun addTile() {
        val options = mutableListOf<Pair<Int, Int>>()
        for (i in 0 until size) {
            for (j in 0 until size) {
                if (tiles[i][j] == 0) {
                    options.add(Pair(i, j))
                }
            }
        }
        if (options.size > 0) {
            val (x, y) = options.random()
            tiles[x][y] = if (Random.nextFloat() <= 0.1) 4 else 2
        }
    }

    private fun slide(row: List<Int>): Array<Int> {
        val arr = row.filter { it > 0 }.toMutableList()
        val missing = List(size - arr.size) { 0 }
        arr.addAll(0, missing)
        return arr.toTypedArray()
    }

    private fun combine(row: List<Int>): Array<Int> {
        val arr = row.toMutableList()
        for (i in size - 1 downTo 1) {
            val a = row[i]
            val b = row[i - 1]
            if (a == b) {
                arr[i] = a + b
                score += arr[i]
                arr[i - 1] = 0
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
                tiles.transpose()
                tiles.flip()
                transposed = true
                flipped = true
            }
            Swipe.RIGHT -> {
                tiles.transpose()
                transposed = true
            }
            Swipe.UP -> {
                tiles.flip()
                flipped = true
            }
            else -> {
            }
        }

        val past = tiles.copyOf()

        for (i in 0 until size) {
            tiles[i] = operate(tiles[i].asList())
        }

        val changed = !past.isEqualTo(tiles)

        if (flipped) tiles.flip()
        if (transposed) tiles.transpose()

        if (changed) addTile()

        _tilesO.value = tiles.copyOf()
    }

    enum class Swipe {
        LEFT, RIGHT, UP, DOWN
    }
}