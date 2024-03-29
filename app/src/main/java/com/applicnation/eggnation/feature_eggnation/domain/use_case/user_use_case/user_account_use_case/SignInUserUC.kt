package com.applicnation.eggnation.feature_eggnation.domain.use_case.user_use_case.user_account_use_case

import com.applicnation.eggnation.feature_eggnation.domain.repository.AuthenticationRepository
import com.applicnation.eggnation.util.Resource
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

class SignInUserUC @Inject constructor(
    private val authenticator: AuthenticationRepository
) {
    /**
     * Signs the user into their account given their email and password
     * @param email The user email (should be sanitized by now)
     * @param password The user password (should be sanitized by now)
     * @exception FirebaseAuthInvalidCredentialsException
     * @exception FirebaseAuthInvalidUserException
     * @exception FirebaseNetworkException
     * @exception Exception All exceptions thrown from this catch block are UNEXPECTED
     */
    operator fun invoke(email: String, password: String): Flow<Resource<String>> = flow {
        emit(Resource.Loading<String>())

        try {
            authenticator.signInUser(email, password)
            Timber.i("SUCCESS: User signed in")
            emit(Resource.Success<String>(message = "Signed in successfully!"))
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Timber.e("Failed to sign user in: Email is invalid (not formatted correctly) --> $e")
            emit(Resource.Error<String>("Invalid Credentials!"))
        } catch (e: FirebaseAuthInvalidUserException) {
            Timber.e("Failed to sign user in: User does not exist --> $e")
            emit(Resource.Error<String>("Invalid Credentials!"))
        } catch (e: FirebaseNetworkException) {
            Timber.e("Firebase Authentication: Failed to sign user up (register): Not connected to internet --> $e")
            emit(Resource.Error<String>("Make sure you are connected to the internet"))
        } catch (e: Exception) {
            Timber.wtf("Failed to sign user in: An unexpected error occurred --> $e")
            emit(Resource.Error<String>("An unexpected error occurred"))
        }

    }.flowOn(Dispatchers.IO)
}
