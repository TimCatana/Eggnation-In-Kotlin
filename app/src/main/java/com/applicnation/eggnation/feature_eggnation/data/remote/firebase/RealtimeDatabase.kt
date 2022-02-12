package com.applicnation.eggnation.feature_eggnation.data.remote.firebase

import android.util.Log
import com.applicnation.eggnation.feature_eggnation.domain.modal.Prize
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RealtimeDatabase {
    private val TAG = "realtimeDatabaseClass"
    private val prizeNode = "availablePrizes"
    private val globalCounterNode = "globalCount"
    private val users = "usersNode"

    private val globalDelta: Long = 1

    private val database = FirebaseDatabase.getInstance()

    suspend fun registerUser(userId: String, username: String) {
        try {
            database.reference.child(users).child(userId).child("username").setValue(username)
                .await()
        } catch (err: Exception) {
            Log.i(
                TAG,
                "Failed to register user to realtime database: ${err.message.toString()}"
            )
        }
    }

    suspend fun incrementGlobalCounter() {
        try {
            database.reference.child(globalCounterNode)
                .setValue(ServerValue.increment(globalDelta))
                .await()
        } catch (err: Exception) {
            Log.i(
                TAG,
                "Failed to update global count: ${err.message.toString()}"
            )
        }
    }

    suspend fun getAvailablePrizeByRNG(rng: String) {
        try {
            val prize = database.reference.child(prizeNode).child(rng).get().await()

            if (prize.exists()) {
                Log.d(TAG, "Prize Won! $prize and ${prize.children}")
            } else {
                Log.d(TAG, "Prize Lost...")
            }
        } catch (e: Exception) {
            Log.d(TAG, "Failed to fetch prize")
        }
    }

    /**
     * Firestore would be more convenient, but this option is free whereas firestore will get expensive really fast.
     */
    // TODO - probably return a flow
    suspend fun getAvailablePrizes(): ArrayList<Prize> {
        val prizeList = ArrayList<Prize>()
        try {
            val prizesSnapshot = database.reference.child(prizeNode).get().await()

            if (prizesSnapshot.exists()) {
                prizesSnapshot.children.forEach() { prize ->
                    val singlePrize = Prize()

                    prize.children.forEach() {
                        when (it.key.toString()) {
                            "prizeId" -> {
                                singlePrize.prizeId = it.value.toString()
                            }
                            "prizeType" -> {
                                singlePrize.prizeType = it.value.toString()
                            }
                            "prizeTier" -> {
                                singlePrize.prizeTier = it.value.toString()
                            }
                            "prizeTitle" -> {
                                singlePrize.prizeName = it.value.toString()
                            }
                            "prizeDesc" -> {
                                singlePrize.prizeDesc = it.value.toString()
                            }
                        }
                    }

                    prizeList.add(singlePrize)

                }

                Log.d(TAG, "Prizes available:  $prizeList")

            } else {
                Log.d(TAG, "Not prizes available")
            }

            return prizeList
        } catch (e: Exception) {
            Log.d(TAG, "Failed to read prizes from database")
            // TODO - need to return something if failed. prizeList will be empty, I'm not sure if this will fail
            // TODO - need some way to send an error.
            return prizeList
        }

    }

}