/*
 * Copyright (c) 2020, Forntoh Thomas (thomasforntoh@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.forntoh.twofoureight.store

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/**
 * A simple data repository for in-app settings.
 */
class PreferenceRepository constructor(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(context.packageName + ".DB", Context.MODE_PRIVATE)

    private val systemUiMode: Int
        get() = when (Resources.getSystem()?.configuration!!.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> AppCompatDelegate.MODE_NIGHT_YES
            else -> AppCompatDelegate.MODE_NIGHT_NO
        }

    private val nightMode: Int
        get() = if (useSystemUiMode) systemUiMode else sharedPreferences.getInt(
            PREFERENCE_NIGHT_MODE,
            PREFERENCE_NIGHT_MODE_DEF_VAL
        )

    var useSystemUiMode: Boolean = false
        get() = sharedPreferences.getBoolean(PREFERENCE_NIGHT_MODE_SYSTEM, false)
        set(value) {
            sharedPreferences.edit().putBoolean(PREFERENCE_NIGHT_MODE_SYSTEM, value).apply()
            field = value
            isDarkTheme = if (value) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                systemUiMode == AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.setDefaultNightMode(nightMode)
                nightMode == AppCompatDelegate.MODE_NIGHT_YES
            }
        }

    private val _nightModeLive: MutableLiveData<Int> = MutableLiveData()
    val nightModeLive: LiveData<Int>
        get() = _nightModeLive

//----------------------------------------------------------------------------------------------------------------------------------

    var isDarkTheme: Boolean = false
        get() = nightMode == AppCompatDelegate.MODE_NIGHT_YES
        set(value) {
            sharedPreferences.edit().putInt(
                PREFERENCE_NIGHT_MODE,
                if (useSystemUiMode) systemUiMode else {
                    if (value)
                        AppCompatDelegate.MODE_NIGHT_YES
                    else
                        AppCompatDelegate.MODE_NIGHT_NO
                }
            ).apply()
            field = value
        }

    private val _isDarkThemeLive: MutableLiveData<Boolean> = MutableLiveData()
    val isDarkThemeLive: LiveData<Boolean>
        get() = _isDarkThemeLive

//----------------------------------------------------------------------------------------------------------------------------------

    var score: Int = 0
        get() = sharedPreferences.getInt(PREFERENCE_SCORE, 0)
        set(value) {
            if (highScore < value) highScore = value
            sharedPreferences.edit().putInt(PREFERENCE_SCORE, value).apply()
            field = value
        }

    private val _scoreLive = MutableLiveData<Int>()
    val scoreLive: LiveData<Int>
        get() = _scoreLive

//----------------------------------------------------------------------------------------------------------------------------------

    var highScore: Int = 0
        get() = sharedPreferences.getInt(PREFERENCE_HIGH_SCORE, 0)
        private set(value) {
            sharedPreferences.edit().putInt(PREFERENCE_HIGH_SCORE, value).apply()
            field = value
        }

    private val _highScoreLive = MutableLiveData<Int>()
    val highScoreLive: LiveData<Int>
        get() = _highScoreLive

//----------------------------------------------------------------------------------------------------------------------------------

    var moves: Int = 0
        get() = sharedPreferences.getInt(PREFERENCE_MOVES, 0)
        set(value) {
            sharedPreferences.edit().putInt(PREFERENCE_MOVES, value).apply()
            field = value
        }

    private val _movesLive = MutableLiveData<Int>()
    val movesLive: LiveData<Int>
        get() = _movesLive

//----------------------------------------------------------------------------------------------------------------------------------

    var timeElapsed: Long = 0L
        get() = sharedPreferences.getLong(PREFERENCE_TIME_ELAPSED, 0L)
        set(value) {
            sharedPreferences.edit().putLong(PREFERENCE_TIME_ELAPSED, value).apply()
            field = value
        }

    private val _timeElapsedLive = MutableLiveData<Long>()
    val timeElapsedLive: LiveData<Long>
        get() = _timeElapsedLive

//----------------------------------------------------------------------------------------------------------------------------------

    private val preferenceChangedListener =
        SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            when (key) {
                PREFERENCE_NIGHT_MODE -> {
                    _nightModeLive.value = nightMode
                    _isDarkThemeLive.value = isDarkTheme
                }
                PREFERENCE_SCORE -> {
                    _scoreLive.value = score
                }
                PREFERENCE_HIGH_SCORE -> {
                    _highScoreLive.value = highScore
                }
                PREFERENCE_MOVES -> {
                    _movesLive.value = moves
                }
                PREFERENCE_TIME_ELAPSED -> {
                    _timeElapsedLive.value = timeElapsed
                }
            }
        }

    init {
        _nightModeLive.value = nightMode
        _isDarkThemeLive.value = isDarkTheme
        _highScoreLive.value = highScore
        _movesLive.value = moves
        _scoreLive.value = score
        _timeElapsedLive.value = timeElapsed
        sharedPreferences.registerOnSharedPreferenceChangeListener(preferenceChangedListener)
    }

    companion object {
        private const val PREFERENCE_TIME_ELAPSED = "timeElapsed"
        private const val PREFERENCE_SCORE = "score"
        private const val PREFERENCE_HIGH_SCORE = "highScore"
        private const val PREFERENCE_MOVES = "moves"
        private const val PREFERENCE_NIGHT_MODE = "preference_night_mode"
        private const val PREFERENCE_NIGHT_MODE_SYSTEM = "preference_night_mode_system"
        private const val PREFERENCE_NIGHT_MODE_DEF_VAL = AppCompatDelegate.MODE_NIGHT_NO
    }
}