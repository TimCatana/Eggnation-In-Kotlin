package com.applicnation.eggnation.feature_eggnation.data.remote.firebase

import com.applicnation.eggnation.feature_eggnation.domain.modal.AvailablePrize
import com.applicnation.eggnation.util.Constants
import com.google.firebase.database.DataSnapshot
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
     * Increments the global counter in the realtime database.
     * @note Errors are caught and dealt with in use-case
     */
    suspend fun incrementGlobalCounter() {
        database
            .reference
            .child(Constants.GLOBAL_COUNTER_NODE)
            .setValue(ServerValue.increment(globalDelta))
            .await()
    }

    /**
     * @importantNote Firestore would be more convenient, see the note on getAvailablePrizeByRNG for why I am using realtime database (it is a financial decision)
     * Gets the list of all currently availablePrizes
     * @exception Exception All exceptions thrown from this catch block are UNEXPECTED
     * @helperFunction convertToAvailablePrizeObject()
     * @return ArrayList of AvailablePrizes OR empty arrayList
     */
    // TODO - probably return a flow
    // TODO - break this up into smaller functions
    suspend fun getAllAvailablePrizes(): ArrayList<AvailablePrize> {
        val prizeList = ArrayList<AvailablePrize>()
        val prizesSnapshot = database
            .reference
            .child(Constants.PRIZE_NODE)
            .get()
            .await()

        if (prizesSnapshot.exists()) {
            prizesSnapshot.children.forEach { singlePrizeSnapshot ->
                val singlePrize = convertToAvailablePrizeObject(singlePrizeSnapshot)
                prizeList.add(singlePrize)
            }
        }

        return prizeList
    }

    /**
     * @importantNote Firestore would be more convenient, but this operation can get really expensive very fast on firestore.
     * Gets a specific prize at node $rng.
     * This is the main game logic, A random number is generated and passed as param rng, then this function query's the
     * node at rng. If the node exists, then the player won. If the node does not exist, then the player lost.
     * @param rng the node name to query
     * @helperFunction convertToAvailablePrizeObject()
     */
    suspend fun getAvailablePrizeByRNG(rng: String): AvailablePrize? {
        val prizeSnapshot = database
            .reference
            .child(Constants.PRIZE_NODE)
            .child(rng)
            .get()
            .await()

        if (prizeSnapshot.exists()) {
            return convertToAvailablePrizeObject(prizeSnapshot)
        } else {
            return null
        }
    }

    /**
     * Helper function for getAllAvailablePrizes
     * @param snapShot data snapshot of availablePrizes in Database
     * @return A single AvailablePrize
     */
    private fun convertToAvailablePrizeObject(snapShot: DataSnapshot): AvailablePrize {
        val prize = AvailablePrize()

        snapShot.children.forEach {
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
    }

}
