package com.applicnation.eggnation.feature_eggnation.domain.use_case.individual_use_cases.prize_use_case.won_prize_use_case

import com.applicnation.eggnation.feature_eggnation.domain.modal.WonPrize
import com.applicnation.eggnation.feature_eggnation.domain.repository.DatabaseRepository
import com.google.firebase.firestore.FirebaseFirestoreException
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class WonPrizeGetByIdUC @Inject constructor(
    private val repository: DatabaseRepository
) {
    // TODO - clean up documentation

    /**
     * Gets a specific won prize for the user's account by it's prizeId
     * @param userId The user's user ID (usually always from Firebase auth). user documents are named after the user's uid
     * @param prizeId The prize's prize ID (I will always have gotten a list of all prizes before I get a specific prize. Each prize
     *                document has the prizeId contained inside it, and that is where I get the ID from). prize documents are names
     *                after the prize's id
     * @exception FirebaseFirestoreException This is the only exception Firestore can throw as of now (according to documentation)
     * @exception Exception All exceptions thrown from this catch block are UNEXPECTED
     * // TODO - there may also be other specific exceptions thrown, but I think I only need to tell the user that it failed to fetch
     * // TODO - the specific error catch blocks are used to make debugging easier when there is a problem
     */
    suspend operator fun invoke(userId: String, prizeId: String): WonPrize {

        // TODO - propogatet success and errors

        try {
            val prize = repository.getWonPrizeById(userId, prizeId)

            if(prize != null) {
                return prize
            } else {
                // probably throw a custom "prize not found" exception or somethng
                throw Exception()
            }
        } catch (e: FirebaseFirestoreException) {
            Timber.e("Failed to add prize to firestore: An unexpected FIRESTORE error occurred --> $e")
            throw Exception()
        } catch (e: Exception) {
            Timber.e("Failed to get prize $prizeId from user's account from firestore: An unexpected error occurred --> $e")
            throw Exception()
        }
    }
}
