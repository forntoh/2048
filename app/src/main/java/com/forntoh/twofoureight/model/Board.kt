package com.forntoh.twofoureight.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.forntoh.twofoureight.random

data class Board(
    val size: Int = 4
) {

    var arr = Array(size) { IntArray(size) { 0 } }

    private var _tiles: MutableList<Tile> = mutableListOf()
    val tiles = _tiles as List<Tile>

    private val _tilesO: MutableLiveData<List<Tile>> = MutableLiveData()
    val tilesO = _tilesO as LiveData<List<Tile>>

    init {
        generateTiles()
    }

    private fun generateTiles() {

        val (x, y) = 4.random(size)

        y.forEachIndexed { i, _ ->
            _tiles.add(Tile(2, x[i], i))
        }

        _tilesO.postValue(tiles)
    }

    fun swipe(direction: Swipe) {

        val isVertical = direction == Swipe.DOWN || direction == Swipe.UP
        val right = direction == Swipe.DOWN || direction == Swipe.RIGHT

        val tilesTemp = mutableListOf<Tile>()

        (0 until size).forEach { i ->
            val filtered = _tiles.filter { if (isVertical) it.xPos == i else it.yPos == i }

            val reduced: List<Tile> =
                if (filtered.size == 1) listOf(
                    when (direction) {
                        Swipe.LEFT -> filtered.first().copy(xPos = 0)
                        Swipe.RIGHT -> filtered.first().copy(xPos = size - 1)
                        Swipe.UP -> filtered.first().copy(yPos = 0)
                        Swipe.DOWN -> filtered.first().copy(yPos = size - 1)
                    }
                ) else {
                    if (right) filtered.reduceRight { tile, acc ->
                        Log.d("TAGER", "\ntile: $tile acc: $acc")
                        if (acc.value == tile.value) acc.copy(value = acc.value + tile.value)
                        else tile
                    } else filtered.reduce { tile, acc ->
                        Log.d("TAGER", "\ntile: $tile acc: $acc")
                        if (acc.value == tile.value) acc.copy(value = acc.value + tile.value)
                        else acc
                    }
                }

            tilesTemp.addAll(reduced)

            Log.d("TAGER", "$direction \nfiltered: $filtered\nreduced: $reduced")
//            Log.d("TAGER", "$direction \nfiltered: $fff")
        }

        var tile: Tile
        while (tilesTemp.contains(run {
                val (m, n) = 1.random(size)
                tile = Tile(2, m.first(), n.first())
                tile
            })) {
            tilesTemp.add(tile)
        }

        _tilesO.postValue(tilesTemp)
        _tiles = tilesTemp
    }

    enum class Swipe {
        LEFT, RIGHT, UP, DOWN
    }

}

public inline fun <S, T : S> Iterable<T>.reduce(operation: (acc: S, T) -> S): List<S> {
    val list = mutableListOf<S>()

    val iterator = this.iterator()
    if (!iterator.hasNext()) return emptyList()

    var accumulator: S = iterator.next()
    while (iterator.hasNext()) {
        accumulator = operation(accumulator, iterator.next())
        list.add(accumulator)
    }
    return list
}

public inline fun <S, T : S> List<T>.reduceRight(operation: (T, acc: S) -> S): List<S> {
    val list = mutableListOf<S>()

    val iterator = listIterator(size)
    if (!iterator.hasPrevious()) return emptyList()

    var accumulator: S = iterator.previous()
    while (iterator.hasPrevious()) {
        accumulator = operation(iterator.previous(), accumulator)
        list.add(accumulator)
    }
    return list
}
