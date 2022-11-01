package com.forntoh.twofoureight.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.forntoh.twofoureight.model.Game
import com.forntoh.twofoureight.store.PreferenceRepository
import com.forntoh.twofoureight.ui.play.PlayScreen
import com.forntoh.twofoureight.ui.theme.GameTheme
import com.google.accompanist.insets.ProvideWindowInsets

@Composable
fun GameApp(
    preferenceRepository: PreferenceRepository,
    game: Game,
) {
    ProvideWindowInsets {
        GameTheme(darkTheme = preferenceRepository.isDarkTheme) {
            Scaffold { padding ->

                val score by preferenceRepository.scoreLive.observeAsState(0)
                val highScore by preferenceRepository.highScoreLive.observeAsState(0)
                val moves by preferenceRepository.movesLive.observeAsState(0)
                val timeElapsed by preferenceRepository.timeElapsedLive.observeAsState(0)

                PlayScreen(
                    score = score,
                    bestScore = highScore,
                    moves = moves,
                    timeElapsed = timeElapsed,
                    game = game,
                    modifier = Modifier.padding(padding),
                    onNewRequest = {
                        preferenceRepository.score = 0
                        preferenceRepository.moves = 0
                        preferenceRepository.timeElapsed = 0
                        game.restart()
                    },
                    onUndoRequest = {

                    }
                )
            }
        }
    }
}