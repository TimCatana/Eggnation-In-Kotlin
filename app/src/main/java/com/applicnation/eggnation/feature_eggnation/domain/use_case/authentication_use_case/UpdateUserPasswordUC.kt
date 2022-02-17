package com.applicnation.eggnation.feature_eggnation.domain.use_case.authentication_use_case

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
     * @param email The user email
     * @param password The user password
     * TODO - fix up documentation
     * @param newPassword The new password to set for the user's profile
     * @exception FirebaseAuthInvalidUserException The user's account is either disabled, deleted (when thrown by reauthenticate())
     *                                             OR the user's account is either disabled, deleted or credentials are no longer valid (when thrown by updatePassword())
     * @exception FirebaseAuthInvalidCredentialsException Either re-authentication failed to due Invalid email/password
     *                                                    OR user is trying to access someone else's account (malicious)
     *                                                    OR the new password is not formatted correctly and invalid (should never occur)
     * @exception FirebaseAuthWeakPasswordException The use password is less that 6 chars. SHOULD NEVER BE THROWN!!!
     * @exception FirebaseAuthRecentLoginRequiredException The user needs to have been re-authenticated recently in order for this update to occur. SHOULD NEVER BE THROWN!!!
     * @exception Exception All exceptions thrown from this catch block are UNEXPECTED
     */
    suspend operator fun invoke(email: String, password: String, newPassword: String) {


        if (authenticator.getUserLoggedInStatus() == null) {
            throw Exception()
            // TODO - throw exception?
            // TODO - this is a really bad situation to be in
        }



        try {
            authenticator.authenticateUser(email, password)
        } catch (e: FirebaseAuthInvalidUserException) {
            Timber.e("Failed to re-authenticate user: The account has either been deleted or disabled --> $e")
//            throw FailedToReauthenticateException("Something went wrong. Please try again")
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Timber.e("Failed to re-authenticate: Invalid credentials (password is incorrect) --> $e")
//            throw FailedToReauthenticateException("Password incorrect")
        } catch (e: Exception) {
            Timber.wtf("Failed to delete user account: An unexpected error occurred --> $e")
//            throw FailedToReauthenticateException("something went wrong. Please try again")
        }



        try {
            authenticator.updateUserPassword(newPassword)
        } catch (e: FirebaseAuthInvalidUserException) {
            // TODO - propogate UI message saying the password was not updated
            Timber.e("Failed to update user password: Either user's account is disabled or deleted and re-authentication failed OR user's account is disabled, deleted or credentials are no longer valid --> $e")
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            // TODO - propogate UI message saying the password was not updated
            Timber.e("Failed to update user password: Either re-authentication failed to due Invalid email/password or if user is trying to access someone else's account OR the new password address is not formatted correctly and invalid --> $e")
        } catch (e: FirebaseAuthWeakPasswordException) {
            // TODO - propogate UI message saying the password was not updated
            Timber.wtf("Failed to update user password: Weak Password (needs to be greater than 6 characters) --> $e")
        } catch (e: FirebaseAuthRecentLoginRequiredException) {
            // TODO - propogate UI message saying the password was not updated
            Timber.wtf("!!!! Failed to update user password: The re-authentication failed but the program still tried to update the user's email. An exception should have been thrown from reauthenticate() and prevented all code following it from running --> $e")
        } catch (e: java.lang.Exception) {
            // TODO - propogate UI message saying the password was not updated
            Timber.e("Failed to update user password: An unexpected error occurred --> $e")
        }
    }
}
