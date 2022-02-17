package com.applicnation.eggnation.feature_eggnation.domain.use_case.prize_use_case.available_prize_use_case

import com.applicnation.eggnation.feature_eggnation.domain.modal.AvailablePrize
import com.applicnation.eggnation.feature_eggnation.domain.repository.DatabaseRepository
import timber.log.Timber
import javax.inject.Inject

class AvailablePrizeGetByRNGUC @Inject constructor(
    private val repository: DatabaseRepository
) {


    /**
     * @importantNote Firestore would be more convenient, but this operation can get really expensive very fast on firestore.
     * Gets a specific prize at node $rng.
     * This is the main game logic, A random number is generated and passed as param rng, then this function query's the
     * node at rng. If the node exists, then the player won. If the node does not exist, then the player lost.
     * @param rng the node name to query
     * @exception Exception All exceptions thrown from this catch block are UNEXPECTED
     */
    suspend operator fun invoke(rng: String): AvailablePrize {

        // TODO - propogate success or error


        try {
            val prize = repository.getAvailablePrizeByRNG(rng)

            if (prize == null) {
                // User lost
                // TODO - maybe throw userLostException? but that's not an optimal way to do what I want to do. Maybe I can just pass null to viewmodel and do final check there
                throw Exception()
            } else {
                return prize
                // User won
            }
        } catch (e: Exception) {
            Timber.e("Failed to fetch available prizes with rng number $rng from realtime database: An unexpected error occurred --> $e")
            throw Exception()
        }
    }
}
