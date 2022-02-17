package com.applicnation.eggnation.feature_eggnation.domain.repository

import android.net.Uri
import com.google.firebase.auth.FirebaseUser

interface AuthenticationRepository {
    suspend fun signUpUser(email: String, password: String)
    suspend fun signInUser(email: String, password: String)
    fun signOutUser()
    suspend fun deleteUserAccount(email: String, password: String)
    suspend fun authenticateUser(email: String, password: String)

    suspend fun sendPasswordResetEmail(email: String)
    suspend fun sendVerificationEmail()


    /** Getters */
    fun getUserLoggedInStatus(): FirebaseUser?
    fun getUserId(): String?
    fun getUserEmail(): String?
    fun getUserUsername(): String?
    fun getUserProfilePicture(): Uri?
    fun getUserEmailVerificationStatus(): Boolean?

    /** Setters */
    suspend fun updateUserEmailAddress(newEmail: String)
    suspend fun updateUserPassword(newPassword: String)
    suspend fun updateUserUsername(newUsername: String)
    suspend fun updateUserProfilePicture(newProfilePictureUri: Uri)
}
