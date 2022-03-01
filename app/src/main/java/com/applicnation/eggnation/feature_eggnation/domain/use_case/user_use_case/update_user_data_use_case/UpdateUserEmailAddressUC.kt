package com.applicnation.eggnation.feature_eggnation.domain.use_case.user_use_case.update_user_data_use_case

import com.applicnation.eggnation.feature_eggnation.domain.repository.AuthenticationRepository
import com.applicnation.eggnation.feature_eggnation.domain.repository.DatabaseRepository
import com.applicnation.eggnation.util.Resource
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

class UpdateUserEmailAddressUC @Inject constructor(
    private val authenticator: AuthenticationRepository,
    private val repository: DatabaseRepository
) {

    // TODO - fix documentation
    // TODO - maybe get rid of userID from parameter and just use the authenticator getUserId function?
    /**
     * Updates the user's email address.
     * @param email The user email (used for re-authentication)
     * @param password The user password (used for re-authentication)
     * @param newEmail The new email to set for the user's profile
     * @exception FirebaseAuthInvalidUserException
     * @exception FirebaseAuthInvalidCredentialsException
     * @exception FirebaseAuthUserCollisionException
     * @exception FirebaseAuthRecentLoginRequiredException SHOULD NEVER BE THROWN!!!
     * @exception Exception All exceptions caught in this block are UNEXPECTED
     * @note Read the log messages below to see what each exception means (it is too messy to put all the info in this comment)
     */

//    /**
//     * Updates the user email in firestore
//     * @param userId The user's user ID (usually always from Firebase auth). user documents are named after the user's uid
//     * @param newEmail The new email the email field should be updated to
//     * @exception FirebaseFirestoreException This is the only exception Firestore can throw as of now (according to documentation)
//     * @exception Exception All exceptions thrown from this catch block are UNEXPECTED
//     */

    operator fun invoke(
        newEmail: String
    ): Flow<Resource<String>> =
        flow {
            emit(Resource.Loading<String>())

            val userId = authenticator.getUserId()
            val email = authenticator.getUserEmail()

            if (email == null || userId == null) {
                // TODO - this is a really bad situation to be in
                Timber.e("!!!! User is logged out but is trying to update email. Something is horribly wrong")
                emit(Resource.Error<String>("Failed to update email"))
                return@flow
            }

            try {
                authenticator.updateUserEmailAddress(newEmail)
                Timber.i("UPDATE EMAIL SUCCESS: User email address updated")
            } catch (e: FirebaseAuthInvalidUserException) {
                Timber.e("UPDATE EMAIL Failed to update user email: The user's account is either disabled, deleted or credentials are no longer valid (thrown either by reauthenticate() or updateEmail() --> $e")
                emit(Resource.Error<String>("Invalid Credentials!"))
                return@flow
            } catch (e: FirebaseAuthInvalidCredentialsException) {
                Timber.e("UPDATE EMAIL Failed to update user email: Either re-authentication failed to due Invalid email/password OR user is trying to access someone else's account OR the new email address is not formatted correctly and invalid --> $e")
                emit(Resource.Error<String>("Invalid Credentials!"))
                return@flow
            } catch (e: FirebaseAuthUserCollisionException) {
                Timber.e("UPDATE EMAIL Failed to update user email: An account already exists with that email address --> $e")
                emit(Resource.Error<String>("Email already exists!"))
                return@flow
            } catch (e: FirebaseAuthRecentLoginRequiredException) {
                Timber.wtf("!!!! UPDATE EMAIL Failed to update user email: The re-authentication failed but the program still tried to update the user's email. An exception should have been thrown from reauthenticate() and prevented all code following it from running --> $e")
                emit(Resource.Error<String>("Failed to update email."))
                return@flow
            } catch (e: Exception) {
                Timber.e("UPDATE EMAIL Failed to update user email address: An unexpected error occurred --> $e")
                emit(Resource.Error<String>("Failed to update email."))
                return@flow
            }

            try {
                repository.updateUserEmail(userId, newEmail)
                Timber.i("DATABASE SUCCESS: User email address updated")
            } catch (e: FirebaseFirestoreException) {
                // TODO - either retry or revert Firebase Auth email. Probably the latter
                Timber.e("DATABASE Failed to update user email: An unexpected FIRESTORE error occurred --> $e")
                emit(Resource.Error<String>("Failed to update email."))
                return@flow
            } catch (e: Exception) {
                Timber.e("DATABASE Failed to update user email: An unexpected error occurred --> $e")
                emit(Resource.Error<String>("Failed to update email."))
                return@flow
            }

            // TODO - users get an email (to their old address) when email is updaated. Need to see how the link works

            emit(Resource.Success<String>(message = "Email updated successfully"))
        }.flowOn(Dispatchers.IO)
}
