package com.applicnation.eggnation.feature_eggnation.data.repository

import android.net.Uri
import com.applicnation.eggnation.feature_eggnation.data.remote.firebase.Authentication
import com.applicnation.eggnation.feature_eggnation.domain.repository.AuthenticationRepository
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val authenticator: Authentication
) : AuthenticationRepository {

    override fun reloadUser() {
        authenticator.reloadUser()
    }

    /**
     * User Account Status
     */
    override suspend fun signUpUser(email: String, password: String) {
        authenticator.signUpUser(email, password)
    }

    override suspend fun signInUser(email: String, password: String) {
        authenticator.signInUser(email, password)
    }

    override fun signOutUser() {
        authenticator.signOutUser()
    }

    override suspend fun deleteUserAccount() {
        authenticator.deleteUserAccount()
    }

    override suspend fun authenticateUser(email: String, password: String) {
        authenticator.authenticateUser(email, password)
    }

    /**
     * User Account Modifications
     */
    override suspend fun sendPasswordResetEmail(email: String) {
        authenticator.sendPasswordResetEmail(email)
    }

    override suspend fun sendVerificationEmail() {
        authenticator.sendVerificationEmail()
    }

    /**
     * Getters
     */
    override fun getUserLoggedInStatus(): FirebaseUser? {
        return authenticator.getUserLoggedInStatus()
    }

    override fun getUserId(): String? {
        return authenticator.getUserId()
    }

    override fun getUserEmail(): String? {
        return authenticator.getUserEmail()
    }

    override fun getUserUsername(): String? {
        return authenticator.getUserUsername()
    }

    override fun getUserProfilePicture(): Uri? {
        return authenticator.getUserProfilePicture()
    }

    override fun getUserEmailVerificationStatus(): Boolean? {
        return authenticator.getUserEmailVerificationStatus()
    }

    /**
     * Setters For User Account
     */
    override suspend fun updateUserEmailAddress(newEmail: String) {
        authenticator.updateUserEmailAddress(newEmail)
    }

    override suspend fun updateUserPassword(newPassword: String) {
        authenticator.updateUserPassword(newPassword)
    }

    override suspend fun updateUserUsername(newUsername: String) {
        authenticator.updateUserUsername(newUsername)
    }

    override suspend fun updateUserProfilePicture(newProfilePictureUri: Uri) {
        authenticator.updateUserProfilePicture(newProfilePictureUri)
    }
}
