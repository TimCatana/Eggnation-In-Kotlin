package com.applicnation.eggnation.feature_eggnation.domain.use_case.individual_use_cases.user_use_case.game_logic_use_case

import com.applicnation.eggnation.feature_eggnation.domain.repository.AuthenticationRepository
import com.applicnation.eggnation.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class ClaimPrizeUC @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
) {
    /**
     *
     */
    operator fun invoke(): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading<Boolean>(message = ""))

        val isEmailVerified = authenticationRepository.getUserEmailVerificationStatus()

        if(isEmailVerified == null) {
            Timber.wtf("isEmailVerified is null. Something is horribly wrong since the user tried to claim a prize while not being signed in")
            emit(Resource.Error<Boolean>(message = "Something went wrong. Please sign out and sign in again."))
            return@flow
        }

        if(isEmailVerified) {
            emit(Resource.Success<Boolean>(data = true, message = "email is verified, good to go"))
        } else {
            emit(Resource.Success<Boolean>(data = false, message = "You need to verify your email in settings before you can claim a prize"))
        }
    }//.flowOn(Dispatchers.IO) // TODO - I can keep this on the main dispacher (get rid of the flowOn)
}
