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

class ReloadUserUC @Inject constructor(
    private val authenticator: AuthenticationRepository
) {
    // TODO - clean up documentation

    /**
     * Signs the user out of their account on their given device
     * @exception Exception All exceptions thrown from this catch block are UNEXPECTED
     */
    operator fun invoke(): Flow<Resource<String>> = flow {
        emit(Resource.Loading<String>())

        // reload the new data
        try {
            authenticator.reloadUser()
            emit(Resource.Success<String>("Reloaded successfully"))
        } catch (e: Exception) {
            Timber.e("Failed to reload user--> $e")
            emit(Resource.Error<String>("Failed to update email"))
            return@flow
        }
    }.flowOn(Dispatchers.IO)
}


