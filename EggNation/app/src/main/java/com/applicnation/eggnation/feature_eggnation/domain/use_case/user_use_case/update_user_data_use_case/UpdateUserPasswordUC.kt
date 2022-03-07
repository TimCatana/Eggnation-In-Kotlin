package com.applicnation.eggnation.feature_eggnation.domain.use_case.user_use_case.update_user_data_use_case

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
     * @param email The user email (used for re-authentication)
     * @param password The user password (used for re-authentication)
     * @param newPassword The new password to set for the user's account
     * @exception FirebaseAuthInvalidUserException
     * @exception FirebaseAuthInvalidCredentialsException
     * @exception FirebaseAuthWeakPasswordException
     * @exception FirebaseAuthRecentLoginRequiredException SHOULD NEVER BE THROWN!!!
     * @exception Exception All exceptions caught in this block are UNEXPECTED
     * @note Read the log messages below to see what each exception means (it is too messy to put all the info in this comment)
     */
    operator fun invoke(newPassword: String): Flow<Resource<String>> = flow {
        emit(Resource.Loading<String>())

        val userId = authenticator.getUserId()

        if(userId == null) {
            Timber.wtf("!!!! user is null? This is literally impossible to happen")
            emit(Resource.Error<String>("Failed to update Password"))
            return@flow
        }

        try {
            authenticator.updateUserPassword(newPassword)
            emit(Resource.Success<String>(message = "Password updated successfully"))
        } catch (e: FirebaseAuthInvalidUserException) {
            Timber.e("Failed to update user password: Either user's account is disabled or deleted and re-authentication failed OR user's account is disabled, deleted or credentials are no longer valid --> $e")
            emit(Resource.Error<String>("Failed to update Password"))
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Timber.e("Failed to update user password: Either re-authentication failed to due Invalid email/password or if user is trying to access someone else's account OR the new password address is not formatted correctly and invalid --> $e")
            emit(Resource.Error<String>("Failed to update Password"))
        } catch (e: FirebaseAuthWeakPasswordException) {
            Timber.e("Failed to update user password: Weak Password (needs to be greater than 6 characters) --> $e")
            emit(Resource.Error<String>("Failed to update Password"))
        } catch (e: FirebaseAuthRecentLoginRequiredException) {
            Timber.wtf("!!!! Failed to update user password: The re-authentication failed but the program still tried to update the user's email. An exception should have been thrown from reauthenticate() and prevented all code following it from running --> $e")
            emit(Resource.Error<String>("Failed to update Password"))
        } catch (e: Exception) {
            Timber.e("Failed to update user password: An unexpected error occurred --> $e")
            emit(Resource.Error<String>("Failed to update Password"))
        }
    }.flowOn(Dispatchers.IO)
}
