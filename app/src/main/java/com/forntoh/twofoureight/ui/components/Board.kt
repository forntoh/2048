package com.forntoh.twofoureight.ui.components

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.forntoh.twofoureight.model.Board
import com.forntoh.twofoureight.ui.theme.Padding

@Composable
fun GameBoard(boardSize: Int = 4) {

    val board = Board(4)

    val tiles by board.tilesO.observeAsState()

    BoxWithConstraints(
        modifier = Modifier
            .padding(Padding.large, 64.dp)
            .aspectRatio(1f)
            .border(
                width = Dp.Hairline,
                color = MaterialTheme.colors.onSurface.copy(0.25f),
                shape = MaterialTheme.shapes.large
            )
            .padding(Padding.medium)
            .pointerInput(Unit) {
                var direction = Board.Swipe.LEFT
                detectDragGestures(
                    onDragEnd = { board.swipe(direction) },
                ) { change, dragAmount ->
                    change.consumeAllChanges()
                    val (x, y) = dragAmount
                    val (pX, pY) = change.previousPosition

                    when {
                        x > 0 -> direction = Board.Swipe.RIGHT
                        x < 0 -> direction = Board.Swipe.LEFT
                    }
                    when {
                        y > 0 -> direction = Board.Swipe.DOWN
                        y < 0 -> direction = Board.Swipe.UP
                    }
//                    Log.d("TAGERs", "GameBoard: $direction $dragAmount")
                }
            }
    ) {
        val tileSize = maxWidth / boardSize

        tiles?.forEach {
            GameTile(number = it.value, size = tileSize, it.xPos, it.yPos)
        }
    }
}