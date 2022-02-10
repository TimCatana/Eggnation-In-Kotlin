package com.applicnation.eggnation.feature_eggnation.domain.repository

import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {
    fun getSelectedSkin(): Flow<Int>
    fun getTapCount(): Flow<Int>
    fun getReceivesNotifications(): Flow<Boolean>
    fun getLastResetTime(): Flow<Long>
    suspend fun updateSelectedSkin(skin: Int)
    suspend fun updateTapCount(tapCount: Int)
    suspend fun updateReceiveNotifications(receivesNotifications: Boolean)
    suspend fun updateLastResetTime(resetTime: Long)
    suspend fun decrementTapCounter()
}