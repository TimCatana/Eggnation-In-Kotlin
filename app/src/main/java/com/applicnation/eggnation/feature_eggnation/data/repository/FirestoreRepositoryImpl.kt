package com.applicnation.eggnation.feature_eggnation.data.repository

import com.applicnation.eggnation.feature_eggnation.domain.modal.User
import com.applicnation.eggnation.feature_eggnation.domain.repository.FirestoreRepository
import javax.inject.Inject

class FirestoreRepositoryImpl @Inject constructor(
    private val database: FirebaseRe
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

    override suspend fun addWonPrizeToUserAccount(
        userId: String,
        prizeId: String,
        prizeType: String,
        prizeName: String
    ) {
        database.addWonPrizeToUserAccount(
            userId = userId,
            prizeId = prizeId,
            prizeType = prizeType,
            prizeName = prizeName
        )
    }


}