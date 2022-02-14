package com.applicnation.eggnation.feature_eggnation.data.repository

import android.util.Log
import com.applicnation.eggnation.feature_eggnation.data.remote.firebase.Authentication
import com.applicnation.eggnation.feature_eggnation.domain.repository.AuthenticationRepository
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val authenticator: Authentication
): AuthenticationRepository {
    override suspend fun signInUser(email: String, password: String) {
       authenticator.signInUser(email, password)
    }

    override suspend fun signUpUser(email: String, password: String) {
        Log.i("SignUp", "in auth rep implementation")
        authenticator.signUpUser(email, password)
    }

    override suspend fun sendPasswordResetEmail(email: String) {
        authenticator.sendPasswordResetEmail(email)
    }

    override suspend fun verifyUserEmail(email: String) {
        TODO("Not yet implemented")
    }

    override fun signOutUser() {
        authenticator.signOutUser()
    }

    override fun getUserId(): String? {
        return authenticator.getUserId()
    }


}