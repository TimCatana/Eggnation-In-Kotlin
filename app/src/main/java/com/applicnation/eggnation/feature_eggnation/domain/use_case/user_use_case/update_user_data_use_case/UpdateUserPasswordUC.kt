package com.applicnation.eggnation.feature_eggnation.domain.use_case.user_use_case.update_user_data_use_case

import com.applicnation.eggnation.feature_eggnation.domain.repository.AuthenticationRepository
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import timber.log.Timber
import javax.inject.Inject

class UpdateUserPasswordUC @Inject constructor(
    private val authenticator: AuthenticationRepository
) {
    /**
     * Updates the user's password.
     * @param email The user email (used for re-authentication)
     * @param password The user password (used for re-authentication)
     * @param newPassword The new password to set for the user's account
     * @exception FirebaseAuthInvalidUserException
     * @exception FirebaseAuthInvalidCredentialsException
     * @exception FirebaseAuthWeakPasswordException
     * @exception FirebaseAuthRecentLoginRequiredException SHOULD NEVER BE THROWN!!!
     * @exception Exception All exceptions caught in this block are UNEXPECTED
     * @note Read the log messages below to see what each exception means (it is too messy to put all the info in this comment)
     */
    suspend operator fun invoke(email: String, password: String, newPassword: String) {
        if (authenticator.getUserLoggedInStatus() == null) {
            throw Exception()
            // TODO - throw exception?
            // TODO - this is a really bad situation to be in
        }

        // TODO - propogate successes and errors to UI

        try {
            authenticator.authenticateUser(email, password)
        } catch (e: FirebaseAuthInvalidUserException) {
            Timber.e("Failed to re-authenticate user: The account has either been deleted or disabled --> $e")
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Timber.e("Failed to re-authenticate user: Invalid credentials (password is incorrect) --> $e")
        } catch (e: Exception) {
            Timber.wtf("Failed to re-authenticate user: An unexpected error occurred --> $e")
        }

        try {
            authenticator.updateUserPassword(newPassword)
        } catch (e: FirebaseAuthInvalidUserException) {
            Timber.e("Failed to update user password: Either user's account is disabled or deleted and re-authentication failed OR user's account is disabled, deleted or credentials are no longer valid --> $e")
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Timber.e("Failed to update user password: Either re-authentication failed to due Invalid email/password or if user is trying to access someone else's account OR the new password address is not formatted correctly and invalid --> $e")
        } catch (e: FirebaseAuthWeakPasswordException) {
            Timber.e("Failed to update user password: Weak Password (needs to be greater than 6 characters) --> $e")
        } catch (e: FirebaseAuthRecentLoginRequiredException) {
            Timber.wtf("!!!! Failed to update user password: The re-authentication failed but the program still tried to update the user's email. An exception should have been thrown from reauthenticate() and prevented all code following it from running --> $e")
        } catch (e: Exception) {
            Timber.e("Failed to update user password: An unexpected error occurred --> $e")
        }
    }
}
