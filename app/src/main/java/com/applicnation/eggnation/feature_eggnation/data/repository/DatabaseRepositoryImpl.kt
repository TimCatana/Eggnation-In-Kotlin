package com.applicnation.eggnation.feature_eggnation.data.repository

import com.applicnation.eggnation.feature_eggnation.data.remote.firebase.Firestore
import com.applicnation.eggnation.feature_eggnation.data.remote.firebase.RealtimeDatabase
import com.applicnation.eggnation.feature_eggnation.domain.modal.AvailablePrize
import com.applicnation.eggnation.feature_eggnation.domain.modal.User
import com.applicnation.eggnation.feature_eggnation.domain.modal.WonPrize
import com.applicnation.eggnation.feature_eggnation.domain.repository.DatabaseRepository
import javax.inject.Inject
import kotlin.collections.ArrayList

class DatabaseRepositoryImpl @Inject constructor(
    private val database: Firestore,
    private val realtimeDatabase: RealtimeDatabase
) : DatabaseRepository {

    /*******************
     **** Firestore ****
     *******************/
    override suspend fun registerUser(userId: String, userInfo: User) {
        database.registerUser(userId, userInfo)
    }

    override suspend fun updateUserEmail(userId: String, newEmail: String) {
        database.updateUserEmail(userId, newEmail)
    }

    override suspend fun updateUserUsername(userId: String, newUsername: String) {
        database.updateUserUsername(userId, newUsername)
    }

    override suspend fun deleteUser() {
        // TODO - use a cloud function to delete user as recommended by firestore docs
    }

    override suspend fun addWonPrizeToUserAccount(userId: String, prizeId: String, prizeTitle: String, prizeDesc: String, prizeTier: String) {
        database.addWonPrizeToUserAccount(userId, prizeId, prizeTitle, prizeDesc, prizeTier)
    }

    override suspend fun getAllWonPrizes(userId: String): java.util.ArrayList<WonPrize> {
        return database.getAllWonPrizes(userId)
    }

    override suspend fun getWonPrizeById(userId: String, prizeId: String): WonPrize? {
        return database.getWonPrizeById(userId, prizeId)
    }

    override suspend fun updateWonPrizeClaimed(userId: String, prizeId: String, prizeClaimed: Boolean) {
        database.updateWonPrizeClaimed(userId, prizeId, prizeClaimed)
    }

    /**********************
     ** RealtimeDatabase **
     **********************/
    override suspend fun incrementGlobalCounter() {
        realtimeDatabase.incrementGlobalCounter()
    }

    override suspend fun getAllAvailablePrizes(): ArrayList<AvailablePrize> {
        return realtimeDatabase.getAllAvailablePrizes()
    }

    override suspend fun getAvailablePrizeByRNG(rng: String): AvailablePrize? {
        return realtimeDatabase.getAvailablePrizeByRNG(rng)
    }
}
