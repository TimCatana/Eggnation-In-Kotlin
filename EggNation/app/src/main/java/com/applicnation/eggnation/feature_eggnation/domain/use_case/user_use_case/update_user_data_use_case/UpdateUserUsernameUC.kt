package com.applicnation.eggnation.feature_eggnation.domain.use_case.user_use_case.update_user_data_use_case

import com.applicnation.eggnation.feature_eggnation.domain.repository.AuthenticationRepository
import com.applicnation.eggnation.feature_eggnation.domain.repository.DatabaseRepository
import com.applicnation.eggnation.util.Resource
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
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
    operator fun invoke(newUsername: String): Flow<Resource<String>> = flow {
        emit(Resource.Loading<String>())

        val userId = authenticator.getUserId()
        val currentUsername = authenticator.getUserUsername()

        if (userId == null || currentUsername == null) {
            Timber.wtf("!!!! user is null? This is literally impossible to happen")
            emit(Resource.Error<String>("Failed to update username"))
            return@flow
        }

        try {
            authenticator.updateUserUsername(newUsername)
            Timber.i("FIREBASE AUTHENTICATION: SUCCESS: User username updated")
        } catch (e: FirebaseAuthInvalidUserException) {
            Timber.e("Failed to update user username: The user's account is either disabled, deleted or the credentials are now invalid --> $e")
            emit(Resource.Error<String>("Failed to update username"))
            return@flow
        } catch (e: Exception) {
            Timber.e("Failed to update user username: An unexpected error occurred --> $e")
            emit(Resource.Error<String>("Failed to update username"))
            return@flow
        }

        try {
            repository.updateUserUsername(userId, newUsername)
            Timber.i("FIREBASE FIRESTORE: SUCCESS: User username updated")
        } catch (e: FirebaseFirestoreException) {
            // TODO - change username back to current username in auth
            Timber.e("Failed to add prize to firestore: An unexpected FIRESTORE error occurred --> $e")
            emit(Resource.Error<String>("Failed to update username"))
            return@flow
        } catch (e: Exception) {
            Timber.e("Failed to update user username in firestore: An unexpected error occurred --> $e")
            emit(Resource.Error<String>("Failed to update username"))
            return@flow
        }

        try {
            authenticator.reloadUser()
        } catch (e: Exception) {
            emit(Resource.Error<String>("Failed to update email"))
            return@flow
        }

        emit(Resource.Success<String>(message = "Username updated successfully"))
    }.flowOn(Dispatchers.IO)
}
