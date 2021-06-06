package com.forntoh.twofoureight.ui.theme

import androidx.compose.ui.graphics.Color

val Black = Color(0xFF000000)
val White = Color(0xFFC8C8C8)

private const val percent = 40

val Tiles = mapOf(
    0 to 1.alpha(percent),
    2 to 2.alpha(percent),
    4 to 3.alpha(percent),
    8 to 4.alpha(percent),
    16 to 5.alpha(percent),
    32 to 6.alpha(percent),
    64 to 7.alpha(percent),
    128 to 8.alpha(percent),
    256 to 9.alpha(percent),
    512 to 10.alpha(percent),
    1024 to 11.alpha(percent),
    2048 to 14.alpha(percent),
    4096 to 15.alpha(percent),
    8192 to 16.alpha(percent),
)

private fun Int.alpha(percent: Int) = this * (100f / percent / 100f)
