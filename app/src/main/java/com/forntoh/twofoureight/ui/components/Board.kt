package com.forntoh.twofoureight.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.forntoh.twofoureight.model.Game
import com.forntoh.twofoureight.model.Grid
import com.forntoh.twofoureight.model.Tile
import com.forntoh.twofoureight.ui.theme.Padding

@Composable
fun GameBoard(boardSize: Int = 4) {

    val grid = remember { Grid(boardSize) }
    val game = Game(grid)

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
                var direction: Game.Swipe? = null

                detectHorizontalDragGestures(
                    onDragEnd = { direction?.let { game.swipe(it) } },
                ) { change, x ->
                    change.consumeAllChanges()
                    when {
                        x > 50 -> direction = Game.Swipe.RIGHT
                        x < -50 -> direction = Game.Swipe.LEFT
                    }
                }
            }
            .pointerInput(Unit) {
                var direction: Game.Swipe? = null
                detectVerticalDragGestures(
                    onDragEnd = { direction?.let { game.swipe(it) } },
                ) { change, y ->
                    change.consumeAllChanges()
                    when {
                        y > 50 -> direction = Game.Swipe.DOWN
                        y < -50 -> direction = Game.Swipe.UP
                    }
                }
            }
    ) {
        val tileSize = maxWidth / boardSize
        for (i in 0 until boardSize) {
            for (j in 0 until boardSize) {
                GameTile(number = game.gridState[i][j], size = tileSize, i, j)
            }
        }
    }
}