package com.forntoh.twofoureight.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.forntoh.twofoureight.ui.theme.Padding
import com.forntoh.twofoureight.ui.theme.Tiles

@Composable
fun TileBox(
    tile: Int,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(
                MaterialTheme.colors.onSurface.copy(alpha = Tiles[tile] ?: Tiles[8192]!!),
                MaterialTheme.shapes.small
            )
            .padding(Padding.medium)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            content()
        }
    }
}