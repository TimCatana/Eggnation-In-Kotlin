package com.applicnation.eggnation.feature_eggnation.domain.use_case.prize_use_case.won_prize_use_case

import com.applicnation.eggnation.feature_eggnation.domain.repository.DatabaseRepository
import com.applicnation.eggnation.util.Constants
import com.google.firebase.firestore.FirebaseFirestoreException
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class WonPrizeUpdatePrizeClaimedUC @Inject constructor(
    private val repository: DatabaseRepository
) {

    // TODO - clean up documentation
    /**
     * Updates the prize claimed boolean for the prize in the user's account.
     * This should only ever update the prize claimed field to "true"
     * @param userId The user's user ID (usually always from Firebase auth). user documents are named after the user's uid
     * @param prizeId The prize's prize ID (I will always have gotten a list of all prizes before I get a specific prize. Each prize
     *                document has the prizeId contained inside it, and that is where I get the ID from). prize documents are names
     *                after the prize's id
     * @param prizeClaimed This should always be passed in as true since a prize can never be "unclaimed"
     * @exception FirebaseFirestoreException This is the only exception Firestore can throw as of now (according to documentation)
     * @exception Exception All exceptions thrown from this catch block are UNEXPECTED
     * // TODO - there may also be other specific exceptions thrown, but I think I only need to tell the user that it failed to fetch
     * // TODO - the specific error catch blocks are used to make debugging easier when there is a problem
     */
    suspend operator fun invoke(userId: String, prizeId: String, prizeClaimed: Boolean) {

        // TODO - propogate success or error to UI

        try {
            return repository.updateWonPrizeClaimed(userId, prizeId, prizeClaimed)
        } catch (e: FirebaseFirestoreException) {
            Timber.e("Failed to add prize to firestore: An unexpected FIRESTORE error occurred --> $e")
        } catch (e: Exception) {
            Timber.e("Failed to update ${Constants.PRIZE_CLAIMED_FIELD} field for prize $prizeId at user's account in firestore : An unexpected error occurred --> $e")
        }
    }
}
