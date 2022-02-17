package com.applicnation.eggnation.feature_eggnation.domain.use_case.prize_use_case.available_prize_use_case

import com.applicnation.eggnation.feature_eggnation.domain.modal.AvailablePrize
import com.applicnation.eggnation.feature_eggnation.domain.repository.DatabaseRepository
import timber.log.Timber
import javax.inject.Inject

class AvailablePrizeGetAllUC @Inject constructor(
    private val repository: DatabaseRepository
) {

    /**
     * @importantNote Firestore would be more convenient, see the note on getAvailablePrizeByRNG for why I am using realtime database (it is a financial decision)
     * Gets the list of all currently availablePrizes
     * @exception Exception All exceptions thrown from this catch block are UNEXPECTED
     */
    suspend operator fun invoke(): ArrayList<AvailablePrize> {

        // TODO - propogate success or error


        try{
            return repository.getAllAvailablePrizes()
        } catch (e: Exception) {
            Timber.e("Failed to fetch available prizes from realtime database: An unexpected error occurred --> $e")
            throw Exception()
        }
    }
}
