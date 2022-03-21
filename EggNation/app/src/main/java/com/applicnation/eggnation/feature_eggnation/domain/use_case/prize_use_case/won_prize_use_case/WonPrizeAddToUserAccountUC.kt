package com.applicnation.eggnation.feature_eggnation.domain.use_case.prize_use_case.won_prize_use_case

import com.applicnation.eggnation.feature_eggnation.domain.repository.DatabaseRepository
import com.google.firebase.firestore.FirebaseFirestoreException
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class WonPrizeAddToUserAccountUC @Inject constructor(
    private val repository: DatabaseRepository
) {
    /**
     * Adds a prize to the user's account
     * @param userId The uid of the user used to add the prize to the correct account in fireStore
     * @param prizeId The unique id of the prize won
     * @param prizeTitle The title of the prize
     * @param prizeDesc The description of the prize
     * @param prizeTier The tier of the prize
     * @note prizeDateWon in prize object will default to current time
     * @note prizeClaimed in prize object will default to false
     * @exception FirebaseFirestoreException This is the only exception Firestore can throw as of now (according to documentation)
     * @exception Exception All exceptions thrown from this catch block are UNEXPECTED
     * @return true - successfully added to database, false = failed to add to database
     */
    suspend operator fun invoke(
        userId: String,
        prizeId: String,
        prizeTitle: String,
        prizeDesc: String,
        prizeType: String,
        prizeTier: String
    ): Boolean {
        try {
            repository.addWonPrizeToUserAccount(
                userId = userId,
                prizeId = prizeId,
                prizeTitle = prizeTitle,
                prizeDesc = prizeDesc,
                prizeType = prizeType,
                prizeTier = prizeTier
            )
            return true
        } catch (e: FirebaseFirestoreException) {
            Timber.e("FIRESTORE: Failed to add won prize to fireStore: An unexpected FIRESTORE error occurred --> $e")
            return false
        } catch (e: Exception) {
            Timber.e("FIRESTORE: Failed to add won prize to fireStore: An unexpected error occurred --> $e")
            return false
        }
    }
}
