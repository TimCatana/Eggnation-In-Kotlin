package com.applicnation.eggnation.feature_eggnation.domain.repository

import com.applicnation.eggnation.feature_eggnation.domain.modal.User
import java.util.*

interface FirestoreRepository {

    suspend fun registerUser(userId: String, userInfo: User)
    suspend fun updateUserEmail(userId: String, newEmail: String)
    suspend fun updateUserUsername(userId: String, newUserName: String)
    suspend fun updatePrizeClaimed(userId: String, prizeId: String, claimed: Boolean)
    suspend fun addWonPrizeToUserAccount(
        userId: String,
        timeWon: Date = Date(),
        claimed: Boolean = false,
        prizeId: String,
        prizeType: String,
        prizeName: String,
    )


//    fun getCurrentUserId(): String
}
