package com.applicnation.eggnation.feature_eggnation.data.remote.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class Functions {
    private val functions = Firebase.functions

    suspend fun updateUserEmail(email: String) {
        val data = hashMapOf(
            "email" to email
        )

        functions.getHttpsCallable("updateUserEmail").call(data).await()
    }

    suspend fun updateUserPassword(password: String) {
        val data = hashMapOf(
            "password" to password
        )

        functions.getHttpsCallable("updateUserPassword").call(data).await()
    }
}