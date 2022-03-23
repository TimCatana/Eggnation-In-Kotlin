package com.applicnation.eggnation.feature_eggnation.domain.use_case.individual_use_cases.user_use_case.user_account_use_case

import com.applicnation.eggnation.feature_eggnation.domain.repository.AuthenticationRepository
import com.applicnation.eggnation.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

class SignOutUserUC @Inject constructor(
    private val authenticator: AuthenticationRepository
) {
    // TODO - clean up documentation

    /**
     * Signs the user out of their account on their given device
     * @exception Exception All exceptions thrown from this catch block are UNEXPECTED
     */
    operator fun invoke(): Flow<Resource<String>> = flow {
        emit(Resource.Loading<String>())

        try {
            authenticator.signOutUser()
            Timber.i("SUCCESS: User signed out")
            emit(Resource.Success<String>())
        } catch (e: Exception) {
            Timber.wtf("Failed to sign user out: Unexpected exception $e")
            emit(Resource.Error<String>(message = "Failed to sign out"))
        }
    }.flowOn(Dispatchers.IO)
}
