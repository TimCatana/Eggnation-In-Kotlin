package com.applicnation.eggnation.feature_eggnation.domain.use_case.user_use_case.user_account_use_case

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

        // should never happen
        if (email == null) {
            Timber.wtf("email is null and yet the user is signed in?!?!")
            emit(Resource.Error<String>(message = "Failed to verify account"))
            return@flow
        }

        try {
            authenticator.authenticateUser(email, password)
        } catch (e: FirebaseAuthInvalidUserException) {
            Timber.e("Failed to re-authenticate user: The account has either been deleted or disabled --> $e")
            emit(Resource.Error<String>(message = "Failed to verify account"))
            return@flow
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Timber.e("Failed to re-authenticate: Invalid credentials (password is incorrect) --> $e")
            emit(Resource.Error<String>(message = "Invalid password"))
            return@flow
        } catch (e: FirebaseTooManyRequestsException) {
            Timber.e("Failed to re-authenticate: Too many attemps --> $e")
            emit(Resource.Error<String>(message = "Too many attempts. Try again later"))
            return@flow
        }   catch (e: FirebaseNetworkException) {
            Timber.e("Failed to re-authenticate: Invalid credentials (password is incorrect) --> $e")
            emit(Resource.Error<String>(message = "Failed to verify account. Not connected to the internet"))
            return@flow
        } catch (e: Exception) {
            Timber.wtf("Failed to delete user account: An unexpected error occurred --> $e")
            emit(Resource.Error<String>(message = "Failed to verify account"))
            return@flow
        }

        try {
            authenticator.deleteUserAccount()
        } catch (e: FirebaseAuthInvalidUserException) {
            Timber.e("Failed to delete user: The account has either been deleted or disabled --> $e")
            emit(Resource.Error<String>(message = "Failed to delete account"))
            return@flow
        } catch (e: FirebaseAuthRecentLoginRequiredException) {
            Timber.wtf("Failed to delete user: Re-authentication required. Something went horribly wrong since this error should have been caught before reaching this section --> $e")
            emit(Resource.Error<String>(message = "Failed to delete account"))
            return@flow
        } catch (e: FirebaseNetworkException) {
            Timber.e("Failed to re-authenticate: Invalid credentials (password is incorrect) --> $e")
            emit(Resource.Error<String>(message = "Failed to delete account. Not connected to the internet"))
            return@flow
        } catch (e: Exception) {
            Timber.wtf("Failed to delete user account: An unexpected error occurred --> $e")
            emit(Resource.Error<String>(message = "Failed to delete account"))
            return@flow
        }

        emit(Resource.Success<String>())
    }.flowOn(Dispatchers.IO)
}