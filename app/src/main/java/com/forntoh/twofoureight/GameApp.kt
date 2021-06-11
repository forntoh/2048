package com.forntoh.twofoureight

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.forntoh.twofoureight.model.Game
import com.forntoh.twofoureight.model.Grid
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
                        .statusBarsPadding()
                        .padding(Padding.large),
                    verticalArrangement = Arrangement.spacedBy(Padding.large),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val game = Game(4)

                    Header(
                        score = 0,
                        bestScore = 1068
                    )

                    Text(text = stringResource(R.string.play_prompt))

                    GameBoard(
                        game = game,
                        onSwipe = game::swipe
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Footer(
                        moves = 10,
                        millisecondsElapsed = 34687531
                    )
                }
            }
        }
    }
}