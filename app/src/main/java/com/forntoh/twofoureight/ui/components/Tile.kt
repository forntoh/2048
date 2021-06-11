package com.forntoh.twofoureight.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.forntoh.twofoureight.ui.theme.GameTheme
import com.forntoh.twofoureight.ui.theme.Padding

@Composable
fun GameTile(number: Int, size: Dp, dx: Int = 0, dy: Int = 0) {

    val x by animateDpAsState(targetValue = Dp(dx * size.value))
    val y by animateDpAsState(targetValue = Dp(dy * size.value))

    TileBox(
        tile = number,
        modifier = Modifier
            .size(size)
            .padding(Padding.small)
            .absoluteOffset(x, y)
    ) {
        var textSize by remember { mutableStateOf(56.sp) }

        if (number != 0) Text(
            text = "$number",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = textSize,
            maxLines = 1,
            softWrap = false,
            onTextLayout = { textLayoutResult ->
                if (textLayoutResult.hasVisualOverflow) {
                    textSize *= 0.9
                }
            },
            color =
            if (number > 256) MaterialTheme.colors.surface
            else MaterialTheme.colors.onSurface
        )
    }
}

@Preview
@Composable
fun TilePreview() {
    GameTheme(darkTheme = true) {
        GameTile(1024, 92.dp)
    }
}