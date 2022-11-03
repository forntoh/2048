package com.forntoh.twofoureight.model

import kotlin.random.Random

class Grid(size: Int) : MutableList<IntArray> by MutableList(size, { IntArray(size) { 0 } }) {

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

    fun isEqualTo(second: List<IntArray>): Boolean {
        if (this.contentEquals(second)) return true
        if (this.size != second.size) return false
        for (i in this.indices) {
            if (this[i].size != second[i].size) {
                return false
            }
            for (j in this[i].indices) {
                if (this[i][j] != second[i][j]) {
                    return false
                }
            }
        }
        return true
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

    private fun contentEquals(second: List<IntArray>): Boolean =
        this.toTypedArray().contentEquals(second.toTypedArray())

    fun copyOf() = this.toTypedArray().copyOf().toList()

}