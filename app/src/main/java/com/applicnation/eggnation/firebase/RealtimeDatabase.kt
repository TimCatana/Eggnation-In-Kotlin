package com.applicnation.eggnationkotlin.firebase

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

// TODO - check that scopes are working properly by logging the scope
class RealtimeDatabase {
    private val database = FirebaseDatabase.getInstance()
    private val userDelta: Long = -1
    private val globalDelta: Long = 1

//    // TODO - seriously considering holding the user count on local storage...
//    fun registerUser(scope: CoroutineScope, userInfo: User, userId: String) {
//        scope.launch {
//            try {
//
//            } catch (err: Exception) {
//                Log.i("RealtimeDatabase", "Failed to register user to realtime database: ${err.message.toString()}")
//            }
//        }
//    }
//
//    // TODO - seriously considering holding the user count on local storage...
//    fun decrementUserCounter(scope: CoroutineScope, userId: String) {
//        scope.launch {
//            try {
//                database.reference.child("users").child(userId)
//                    .setValue(ServerValue.increment(userDelta)).await()
//            } catch (err: Exception) {
//                Log.i("RealtimeDatabase", "Failed to update user count: ${err.message.toString()}")
//            }
//        }
//    }

    fun incrementGlobalCounter(scope: CoroutineScope) {
        scope.launch {
            try {
                database.reference.child("globalCount").setValue(ServerValue.increment(globalDelta))
                    .await()
            } catch (err: Exception) {
                Log.i(
                    "RealtimeDatabase",
                    "Failed to update global count: ${err.message.toString()}"
                )
            }
        }
    }

    fun checkIfUserWon(scope: CoroutineScope, rng: Int) {
        scope.launch {
            try {
                // TODO - need to check how returning null from database works in kotlin. Test that when I get ther
                val prize = database.reference.child("prizes").get().await()

                prize?.let{
                    // add prize to firestore
                    // delete prize from realtime database
                }
            } catch (err: Exception) {
                Log.i("RealtimeDatabase", "Failed to check if user won: ${err.message.toString()}")
            }
        }
    }

}
