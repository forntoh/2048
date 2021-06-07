package com.forntoh.twofoureight

fun Int.random(size: Int, unique: Boolean = true): MutableList<Int> {
    return if (unique) (0 until size).shuffled().take(this).toMutableList()
    else IntArray(this) { (0 until size).random() }.toMutableList()
}

fun <T> Array<Array<T>>.isEqualTo(second: Array<Array<T>>): Boolean {
    if (this.contentEquals(second)) return true
    if (this.size != second.size) return false
    for (i in this.indices) {
        if (this[i].size != second[i].size) {
            return false
        }
        for (j in this[i].indices) {
            if (!this[i][j]?.equals(second[i][j])!!) {
                return false
            }
        }
    }
    return true
}

fun Array<Array<Int>>.transpose() {
    val transpose = Array(size) { Array(size) { 0 } }
    for (i in 0 until size) {
        for (j in 0 until size) {
            transpose[j][i] = this[i][j]
        }
    }

    for (i in 0 until size) {
        this[i] = transpose[i]
    }
}

fun Array<Array<Int>>.flip() {
    for (i in 0 until size) {
        this[i].reverse()
    }
}