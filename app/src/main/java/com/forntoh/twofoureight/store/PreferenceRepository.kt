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
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * A simple data repository for in-app settings.
 */
class PreferenceRepository constructor(context: Context) {

    val gson = Gson()

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

    private val _isNightMode: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isNightMode: StateFlow<Boolean> = _isNightMode.asStateFlow()

//----------------------------------------------------------------------------------------------------------------------------------

    var score: Int = 0
        get() = sharedPreferences.getInt(PREFERENCE_SCORE, 0)
        set(value) {
            previousScore = score
            sharedPreferences.edit().putInt(PREFERENCE_SCORE, value).apply()
            field = value
        }

    var previousScore: Int = 0
        get() = sharedPreferences.getInt(PREFERENCE_PREVIOUS_SCORE, 0)
        set(value) {
            sharedPreferences.edit().putInt(PREFERENCE_PREVIOUS_SCORE, value).apply()
            field = value
        }

//----------------------------------------------------------------------------------------------------------------------------------

    var highScore: Int = 0
        get() = sharedPreferences.getInt(PREFERENCE_HIGH_SCORE, 0)
        set(value) {
            sharedPreferences.edit().putInt(PREFERENCE_HIGH_SCORE, value).apply()
            field = value
        }

//----------------------------------------------------------------------------------------------------------------------------------

    var moves: Int = 0
        get() = sharedPreferences.getInt(PREFERENCE_MOVES, 0)
        set(value) {
            sharedPreferences.edit().putInt(PREFERENCE_MOVES, value).apply()
            field = value
        }

//----------------------------------------------------------------------------------------------------------------------------------

    var timeElapsed: Long = 0L
        get() = sharedPreferences.getLong(PREFERENCE_TIME_ELAPSED, 0L)
        set(value) {
            sharedPreferences.edit().putLong(PREFERENCE_TIME_ELAPSED, value).apply()
            field = value
        }

//----------------------------------------------------------------------------------------------------------------------------------

    var boardState: List<IntArray> = emptyList()
        get() = gson.fromJson(
            sharedPreferences.getString(PREFERENCE_BOARD_STATE, "[]"),
            object : TypeToken<MutableList<IntArray>>() {}.type
        )
        set(value) {
            previousBoardState = boardState
            sharedPreferences.edit().putString(PREFERENCE_BOARD_STATE, gson.toJson(value)).apply()
            field = value
        }

    var previousBoardState: List<IntArray> = emptyList()
        get() = gson.fromJson(
            sharedPreferences.getString(PREFERENCE_BOARD_PREVIOUS_STATE, "[]"),
            object : TypeToken<MutableList<IntArray>>() {}.type
        )
        set(value) {
            sharedPreferences.edit().putString(PREFERENCE_BOARD_PREVIOUS_STATE, gson.toJson(value)).apply()
            field = value
        }

//----------------------------------------------------------------------------------------------------------------------------------

    var paused: Boolean = true
        get() = sharedPreferences.getBoolean(PREFERENCE_PAUSED, true)
        set(value) {
            sharedPreferences.edit().putBoolean(PREFERENCE_PAUSED, value).apply()
            field = value
        }

//----------------------------------------------------------------------------------------------------------------------------------

    private val preferenceChangedListener =
        SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            when (key) {
                PREFERENCE_NIGHT_MODE -> {
                    _isNightMode.value = isDarkTheme
                }
            }
        }

    init {
        _isNightMode.value = isDarkTheme
        sharedPreferences.registerOnSharedPreferenceChangeListener(preferenceChangedListener)
    }

    companion object {
        private const val PREFERENCE_BOARD_STATE = "boardState"
        private const val PREFERENCE_BOARD_PREVIOUS_STATE = "boardPrevState"
        private const val PREFERENCE_TIME_ELAPSED = "timeElapsed"
        private const val PREFERENCE_PAUSED = "paused"
        private const val PREFERENCE_SCORE = "score"
        private const val PREFERENCE_PREVIOUS_SCORE = "scorePrev"
        private const val PREFERENCE_HIGH_SCORE = "highScore"
        private const val PREFERENCE_MOVES = "moves"
        private const val PREFERENCE_NIGHT_MODE = "preference_night_mode"
        private const val PREFERENCE_NIGHT_MODE_SYSTEM = "preference_night_mode_system"
        private const val PREFERENCE_NIGHT_MODE_DEF_VAL = AppCompatDelegate.MODE_NIGHT_NO
    }
}