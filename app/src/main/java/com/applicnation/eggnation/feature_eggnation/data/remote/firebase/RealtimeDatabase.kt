package com.applicnation.eggnation.feature_eggnation.data.remote.firebase

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
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
                Log.d(TAG, "Prize Won! $prize")
            } else {
                Log.d(TAG, "Prize Lost...")
            }
        } catch (e: Exception) {
            Log.d(TAG, "Failed to fetch prize")
        }
    }

    suspend fun getAvailablePrizes() {
        try {
            val prizes = database.reference.child(prizeNode).get().await()

            if (prizes.exists()) {
                Log.d(TAG, "Prizes fetched $prizes")
            } else {
                Log.d(TAG, "Not prizes available")
            }
        } catch (e: Exception) {

        }
    }

}