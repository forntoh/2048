package com.forntoh.twofoureight

fun Int.random(size: Int, unique: Boolean = true): MutableList<Int> {
    return if (unique) (0 until size).shuffled().take(this).toMutableList()
    else IntArray(this) { (0 until size).random() }.toMutableList()
}

fun Int.random(size: Int): Pair<MutableList<Int>, MutableList<Int>> {
    val taker = listOf(true, false).shuffled()
    val x = this.random(size, taker[0])
    val y = this.random(size, taker[1])
    return Pair(x, y)
}