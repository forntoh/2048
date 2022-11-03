package com.forntoh.twofoureight.model

import kotlin.random.Random

class Grid(
    size: Int,
    init: List<IntArray>
) : MutableList<IntArray> by MutableList(size, { init[it] }) {

    init {
        if (this.all { m -> m.all { n -> n == 0 } })
            repeat(size / 2) { addTile() }
    }

    fun addTile() {
        val options = mutableListOf<Pair<Int, Int>>()
        for (i in 0 until size) {
            for (j in 0 until size) {
                if (this[i][j] == 0) {
                    options.add(Pair(i, j))
                }
            }
        }
        if (options.size > 0) {
            val (x, y) = options.random()
            this[x][y] = if (Random.nextFloat() <= 0.1) 4 else 2
        }
    }

    fun transpose() {
        val transpose = Array(size) { IntArray(size) { 0 } }
        for (i in 0 until size) {
            for (j in 0 until size) {
                transpose[j][i] = this[i][j]
            }
        }
        for (i in 0 until size) {
            this[i] = transpose[i]
        }
    }

    fun flip() {
        for (i in 0 until size) {
            this[i].reverse()
        }
    }

    fun isEqualTo(second: List<IntArray>): Boolean {
        if (this.size != second.size) return false
        return this.contentEquals(second)
    }

    private fun contentEquals(second: List<IntArray>): Boolean {
        for (i in this.indices) {
            if (!this[i].contentEquals(second[i])) return false
        }
        return true
    }

    fun copyOf() = this.toTypedArray().copyOf().toList()

}