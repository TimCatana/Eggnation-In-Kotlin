package com.applicnation.eggnation.feature_eggnation.data.repository

import com.applicnation.eggnation.feature_eggnation.data.remote.firebase.Firestore
import com.applicnation.eggnation.feature_eggnation.domain.modal.User
import com.applicnation.eggnation.feature_eggnation.domain.repository.FirestoreRepository
import java.util.*
import javax.inject.Inject

// TODO - I think I might need a hilt tag
class FirestoreRepositoryImpl @Inject constructor(
    private val database: Firestore
) : FirestoreRepository {

    override suspend fun registerUser(userId: String, userInfo: User) {
        database.registerUser(userId = userId, userInfo = userInfo)
    }

    override suspend fun updateUserEmail(userId: String, newEmail: String) {
        database.updateUserEmail(userId = userId, newEmail = newEmail)
    }

    override suspend fun updateUserUsername(userId: String, newUserName: String) {
        database.updateUserUsername(userId = userId, newUserName = newUserName)
    }

    override suspend fun updatePrizeClaimed(userId: String, prizeId: String, claimed: Boolean) {
        TODO("Not yet implemented")
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
        database.addWonPrizeToUserAccount(
            userId = userId,
            prizeId = prizeId,
            prizeType = prizeType,
            prizeName = prizeName
        )
    }


}