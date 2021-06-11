package com.forntoh.twofoureight

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.forntoh.twofoureight.ui.components.Footer
import com.forntoh.twofoureight.ui.components.GameBoard
import com.forntoh.twofoureight.ui.components.Header
import com.forntoh.twofoureight.ui.theme.GameTheme
import com.forntoh.twofoureight.ui.theme.Padding
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.statusBarsPadding

@Composable
fun GameApp() {
    ProvideWindowInsets {
        GameTheme {
            Scaffold {
                Column(
                    modifier = Modifier
                        .padding(Padding.large)
                        .statusBarsPadding(),
                    verticalArrangement = Arrangement.spacedBy(Padding.large + 4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Header(score = 24, bestScore = 1068)

                    Text(text = "Join the tiles and get to the 2048 tile!")

                    GameBoard()

                    Spacer(modifier = Modifier.weight(1f))

                    Footer(moves = 10, millisecondsElapsed = 534687531)
                }
            }
        }
    }
}