package com.applicnation.eggnation.feature_eggnation.domain.use_case.authentication_use_case

import com.applicnation.eggnation.feature_eggnation.domain.repository.AuthenticationRepository
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class UpdateUserUsernameUC @Inject constructor(
    private val authenticator: AuthenticationRepository
) {



    /**
     * Updates the user's username.
     * TODO - fix up documentation
     * @param newUsername The new username to set for the user's profile
     * @exception FirebaseAuthInvalidUserException The user's account is either disabled, deleted or credentials are no longer valid
     * @exception Exception All exceptions thrown from this catch block are UNEXPECTED
     */
    suspend operator fun invoke(newUsername: String) {


        if (authenticator.getUserLoggedInStatus() == null) {
            throw Exception()
            // TODO - throw exception?
            // TODO - this is a really bad situation to be in
        }




        try {
            authenticator.updateUserUsername(newUsername)
            Timber.i("SUCCESS: User username updated")
        } catch (e: FirebaseAuthInvalidUserException) {
            // TODO - propogate UI message saying the username was not updated
            // TODO - maybe if the user changes their email this may be thrown? maybe I'll need to re-authenticate then
            Timber.e("Failed to update user username: The user's account is either disabled, deleted or the credentials are now invalid --> $e")
        } catch (e: Exception) {
            // TODO - propogate UI message saying the username was not updated
            Timber.e("Failed to update user username: An unexpected error occurred --> $e")
        }
    }
}
