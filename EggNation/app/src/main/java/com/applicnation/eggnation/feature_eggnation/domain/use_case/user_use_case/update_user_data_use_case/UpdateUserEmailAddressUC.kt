package com.applicnation.eggnation.feature_eggnation.domain.use_case.user_use_case.update_user_data_use_case

import com.applicnation.eggnation.feature_eggnation.domain.repository.AuthenticationRepository
import com.applicnation.eggnation.feature_eggnation.domain.repository.DatabaseRepository
import com.applicnation.eggnation.feature_eggnation.domain.repository.FunctionsRepository
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
import java.lang.Exception
import javax.inject.Inject

class UpdateUserEmailAddressUC @Inject constructor(
    private val authenticator: AuthenticationRepository,
    private val repository: DatabaseRepository,
    private val functions: FunctionsRepository
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
        password: String,
        newEmail: String
    ): Flow<Resource<String>> =
        flow {
            emit(Resource.Loading<String>())

            val currentEmail = authenticator.getUserEmail()
            if (currentEmail == null) {
                Timber.wtf("!!!! user is null? This is literally impossible to happen")
                emit(Resource.Error<String>("Failed to update email"))
                return@flow
            }

            try {
                authenticator.authenticateUser(currentEmail, password)
                emit(Resource.Success<String>(message = "password valid"))
            } catch (e: FirebaseAuthInvalidUserException) {
                Timber.e("Account has been disabled or deleted --> $e")
                emit(Resource.Error<String>(message = "Invalid password"))
            } catch (e: FirebaseAuthInvalidCredentialsException) {
                Timber.e("Invalid credentials --> $e")
                emit(Resource.Error<String>(message = "Invalid password"))
            } catch (e: Exception) {
                Timber.e("Something went wrong --> $e")
                emit(Resource.Error<String>(message = "Invalid password"))
            }

            try {
                functions.updateUserEmail(newEmail)
            } catch (e: Exception) {
                Timber.e("Failed to update email in functions --> $e")
                emit(Resource.Error<String>("Failed to update email"))
            }

            try {
                authenticator.authenticateUser(newEmail, password)
                emit(Resource.Success<String>(message = "password valid"))
            } catch (e: FirebaseAuthInvalidUserException) {
                Timber.e("Account has been disabled or deleted --> $e")
                emit(Resource.Error<String>(message = "Invalid password"))
            } catch (e: FirebaseAuthInvalidCredentialsException) {
                Timber.e("Invalid credentials --> $e")
                emit(Resource.Error<String>(message = "Invalid password"))
            } catch (e: Exception) {
                Timber.e("Something went wrong --> $e")
                emit(Resource.Error<String>(message = "Invalid password"))
            }

            try {
                authenticator.reloadUser()
            } catch (e: Exception) {
                emit(Resource.Error<String>("Failed to update email"))
            }

            emit(Resource.Success<String>(message = "Email updated successfully"))
        }.flowOn(Dispatchers.IO)
}
