package com.forntoh.twofoureight.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
                detectHorizontalDragGestures(
                    onDragEnd = { board.swipe(direction) },
                ) { change, x ->
                    change.consumeAllChanges()
                    when {
                        x > 0 -> direction = Board.Swipe.RIGHT
                        x < 0 -> direction = Board.Swipe.LEFT
                    }
                }
            }
            .pointerInput(Unit) {
                var direction = Board.Swipe.LEFT
                detectVerticalDragGestures(
                    onDragEnd = { board.swipe(direction) },
                ) { change, y ->
                    change.consumeAllChanges()
                    when {
                        y > 0 -> direction = Board.Swipe.DOWN
                        y < 0 -> direction = Board.Swipe.UP
                    }
                }

            }
    ) {
        val tileSize = maxWidth / boardSize

        tiles?.let {
            for (i in 0 until board.size) {
                for (j in 0 until board.size) {
                    GameTile(number = it[i][j], size = tileSize, i, j)
                }
            }
        }
    }
}