package com.applicnation.eggnation.feature_eggnation.domain.use_case.authentication_use_case

import com.applicnation.eggnation.feature_eggnation.domain.repository.AuthenticationRepository
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import timber.log.Timber
import javax.inject.Inject

class UpdateUserEmailAddressUC @Inject constructor(
    private val authenticator: AuthenticationRepository
) {

    /**
     * Updates the user's email address .
     * @param email The user email
     * @param password The user password
     * @param newEmail The new email to set for the user's profile
     * TODO - fix up documentation
     * @exception FirebaseAuthInvalidUserException The user's account is either disabled, deleted (when thrown by reauthenticate())
     *                                             OR the user's account is either disabled, deleted or credentials are no longer valid (when thrown by updateEmail())
     * @exception FirebaseAuthInvalidCredentialsException Either re-authentication failed to due Invalid email/password
     *                                                    OR user is trying to access someone else's account (malicious)
     *                                                    OR the new email address is not formatted correctly and invalid
     * @exception FirebaseAuthUserCollisionException The user is trying th update their email address with an email that already exists (probably by someone else)
     * @exception FirebaseAuthRecentLoginRequiredException The user needs to have been re-authenticated recently in order for this update to occur. SHOULD NEVER BE THROWN!!!
     * @exception Exception All exceptions thrown from this catch block are UNEXPECTED
     */
    suspend operator fun invoke(email: String, password: String, newEmail: String) {


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
            authenticator.updateUserEmailAddress(newEmail)
            Timber.i("SUCCESS: User email address updated")
        } catch (e: FirebaseAuthInvalidUserException) {
            // TODO - propogate UI message saying the email was not updated
            Timber.e("Failed to update user email: The user's account is either disabled, deleted or credentials are no longer valid (thrown either by reauthenticate() or updateEmail() --> $e")
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            // TODO - propogate UI message saying the email was not updated
            Timber.e("Failed to update user email: Either re-authentication failed to due Invalid email/password OR user is trying to access someone else's account OR the new email address is not formatted correctly and invalid --> $e")
        } catch (e: FirebaseAuthUserCollisionException) {
            // TODO - propogate UI message saying the email was not updated
            Timber.e("Failed to update user email: An account already exists with that email address --> $e")
        } catch (e: FirebaseAuthRecentLoginRequiredException) {
            // TODO - propogate UI message saying the email was not updated
            Timber.wtf("!!!! Failed to update user email: The re-authentication failed but the program still tried to update the user's email. An exception should have been thrown from reauthenticate() and prevented all code following it from running --> $e")
        } catch (e: Exception) {
            // TODO - propogate UI message saying the email was not updated
            Timber.e("Failed to update user email address: An unexpected error occurred --> $e")
        }
    }
}
