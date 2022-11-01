package com.forntoh.twofoureight.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.forntoh.twofoureight.model.Game
import com.forntoh.twofoureight.store.PreferenceRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import java.util.*
import kotlin.time.Duration
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
            onMove = { preferenceRepository.moves++ }
        )

        preferenceRepository.useSystemUiMode = true

        setContent {
            GameApp(
                preferenceRepository = preferenceRepository,
                game = game
            )
        }

        tickerFlow(Duration.seconds(1))
            .map { Calendar.getInstance() }
            .distinctUntilChanged { old, new ->
                old.get(Calendar.SECOND) == new.get(Calendar.SECOND)
            }
            .onEach {
                setDateTime(it)
            }
            .launchIn(GlobalScope)
    }

}

@ExperimentalTime
fun tickerFlow(
    period: Duration,
    initialDelay: Duration = Duration.ZERO
) = flow {
    delay(initialDelay)
    while (true) {
        emit(Unit)
        delay(period)
    }
}