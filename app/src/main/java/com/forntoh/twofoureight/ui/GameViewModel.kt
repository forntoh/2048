package com.forntoh.twofoureight.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.forntoh.twofoureight.store.PreferenceRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class GameViewModel(
    private val preferenceRepository: PreferenceRepository
) : ViewModel() {

    private val _playTimeInSecs = MutableStateFlow(preferenceRepository.timeElapsed)
    val playTimeInSecs: StateFlow<Long> = _playTimeInSecs.asStateFlow()

    private val _moves = MutableStateFlow(preferenceRepository.moves)
    val moves: StateFlow<Int> = _moves.asStateFlow()

    private val _score = MutableStateFlow(preferenceRepository.score)
    val score: StateFlow<Int> = _score.asStateFlow()

    private val _highScore = MutableStateFlow(preferenceRepository.highScore)
    val highScore: StateFlow<Int> = _highScore.asStateFlow()

    init {
        viewModelScope.launch {
            flow {
                while (true) {
                    emit(Unit)
                    delay(1.seconds)
                }
            }
                .map { preferenceRepository.paused }
                .distinctUntilChanged { _, new -> new }
                .onEach { preferenceRepository.timeElapsed = _playTimeInSecs.updateAndGet { prev -> prev + 1 } }
                .collect()
        }
    }

    fun setMoves(moves: Int) {
        preferenceRepository.moves = _moves.updateAndGet { moves }
    }

    fun setScore(score: Int) {
        preferenceRepository.score = _score.updateAndGet { score }
    }

    fun newGame() {
        setMoves(0)
        setScore(0)
        preferenceRepository.timeElapsed = _playTimeInSecs.updateAndGet { 0 }
    }

}

class GameVieModelFactory(private val context: Context) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = GameViewModel(PreferenceRepository(context)) as T
}