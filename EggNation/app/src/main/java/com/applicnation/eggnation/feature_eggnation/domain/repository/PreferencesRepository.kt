package com.applicnation.eggnation.feature_eggnation.domain.repository

import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {
    fun getTapCount(): Flow<Int>
    fun getLastResetTime(): Flow<Long>

    suspend fun updateTapCount(tapCount: Int)
    suspend fun updateLastResetTime(resetTime: Long)
    suspend fun decrementTapCounter()
}