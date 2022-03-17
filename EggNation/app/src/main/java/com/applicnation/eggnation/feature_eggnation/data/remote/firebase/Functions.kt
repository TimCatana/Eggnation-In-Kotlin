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

    suspend fun updateUserUsername(username: String) {
        val data = hashMapOf(
            "username" to username
        )

        functions.getHttpsCallable("updateUserUsername").call(data).await()
    }

    suspend fun sendMeClaimPrizeEmail(
        prizeId: String,
        email: String,
        country: String,
        region: String,
        address: String
    ) {
        val data = hashMapOf(
            "prizeId" to prizeId,
            "email" to email,
            "country" to country,
            "region" to region,
            "address" to address
        )

        functions.getHttpsCallable("sendMeEmail").call(data).await()
    }
}