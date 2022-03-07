package com.applicnation.eggnation.feature_eggnation.data.remote.firebase

import android.net.Uri
import com.applicnation.eggnation.feature_eggnation.domain.util.*
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.userProfileChangeRequest
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import java.lang.Exception
import kotlin.jvm.Throws

class Authentication {
    private val auth = FirebaseAuth.getInstance()

//    suspend fun stateListenenr(): FirebaseAuth.AuthStateListener {
//        return auth.addAuthStateListener {
//
//        }
//    }

    /**
     * Creates a new account for the user.
     * Adds the user to firebase auth console
     * @param email (String) The user email (should be valid and sanitized by now)
     * @param password (String) The user password (should be valid and sanitized by now)
     *                 should be greater than 8 characters contain lowercase letters, uppercase letters and numbers. Should contain no whitespace
     * @note Errors are caught and dealt with in use-case
     */
    suspend fun signUpUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).await()
    }

    /**
     * Signs the user into their account given their email and password
     * @param email The user email (should be sanitized by now)
     * @param password The user password (should be sanitized by now)
     * @note Errors are caught and dealt with in use-case
     */
    suspend fun signInUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).await()
    }

    /**
     * Signs the user out of their account on their given device
     * @note Errors are caught and dealt with in use-case
     */
    fun signOutUser() {
        auth.signOut()
    }

    /**
     * Re-authenticates the user
     * @param email The email for the account to authenticate
     * @param password The password for the account to authenticate
     * @note Errors are caught and dealt with in use-case
     * @importantNote Current user MUST be logged in before thus function is run. Check that getUserLoggedInStatus is not null.
     */
    suspend fun authenticateUser(email: String, password: String) {
        val credential = EmailAuthProvider.getCredential(email, password)
        auth.currentUser?.reauthenticate(credential)?.await()
    }

    /**
     * Deletes a user from Firebase authentication. This action is irreversable.
     * @note Errors are caught and dealt with in use-case
     * @importantNote Current user MUST be logged in before thus function is run. Check that getUserLoggedInStatus is not null.
     */
    suspend fun deleteUserAccount() {
        auth.currentUser?.delete()?.await()
    }

    /**
     * Sends the user a verification email. The email should contain a deep link to the VerifyEmailScreen.
     * If user is not logged in when they reach the VerifyEmailScreen, then do not do anything. That is a security hazard
     * @param email The email to send password reset link to
     * @note Errors are caught and dealt with in use-case
     */
    suspend fun sendPasswordResetEmail(email: String) {
        auth.sendPasswordResetEmail(email).await()
    }

    /**
     * Sends a verification email to the email that the current user is using.
     * If they inputted the incorrect email when they registered, they have the option to change their email in settings.
     * @note Errors are caught and dealt with in use-case
     * @importantNote Current user MUST be logged in before thus function is run. Check that getUserLoggedInStatus is not null.
     */
    suspend fun sendVerificationEmail() {
        auth.currentUser?.sendEmailVerification()?.await()
    }

    /**
     * Getters for the user's profile
     * - getUserLoggedInStatus
     * - getUserId
     * - getUserEmail
     * - getUserUsername
     * - getUserPhotoUrl
     * - getUserEmailVerificationStatus
     */

    fun getUserLoggedInStatus(): FirebaseUser? {
        return auth.currentUser
    }

    /**
     * @note Errors are caught and dealt with in use-case
     * @importantNote Sometimes current user MUST be logged in before this function is run to prevent errors.
     *                Check that getUserLoggedInStatus is not null.
     */
    fun getUserId(): String? {
        return auth.currentUser?.uid
    }

    /**
     * @note Errors are caught and dealt with in use-case
     * @importantNote Sometimes current user MUST be logged in before this function is run to prevent errors.
     *                Check that getUserLoggedInStatus is not null.
     */
    fun getUserEmail(): String? {
        return auth.currentUser?.email
    }

    /**
     * @note Errors are caught and dealt with in use-case
     * @importantNote Sometimes current user MUST be logged in before this function is run to prevent errors.
     *                Check that getUserLoggedInStatus is not null.
     */
    fun getUserUsername(): String? {
        Timber.wtf("IN AUTH BACKEND: ${auth.currentUser?.displayName}")
        return auth.currentUser?.displayName
    }

    suspend fun reloadUser() {
        auth.currentUser?.getIdToken(true)?.await()
    }

    /**
     * @note Errors are caught and dealt with in use-case
     * @importantNote Sometimes current user MUST be logged in before this function is run to prevent errors.
     *                Check that getUserLoggedInStatus is not null.
     */
    fun getUserProfilePicture(): Uri? {
        return auth.currentUser?.photoUrl
    }

    /**
     * @note Errors are caught and dealt with in use-case
     * @importantNote Sometimes current user MUST be logged in before this function is run to prevent errors.
     *                Check that getUserLoggedInStatus is not null.
     */
    fun getUserEmailVerificationStatus(): Boolean? {
        return auth.currentUser?.isEmailVerified
    }

    /**
     * Setters for the user's profile
     * - updateUserEmailAddress
     * - updateUserPassword
     * - updateUserUsername
     * - updateProfilePicture
     */

    /**
     * Updates the user's email address.
     * @param newEmail The new email to set for the user's profile
     * @note Errors are caught and dealt with in use-case
     * @importantNote Current user MUST be logged in before thus function is run. Check that getUserLoggedInStatus is not null.
     */
    suspend fun updateUserEmailAddress(newEmail: String) {
        auth.currentUser?.updateEmail(newEmail)?.await()
    }

    /**
     * Updates the user's password.
     * @param newPassword The new password to set for the user's profile
     * @note Errors are caught and dealt with in use-case
     * @importantNote Current user MUST be logged in before thus function is run. Check that getUserLoggedInStatus is not null.
     */
    suspend fun updateUserPassword(newPassword: String) {
        auth.currentUser?.updatePassword(newPassword)?.await()
    }

    /**
     * Updates the user's username.
     * @param newUsername The new username to set for the user's profile
     * @note Errors are caught and dealt with in use-case
     * @importantNote Current user MUST be logged in before thus function is run. Check that getUserLoggedInStatus is not null.
     */
    suspend fun updateUserUsername(newUsername: String) {
        val profileUpdates = userProfileChangeRequest { displayName = newUsername }
        auth.currentUser?.updateProfile(profileUpdates)?.await()
    }

    /**
     * Updates the user's profile picture.
     * @param newProfilePictureUri The new profile picture's uri to set for the user's profile
     * @note Errors are caught and dealt with in use-case
     * @importantNote Current user MUST be logged in before thus function is run. Check that getUserLoggedInStatus is not null.
     */
    suspend fun updateUserProfilePicture(newProfilePictureUri: Uri) {
        val profileUpdates = userProfileChangeRequest { photoUri = newProfilePictureUri }
        auth.currentUser?.updateProfile(profileUpdates)?.await()
    }

}
