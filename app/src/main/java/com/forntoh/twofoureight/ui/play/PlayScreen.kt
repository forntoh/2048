package com.forntoh.twofoureight.ui.play

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.forntoh.twofoureight.R
import com.forntoh.twofoureight.model.Game
import com.forntoh.twofoureight.ui.components.Footer
import com.forntoh.twofoureight.ui.components.GameBoard
import com.forntoh.twofoureight.ui.components.Header
import com.forntoh.twofoureight.ui.theme.GameTheme
import com.forntoh.twofoureight.ui.theme.Padding
import com.google.accompanist.insets.statusBarsPadding

@Composable
fun PlayScreen(
    score: Int,
    bestScore: Int,
    moves: Int,
    game: Game,
    onNewRequest: () -> Unit = {},
    onUndoRequest: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .statusBarsPadding()
            .padding(Padding.large),
        verticalArrangement = Arrangement.spacedBy(Padding.large),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Header(
            score = score,
            bestScore = bestScore,
            onNewRequest = onNewRequest,
            onUndoRequest = onUndoRequest,
        )

        Text(text = stringResource(R.string.play_prompt))

        GameBoard(
            game = game,
            onSwipe = game::swipe
        )

        Spacer(modifier = Modifier.weight(1f))

        Footer(
            moves = moves,
            millisecondsElapsed = 34687531
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PlayScreenPreview() {
    GameTheme {
        PlayScreen(
            score = 24,
            bestScore = 1024,
            moves = 10,
            Game(4)
        )
    }
}