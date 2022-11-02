package com.forntoh.twofoureight.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.forntoh.twofoureight.model.Game
import com.forntoh.twofoureight.store.PreferenceRepository

class MainActivity : ComponentActivity() {

    private val preferenceRepository: PreferenceRepository by lazy { PreferenceRepository(this) }

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
                game = game,
            )
        }
    }

    override fun onResume() {
        super.onResume()
        preferenceRepository.paused = true
    }

}