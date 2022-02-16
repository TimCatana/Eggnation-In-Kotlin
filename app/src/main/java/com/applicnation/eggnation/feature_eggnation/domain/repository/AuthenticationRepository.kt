package com.applicnation.eggnation.feature_eggnation.domain.repository

import android.net.Uri

interface AuthenticationRepository {
    suspend fun signUpUser(email: String, password: String)
    suspend fun signInUser(email: String, password: String)
    fun signOutUser()
    suspend fun deleteUserAccount(email: String, password: String)

    suspend fun sendPasswordResetEmail(email: String)
    suspend fun sendVerificationEmail()

    /** Getters */
    fun getUserId(): String?
    fun getUserEmail(): String?
    fun getUserUsername(): String?
    fun getUserProfilePicture(): Uri?
    fun getUserEmailVerificationStatus(): Boolean?

    /** Setters */
    suspend fun updateUserEmailAddress(email: String, password: String, newEmail: String)
    suspend fun updateUserPassword(email: String, password: String, newPassword: String)
    suspend fun updateUserUsername(newUsername: String)
    suspend fun updateUserProfilePicture(newProfilePictureUri: Uri)
}
