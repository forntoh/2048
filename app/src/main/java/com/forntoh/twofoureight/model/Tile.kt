package com.forntoh.twofoureight.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class Tile(x: Int, y: Int, value: Int) {
    var x by mutableStateOf(x)
    var y by mutableStateOf(y)
    var value by mutableStateOf(value)
}