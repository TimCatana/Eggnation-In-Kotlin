package com.applicnation.eggnation.feature_eggnation.domain.use_case.individual_use_cases.user_use_case.update_user_data_use_case

import com.applicnation.eggnation.feature_eggnation.domain.repository.AuthenticationRepository
import com.applicnation.eggnation.util.Resource
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

class UpdateUserPasswordUC @Inject constructor(
    private val authenticator: AuthenticationRepository
) {
    /**
     * Updates the user's password.
     * @param password The user password (used for re-authentication)
     * @param newPassword The new password to set for the user's account
     * @exception FirebaseAuthInvalidUserException
     * @exception FirebaseAuthInvalidCredentialsException
     * @exception FirebaseAuthWeakPasswordException
     * @exception FirebaseAuthRecentLoginRequiredException SHOULD NEVER BE THROWN!!!
     * @exception Exception All exceptions caught in this block are UNEXPECTED
     * @note Read the log messages below to see what each exception means (it is too messy to put all the info in this comment)
     */

    // TDOO - add Firebase network error to the try-catches
    operator fun invoke(password: String, newPassword: String): Flow<Resource<String>> = flow {
        emit(Resource.Loading<String>())

        val email = authenticator.getUserEmail()
        if (email == null) {
            Timber.wtf("!!!! user is null? This is literally impossible to happen")
            emit(Resource.Error<String>("Failed to update Password"))
            return@flow
        }

        // authentcate user to make sure no one is trying to change their password without their consent
        try {
            authenticator.authenticateUser(email, password)
        } catch (e: Exception) {
            authenticateUserLogs(e)
            emit(Resource.Error<String>(message = "Invalid password"))
            return@flow
        }

        try {
            authenticator.updateUserPassword(newPassword)
        } catch (e: FirebaseAuthInvalidUserException) {
            Timber.e("Failed to update user password: Either user's account is disabled or deleted and re-authentication failed OR user's account is disabled, deleted or credentials are no longer valid --> $e")
            emit(Resource.Error<String>("Failed to update Password"))
            return@flow
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Timber.e("Failed to update user password: Either re-authentication failed to due Invalid email/password or if user is trying to access someone else's account OR the new password address is not formatted correctly and invalid --> $e")
            emit(Resource.Error<String>("Failed to update Password"))
            return@flow
        } catch (e: FirebaseAuthWeakPasswordException) {
            Timber.e("Failed to update user password: Weak Password (needs to be greater than 6 characters) --> $e")
            emit(Resource.Error<String>("Failed to update Password"))
            return@flow
        } catch (e: FirebaseAuthRecentLoginRequiredException) {
            Timber.wtf("!!!! Failed to update user password: The re-authentication failed but the program still tried to update the user's email. An exception should have been thrown from reauthenticate() and prevented all code following it from running --> $e")
            emit(Resource.Error<String>("Failed to update Password"))
            return@flow
        } catch (e: Exception) {
            Timber.e("Failed to update user password: An unexpected error occurred --> $e")
            emit(Resource.Error<String>("Failed to update Password"))
            return@flow
        }

        try {
            authenticator.authenticateUser(email, newPassword)
        } catch (e: FirebaseAuthInvalidUserException) {
            Timber.e("Account has been disabled or deleted --> $e")
            emit(Resource.Error<String>(message = "Invalid password"))
            return@flow
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Timber.e("Invalid credentials --> $e")
            emit(Resource.Error<String>(message = "Invalid password"))
            return@flow
        } catch (e: java.lang.Exception) {
            Timber.e("Something went wrong --> $e")
            emit(Resource.Error<String>(message = "Invalid password"))
            return@flow
        }

        try {
            authenticator.reloadUser()
        } catch (e: java.lang.Exception) {
            emit(Resource.Error<String>("update password"))
            return@flow
        }

        emit(Resource.Success<String>(message = "password valid"))
    }.flowOn(Dispatchers.IO)


    /**
     * For debugging purposes
     */
    private fun authenticateUserLogs(e: Exception) {
        when (e) {
            is FirebaseAuthInvalidUserException -> {
                Timber.e("Account has been disabled or deleted --> $e")
            }
            is FirebaseAuthInvalidCredentialsException -> {
                Timber.e("Invalid credentials --> $e")
            }
            else -> {
                Timber.e("Unexpected error --> $e")
            }
        }
    }
}
