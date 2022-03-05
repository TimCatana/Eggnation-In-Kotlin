package com.applicnation.eggnation.feature_eggnation.data.remote.firebase

import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class Functions {
    private val functions = Firebase.functions

    suspend fun updateUserUsername(username: String){
        val data = hashMapOf(
            "username" to username
        )

        functions.getHttpsCallable("updateUserUsername").call(data).await()
    }

}