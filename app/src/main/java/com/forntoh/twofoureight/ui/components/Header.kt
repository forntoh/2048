package com.forntoh.twofoureight.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.forntoh.twofoureight.R
import com.forntoh.twofoureight.ui.theme.GameTheme

@Composable
fun Header(
    score: Int,
    bestScore: Int,
    modifier: Modifier = Modifier,
    onNewRequest: () -> Unit = {},
    onUndoRequest: () -> Unit = {},
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
    ) {
        TileBox(
            tile = 8,
            modifier = Modifier
                .aspectRatio(1 / 1.1f)
                .weight(1f)
        ) {
            Text(
                text = stringResource(R.string.app_name),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp,
                maxLines = 1,
                softWrap = false,
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .aspectRatio(1 / 1.1f)
                .weight(1f)
        ) {
            TileBox(
                tile = 2,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(
                    text = stringResource(R.string.score),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Black,
                    fontSize = 13.sp,
                    maxLines = 1,
                    softWrap = false,
                )
                Text(
                    text = score.toString(),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Black,
                    fontSize = 22.sp,
                    maxLines = 1,
                    softWrap = false,
                )
            }
            TileBox(
                tile = 16,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(.7f)
                    .clickable { onNewRequest() }
            ) {
                Text(
                    text = stringResource(R.string.action_new),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Black,
                    fontSize = 22.sp,
                    maxLines = 1,
                    softWrap = false,
                )
            }
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .aspectRatio(1 / 1.1f)
                .weight(1f)
        ) {
            TileBox(
                tile = 2,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(
                    text = stringResource(R.string.best),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Black,
                    fontSize = 13.sp,
                    maxLines = 1,
                    softWrap = false,
                )
                Text(
                    text = bestScore.toString(),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Black,
                    fontSize = 22.sp,
                    maxLines = 1,
                    softWrap = false,
                )
            }
            TileBox(
                tile = 16,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(.7f)
                    .clickable { onUndoRequest() }
            ) {
                Text(
                    text = stringResource(R.string.action_undo),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.W900,
                    fontSize = 22.sp,
                    maxLines = 1,
                    softWrap = false,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHeader() {
    GameTheme {
        Surface {
            Header(
                score = 36,
                bestScore = 1986,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewHeaderNight() {
    GameTheme {
        Surface {
            Header(
                score = 36,
                bestScore = 1986,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}