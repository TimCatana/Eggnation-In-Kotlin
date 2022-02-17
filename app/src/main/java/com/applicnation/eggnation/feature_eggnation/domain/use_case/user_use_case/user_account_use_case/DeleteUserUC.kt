package com.applicnation.eggnation.feature_eggnation.domain.use_case.user_use_case.user_account_use_case

import com.applicnation.eggnation.feature_eggnation.domain.repository.AuthenticationRepository
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException
import timber.log.Timber
import javax.inject.Inject

class DeleteUserUC @Inject constructor(
    private val authenticator: AuthenticationRepository
) {

    // TODO - clean up documentation

    /**
     * Permenantly deletes the user's account
     * @exception Exception All exceptions thrown from this catch block are UNEXPECTED
     */
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

        // TODO - run cloud function to delete user from database as well so that there's no garbage data in the db
    }
}