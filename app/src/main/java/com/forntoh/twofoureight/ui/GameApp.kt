package com.forntoh.twofoureight.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.forntoh.twofoureight.ui.play.PlayScreen
import com.forntoh.twofoureight.ui.theme.GameTheme
import com.google.accompanist.insets.ProvideWindowInsets

@Composable
fun GameApp(
    gameViewModel: GameViewModel = viewModel(factory = GameVieModelFactory(LocalContext.current))
) {
    val isDarkTheme by gameViewModel.isDarkTheme.collectAsState()
    val game by gameViewModel.game.collectAsState()

    ProvideWindowInsets {
        GameTheme(darkTheme = isDarkTheme) {
            Scaffold { padding ->

                val score by gameViewModel.score.collectAsState()
                val highScore by gameViewModel.highScore.collectAsState()
                val moves by gameViewModel.moves.collectAsState()
                val timeElapsed by gameViewModel.playTimeInSecs.collectAsState()

                PlayScreen(
                    score = score,
                    bestScore = highScore,
                    moves = moves,
                    timeElapsed = timeElapsed,
                    game = game,
                    modifier = Modifier.padding(padding),
                    onNewRequest = gameViewModel::newGame,
                    onUndoRequest = gameViewModel::undoMove
                )
            }
        }
    }
}