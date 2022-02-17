package com.applicnation.eggnation.feature_eggnation.domain.repository

import com.applicnation.eggnation.feature_eggnation.domain.modal.AvailablePrize
import com.applicnation.eggnation.feature_eggnation.domain.modal.User
import com.applicnation.eggnation.feature_eggnation.domain.modal.WonPrize
import java.util.*

interface DatabaseRepository {
    /**
     * Firestore
     */
    suspend fun registerUser(userId: String, userInfo: User)
    suspend fun updateUserEmail(userId: String, newEmail: String)
    suspend fun  updateUserUsername(userId: String, newUsername: String)
    suspend fun deleteUser()

    suspend fun addWonPrizeToUserAccount(userId: String, prizeId: String, prizeTitle: String, prizeDesc: String, prizeTier: String)
    suspend fun getAllWonPrizes(userId: String): ArrayList<WonPrize>
    suspend fun getWonPrizeById(userId: String, prizeId: String): WonPrize?
    suspend fun updateWonPrizeClaimed(userId: String, prizeId: String, prizeClaimed: Boolean)

    /**
     * Realtime Database
     */
    suspend fun incrementGlobalCounter()

    suspend fun getAllAvailablePrizes(): ArrayList<AvailablePrize>
    suspend fun getAvailablePrizeByRNG(rng: String): AvailablePrize?
}
