package com.forntoh.twofoureight.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.forntoh.twofoureight.model.Game
import com.forntoh.twofoureight.store.PreferenceRepository
import com.forntoh.twofoureight.tickerFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime

class MainActivity : ComponentActivity() {

    private val preferenceRepository: PreferenceRepository by lazy { PreferenceRepository(this) }

    @ExperimentalTime
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        val game = Game(
            size = 4,
            onScoreChange = { preferenceRepository.score = it },
            onMove = {
                preferenceRepository.moves++
                preferenceRepository.paused = false
            },
        )

        preferenceRepository.useSystemUiMode = true

        setContent {
            GameApp(
                preferenceRepository = preferenceRepository,
                game = game
            )
        }

        tickerFlow(1.seconds)
            .map { preferenceRepository.paused }
            .distinctUntilChanged { _, new -> new }
            .onEach { preferenceRepository.timeElapsed++ }
            .launchIn(lifecycleScope)
    }

    override fun onResume() {
        super.onResume()
        preferenceRepository.paused = true
    }

}