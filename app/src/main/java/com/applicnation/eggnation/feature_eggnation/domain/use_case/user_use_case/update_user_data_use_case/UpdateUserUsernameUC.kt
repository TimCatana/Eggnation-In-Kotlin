package com.applicnation.eggnation.feature_eggnation.domain.use_case.user_use_case.update_user_data_use_case

import com.applicnation.eggnation.feature_eggnation.domain.repository.AuthenticationRepository
import com.applicnation.eggnation.feature_eggnation.domain.repository.DatabaseRepository
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.firestore.FirebaseFirestoreException
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class UpdateUserUsernameUC @Inject constructor(
    private val authenticator: AuthenticationRepository,
    private val repository: DatabaseRepository
) {

    // TODO - fix documentation

//    /**
//     * Updates the user username in firestore
//     * @param userId The user's user ID (usually always from Firebase auth). user documents are named after the user's uid
//     * @param newUsername The new username the email field should be updated to
//     * @exception FirebaseFirestoreException This is the only exception Firestore can throw as of now (according to documentation)
//     * @exception Exception All exceptions thrown from this catch block are UNEXPECTED
//     * // TODO - there may also be other specific exceptions thrown, but I think I only need to tell the user that it failed to fetch
//     * // TODO - the specific error catch blocks are used to make debugging easier when there is a problem
//     */



    /**
     * Updates the user's username.
     * @param newUsername The new username to set for the user's profile
     * @exception FirebaseAuthInvalidUserException
     * @exception Exception All exceptions caught in this block are UNEXPECTED
     * @note Read the log messages below to see what each exception means (it is too messy to put all the info in this comment)
     */
    suspend operator fun invoke(userId: String, newUsername: String) {
        if (authenticator.getUserLoggedInStatus() == null) {
            throw Exception()
            // TODO - throw exception?
            // TODO - this is a really bad situation to be in
        }

        // TODO - propogate successes and errors to UI
        try {
            authenticator.updateUserUsername(newUsername)
            Timber.i("SUCCESS: User username updated")
        } catch (e: FirebaseAuthInvalidUserException) {
            // TODO - maybe if the user changes their email this may be thrown? maybe I'll need to re-authenticate then
            Timber.e("Failed to update user username: The user's account is either disabled, deleted or the credentials are now invalid --> $e")
        } catch (e: Exception) {
            Timber.e("Failed to update user username: An unexpected error occurred --> $e")
        }


        // TODO - update in firestore as well
        try {
            repository.updateUserUsername(userId, newUsername)
        } catch (e: FirebaseFirestoreException) {
            // TODO - maybe throw an exception?
            // TODO - propogate error via a boolean. If failed, retry this function. if failed again... rip
            Timber.e("Failed to add prize to firestore: An unexpected FIRESTORE error occurred --> $e")
            throw Exception()
        } catch (e: Exception) {
            Timber.e("Failed to update user emaul in firestore: An unexpected error occurred --> $e")
        }
    }
}
