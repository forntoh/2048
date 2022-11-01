package com.forntoh.twofoureight.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class Tile(
    x: Int = 0,
    y: Int = 0,
    var value: Int = 0
) {
    var x by mutableStateOf(x)
    var y by mutableStateOf(y)
}