package com.applicnation.eggnation.feature_eggnation.data.remote.firebase

import android.util.Log
import com.applicnation.eggnation.feature_eggnation.domain.modal.AvailablePrize
import com.applicnation.eggnation.util.Constants
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import kotlinx.coroutines.tasks.await
import timber.log.Timber

/**
 * @note add note here mentioning how this section of coe is easily portable to a new database should I ever choose to do so
 */
class RealtimeDatabase {
    private val database = FirebaseDatabase.getInstance()
    private val globalDelta: Long = 1

    /**
     * Increments the global counter in the realtime database
     * @exception Exception All exceptions thrown from this catch block are UNEXPECTED
     * @note This function can silently fail, the user does not need to know anything about it.
     */
    suspend fun incrementGlobalCounter() {
        try {
            database
                .reference
                .child(Constants.GLOBAL_COUNTER_NODE)
                .setValue(ServerValue.increment(globalDelta))
                .await()
        } catch (e: Exception) {
            Timber.e("Failed to increment global counter in realtime database: An unexpected error occurred --> $e")
        }
    }

    /**
     * @importantNote Firestore would be more convenient, see the note on getAvailablePrizeByRNG for why I am using realtime database (it is a financial decision)
     * Gets the list of all currently availablePrizes
     * @exception Exception All exceptions thrown from this catch block are UNEXPECTED
     */
    // TODO - probably return a flow
    // TODO - there are probably more specific errors for me to check
    suspend fun getAvailablePrizes(): ArrayList<AvailablePrize> {
        val prizeList = ArrayList<AvailablePrize>()

        try {
            val prizesSnapshot = database.reference.child(Constants.PRIZE_NODE).get().await()

            if (prizesSnapshot.exists()) {
                prizesSnapshot.children.forEach { singlePrizeSnapshot ->
                    val singlePrize = AvailablePrize()

                    singlePrizeSnapshot.children.forEach {
                        // TODO - maybe move this to a function?
                        when (it.key.toString()) {
                            "prizeId" -> {
                                singlePrize.prizeId = it.value.toString()
                            }
                            "prizeTitle" -> {
                                singlePrize.prizeTitle = it.value.toString()
                            }
                            "prizeDesc" -> {
                                singlePrize.prizeDesc = it.value.toString()
                            }
                            "prizeType" -> {
                                singlePrize.prizeType = it.value.toString()
                            }
                            "prizeTier" -> {
                                singlePrize.prizeTier = it.value.toString()
                            }
                        }
                    }

                    prizeList.add(singlePrize)
                }

                return prizeList
            } else {
                // TODO - return empty prize list, no prizes available
                return prizeList
            }

        } catch (e: Exception) {
            Timber.e("Failed to fetch available prizes from realtime database: An unexpected error occurred --> $e")
            throw Exception()
        }
    }


    /**
     * @importantNote Firestore would be more convenient, but this operation can get really expensive very fast on firestore.
     * Gets a specific prize at node $rng.
     * This is the main game logic, A random number is generated and passed as param rng, then this function query's the
     * node at rng. If the node exists, then the player won. If the node does not exist, then the player lost.
     * @param rng the node name to query
     * @exception Exception All exceptions thrown from this catch block are UNEXPECTED
     */
    // TODO - returning mull can mean either that the fetch attempt failed or that the user lost this round, either way, it means the user lost so just display the lost animation
    suspend fun getAvailablePrizeByRNG(rng: String): AvailablePrize {
        val prize = AvailablePrize()

        try {
            val prizeSnapshot =
                database.reference.child(Constants.PRIZE_NODE).child(rng).get().await()

            if (prizeSnapshot.exists()) {
                prizeSnapshot.children.forEach() {
                    when (it.key.toString()) {
                        "prizeId" -> {
                            prize.prizeId = it.value.toString()
                        }
                        "prizeTitle" -> {
                            prize.prizeTitle = it.value.toString()
                        }
                        "prizeDesc" -> {
                            prize.prizeDesc = it.value.toString()
                        }
                        "prizeType" -> {
                            prize.prizeType = it.value.toString()
                        }
                        "prizeTier" -> {
                            prize.prizeTier = it.value.toString()
                        }
                    }
                }

                return prize
            } else {
                return prize
            }
        } catch (e: Exception) {
            Timber.e("Failed to fetch available prizes with rng number $rng from realtime database: An unexpected error occurred --> $e")
            throw Exception()
        }
    }

}
