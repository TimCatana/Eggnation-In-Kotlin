package com.applicnation.eggnation.feature_eggnation.domain.repository

import com.applicnation.eggnation.feature_eggnation.domain.modal.Prize
import com.applicnation.eggnation.feature_eggnation.domain.modal.User
import java.util.*

interface DatabaseRepository {

    /**
     * Firestore
     */
    suspend fun registerUser(userId: String, userInfo: User)
    suspend fun updateUserEmail(userId: String, newEmail: String)
    suspend fun updateUserUsername(userId: String, newUserName: String)
    suspend fun updatePrizeClaimed(userId: String, prizeId: String, claimed: Boolean)
    suspend fun getWonPrizes(userId: String)
    suspend fun addWonPrizeToUserAccount(
        userId: String,
        timeWon: Date = Date(),
        claimed: Boolean = false,
        prizeId: String,
        prizeType: String,
        prizeName: String,
    )

    /**
     * Realtime Database
     */
    suspend fun getAvailablePrizes(): ArrayList<Prize>
    suspend fun getAvailablePrizeByRNG(rng: String): Prize
    suspend fun incrementGlobalCounter() // should increment on the server side
//    suspend fun incrementUserCount(userId: String) NOTE - this will only be used if I decide to add count increments on the server side, which I doubt I will do


//    fun getCurrentUserId(): String
}
