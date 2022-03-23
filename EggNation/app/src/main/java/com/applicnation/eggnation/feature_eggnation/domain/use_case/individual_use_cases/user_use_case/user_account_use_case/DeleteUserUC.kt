package com.applicnation.eggnation.feature_eggnation.domain.use_case.individual_use_cases.user_use_case.user_account_use_case

import com.applicnation.eggnation.feature_eggnation.domain.repository.AuthenticationRepository
import com.applicnation.eggnation.util.Resource
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

class DeleteUserUC @Inject constructor(
    private val authenticator: AuthenticationRepository
) {

    /**
     * Permanently deletes user's account
     * @exception FirebaseAuthInvalidUserException
     * @exception FirebaseAuthInvalidCredentialsException
     * @exception FirebaseAuthRecentLoginRequiredException
     * @exception FirebaseNetworkException
     * @exception FirebaseTooManyRequestsException
     * @exception Exception All exceptions thrown from this catch block are UNEXPECTED
     */
    operator fun invoke(password: String) = flow {
        emit(Resource.Loading<String>())

        val email = authenticator.getUserEmail()

        if (email == null) {
            Timber.wtf("!!!! FIREBASE AUTH: email is null. This should never happen")
            emit(Resource.Error<String>(message = "Failed to verify account"))
            return@flow
        }

        try {
            authenticator.authenticateUser(email, password)
        } catch (e: FirebaseAuthInvalidUserException) {
            Timber.e("FIREBASE AUTH: Failed to re-authenticate user: The account has either been deleted or disabled --> $e")
            emit(Resource.Error<String>(message = "Failed to verify account"))
            return@flow
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Timber.e("FIREBASE AUTH: Failed to re-authenticate: Invalid credentials (password is incorrect) --> $e")
            emit(Resource.Error<String>(message = "Invalid password"))
            return@flow
        } catch (e: FirebaseTooManyRequestsException) {
            Timber.e("FIREBASE AUTH: Failed to re-authenticate: Too many attempts --> $e")
            emit(Resource.Error<String>(message = "Too many attempts. Try again later"))
            return@flow
        }   catch (e: FirebaseNetworkException) {
            Timber.e("FIREBASE AUTH: Failed to re-authenticate: Not connected to the internet --> $e")
            emit(Resource.Error<String>(message = "Failed to verify account. Not connected to the internet"))
            return@flow
        } catch (e: Exception) {
            Timber.wtf("FIREBASE AUTH: Failed to delete user account: An unexpected error occurred --> $e")
            emit(Resource.Error<String>(message = "Failed to verify account"))
            return@flow
        }

        try {
            authenticator.deleteUserAccount()
        } catch (e: FirebaseAuthInvalidUserException) {
            Timber.e("FIREBASE AUTH: Failed to delete user: The account has either been deleted or disabled --> $e")
            emit(Resource.Error<String>(message = "Failed to delete account"))
            return@flow
        } catch (e: FirebaseAuthRecentLoginRequiredException) {
            Timber.wtf("FIREBASE AUTH: Failed to delete user: Re-authentication required. The user needs a recent login --> $e")
            emit(Resource.Error<String>(message = "Failed to delete account"))
            return@flow
        } catch (e: FirebaseNetworkException) {
            Timber.e("FIREBASE AUTH: Failed to re-authenticate: Not connected to the internet --> $e")
            emit(Resource.Error<String>(message = "Failed to delete account. Not connected to the internet"))
            return@flow
        } catch (e: Exception) {
            Timber.wtf("FIREBASE AUTH: Failed to delete user account: An unexpected error occurred --> $e")
            emit(Resource.Error<String>(message = "Failed to delete account"))
            return@flow
        }

        emit(Resource.Success<String>())
    }.flowOn(Dispatchers.IO)
}