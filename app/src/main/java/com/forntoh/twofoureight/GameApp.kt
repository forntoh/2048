package com.forntoh.twofoureight

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import com.forntoh.twofoureight.ui.components.GameBoard
import com.forntoh.twofoureight.ui.theme.GameTheme

@Composable
fun GameApp() {
    GameTheme {
        Scaffold {
            GameBoard()
        }
    }
}