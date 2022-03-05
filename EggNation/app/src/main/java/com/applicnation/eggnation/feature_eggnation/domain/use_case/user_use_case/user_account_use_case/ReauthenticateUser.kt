package com.applicnation.eggnation.feature_eggnation.domain.use_case.user_use_case.user_account_use_case

import com.applicnation.eggnation.feature_eggnation.domain.repository.AuthenticationRepository
import com.applicnation.eggnation.util.Resource
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class ReauthenticateUser @Inject constructor(
    private val authenticator: AuthenticationRepository
) {
    // TODO - clean up documentation

    /**
     * Signs the user out of their account on their given device
     * @exception Exception All exceptions thrown from this catch block are UNEXPECTED
     */
    operator fun invoke(userPassword: String): Flow<Resource<String>> = flow {
        emit(Resource.Loading<String>())

        val userEmail = authenticator.getUserEmail()

        if(userEmail == null) {
            // TODO - sign out and tell the user something went horribly wrong and they need to sign in
            Timber.wtf("!!!! Failed to reauthenticate. User email is null. Something went horribly wrong")
            emit(Resource.Error<String>(message = "Something went horribly wrong"))
            return@flow
        }

        try {
            authenticator.authenticateUser(userEmail, userPassword)
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
    }.flowOn(Dispatchers.IO)
}
