package com.applicnation.eggnation.feature_eggnation.data.repository

import android.net.Uri
import com.applicnation.eggnation.feature_eggnation.data.remote.firebase.Authentication
import com.applicnation.eggnation.feature_eggnation.domain.repository.AuthenticationRepository
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val authenticator: Authentication
) : AuthenticationRepository {

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

    override suspend fun deleteUserAccount(email: String, password: String) {
        authenticator.deleteUserAccount(email, password)
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
     * Getters For User Account
     */
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
    override suspend fun updateUserEmailAddress(email: String, password: String, newEmail: String) {
        authenticator.updateUserEmailAddress(email, password, newEmail)
    }

    override suspend fun updateUserPassword(email: String, password: String, newPassword: String) {
        authenticator.updateUserPassword(email, password, newPassword)
    }

    override suspend fun updateUserUsername(newUsername: String) {
        authenticator.updateUserUsername(newUsername)
    }

    override suspend fun updateUserProfilePicture(newProfilePictureUri: Uri) {
        authenticator.updateUserProfilePicture(newProfilePictureUri)
    }
}
