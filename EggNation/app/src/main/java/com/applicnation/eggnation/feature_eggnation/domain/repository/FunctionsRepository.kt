package com.applicnation.eggnation.feature_eggnation.domain.repository

interface FunctionsRepository {
    suspend fun updateUserEmail(email: String)
    suspend fun updateUserUsername(username: String)
}