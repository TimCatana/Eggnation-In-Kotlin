package com.applicnation.eggnation.feature_eggnation.domain.repository

interface AuthenticationRepository {

    suspend fun signInUser(email: String, password: String)
    suspend fun signUpUser(email: String, password: String)
    suspend fun verifyUserEmail(email: String)
    suspend fun sendPasswordResetEmail(email: String)
    fun signOutUser()
    fun getUserId(): String?

//    suspend fun updateUser(User: Any) // TODO - probably add the firebase auth object as a parameter

}