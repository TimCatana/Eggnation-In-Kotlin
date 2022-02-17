package com.applicnation.eggnation.feature_eggnation.domain.use_case.authentication_use_case

import com.applicnation.eggnation.feature_eggnation.domain.repository.AuthenticationRepository
import com.applicnation.eggnation.feature_eggnation.domain.util.FailedToDeleteAccountException
import com.applicnation.eggnation.feature_eggnation.domain.util.FailedToReauthenticateException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException
import timber.log.Timber
import javax.inject.Inject

class DeleteUserAccountUC @Inject constructor(
    private val authenticator: AuthenticationRepository
) {

    /** Deletes a user from Firebase authentication. This action is irreversable.
     * @param email The user email
     * @param password The user password
     * TODO - fix up this documentation
     * @exception FirebaseAuthInvalidUserException Either the account re-authentication failed (the user inputted an incorrect password)
     *                                             OR The re-authentication failed and the program attempted to delete the user (this should never happen)
     * @exception FirebaseAuthInvalidCredentialsException Either the user's credentials are incorrect
     *                                                    OR the user tried to delete someone else's account (malicous behaviour)
     * @exception Exception All exceptions thrown from this catch block are UNEXPECTED
     * */
    suspend operator fun invoke(email: String, password: String) {

        if (authenticator.getUserLoggedInStatus() == null) {
            throw Exception()
            // TODO - throw exception?
            // TODO - this is a bad situation to be in
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
            authenticator.deleteUserAccount(email, password)
        } catch (e: FirebaseAuthInvalidUserException) {
            Timber.e("Failed to delete user: The account has either been deleted or disabled --> $e")
//            throw FailedToDeleteAccountException("Something went wrong. Please try again")
        } catch (e: FirebaseAuthRecentLoginRequiredException) {
            Timber.wtf("Failed to delete user: Re-authentication required. Something went horribly wrong since this error should have been caught before reaching this section --> $e")
//            throw FailedToDeleteAccountException("Password incorrect")
        } catch (e: java.lang.Exception) {
            Timber.wtf("Failed to delete user account: An unexpected error occurred --> $e")
//            throw FailedToDeleteAccountException("Failed to delete account!")
        }
    }
}
