package com.applicnation.eggnation.feature_eggnation.data.remote.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class Functions {
    private val functions = Firebase.functions

    suspend fun updateUserEmail(email: String): Task<String> {
        val data = hashMapOf(
            "email" to email
        )

        return functions.getHttpsCallable("updateUserEmail").call(data).continueWith { task ->
            // This continuation runs on either success or failure, but if the task
            // has failed then result will throw an Exception which will be
            // propagated down.
            val result = task.result?.data as String
            result
        }
    }
}