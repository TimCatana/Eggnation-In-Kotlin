package com.applicnation.eggnation.feature_eggnation.data.repository

import com.applicnation.eggnation.feature_eggnation.data.remote.firebase.Firestore
import com.applicnation.eggnation.feature_eggnation.data.remote.firebase.RealtimeDatabase
import com.applicnation.eggnation.feature_eggnation.domain.modal.Prize
import com.applicnation.eggnation.feature_eggnation.domain.modal.User
import com.applicnation.eggnation.feature_eggnation.domain.repository.DatabaseRepository
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class DatabaseRepositoryImpl @Inject constructor(
    private val firestore: Firestore,
    private val realtimeDatabase: RealtimeDatabase
) : DatabaseRepository {

    /**
     * Firestore
     */
    override suspend fun registerUser(userId: String, userInfo: User) {
        firestore.registerUser(userId = userId, userInfo = userInfo)
        // TODO - register user in realtime database as well (to make future decisions easier)
    }

    override suspend fun updateUserEmail(userId: String, newEmail: String) {
        firestore.updateUserEmail(userId = userId, newEmail = newEmail)
    }

    override suspend fun updateUserUsername(userId: String, newUserName: String) {
        firestore.updateUserUsername(userId = userId, newUserName = newUserName)
        // TODO - update in realtime database as well
    }

    override suspend fun updatePrizeClaimed(userId: String, prizeId: String, claimed: Boolean) {
        TODO("Not yet implemented")
    }

    override suspend fun getWonPrizes(userId: String) {
        firestore.getWonPrizes(userId)
    }

    override suspend fun addWonPrizeToUserAccount(
        userId: String,
        timeWon: Date,
        claimed: Boolean,
        prizeId: String,
        prizeType: String,
        prizeName: String
    ) {
        // TODO - need to add all parameters in bottom function
//        firestore.addWonPrizeToUserAccount(
//            userId = userId,
//            prizeId = prizeId,
//            prizeType = prizeType,
//            prizeName = prizeName
//        )
    }

    /**
     * RealtimeDatabase
     */
    override suspend fun getAvailablePrizes(): ArrayList<Prize> {
        return realtimeDatabase.getAvailablePrizes()
    }

    override suspend fun getAvailablePrizeByRNG(rng: String): Prize {
        return realtimeDatabase.getAvailablePrizeByRNG(rng)
    }

    override suspend fun incrementGlobalCounter() {
        realtimeDatabase.incrementGlobalCounter()
    }

}