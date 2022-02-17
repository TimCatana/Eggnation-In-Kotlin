package com.applicnation.eggnation.feature_eggnation.domain.use_case.prize_use_case.won_prize_use_case

import com.applicnation.eggnation.feature_eggnation.domain.modal.WonPrize
import com.applicnation.eggnation.feature_eggnation.domain.repository.DatabaseRepository
import com.google.firebase.firestore.FirebaseFirestoreException
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class WonPrizeGetAllUC @Inject constructor(
    private val repository: DatabaseRepository
) {
    // TODO - clean up documentation

    /**
     * Gets the list of all won prizes for the user's account
     * @param userId The user's user ID (usually always from Firebase auth). documents are named after the user's uid
     * @exception FirebaseFirestoreException This is the only exception Firestore can throw as of now (according to documentation)
     * @exception Exception All exceptions thrown from this catch block are UNEXPECTED
     * // TODO - there may also be other specific exceptions thrown, but I think I only need to tell the user that it failed to fetch
     * // TODO - the specific error catch blocks are used to make debugging easier when there is a problem
     * @return ArrayList<WonPrize> The list of won prizes
     * // TODO maybe paginate prizes? But probably not cause no one should have like 100 prizes at any given moment in time
     */
    suspend operator fun invoke(userId: String): ArrayList<WonPrize> {

        try {
            // TODO - add a val to get the results, then return so that exceptions have time to be caught
            return repository.getAllWonPrizes(userId)
        } catch (e: FirebaseFirestoreException) {
            // TODO - maybe throw an exception?
            // TODO - propogate error via a boolean. If failed, retry this function. if failed again... rip
            Timber.e("Failed to add prize to firestore: An unexpected FIRESTORE error occurred --> $e")
            throw Exception()
        } catch (e: Exception) {
            // TODO - propogate error via a boolean. If failed, retry this function. if failed again... rip
            Timber.e("Failed to add prize to firestore: An unexpected error occurred --> $e")
            throw Exception()
        }
    }
}
