package com.applicnation.eggnation.feature_eggnation.domain.repository

interface FunctionsRepository {
    suspend fun updateUserEmail(email: String)
    suspend fun updateUserUsername(username: String)
    suspend fun sendMeClaimPrizeEmail(
        prizeId: String,
        email: String,
        country: String,
        region: String,
        address: String
    )
}