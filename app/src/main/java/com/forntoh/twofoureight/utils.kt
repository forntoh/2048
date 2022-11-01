package com.forntoh.twofoureight

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

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