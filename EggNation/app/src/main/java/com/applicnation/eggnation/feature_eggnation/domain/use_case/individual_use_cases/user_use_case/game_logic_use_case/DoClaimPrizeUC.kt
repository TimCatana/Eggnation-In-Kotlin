package com.applicnation.eggnation.feature_eggnation.domain.use_case.individual_use_cases.user_use_case.game_logic_use_case

import com.applicnation.eggnation.feature_eggnation.domain.repository.AuthenticationRepository
import com.applicnation.eggnation.feature_eggnation.domain.repository.DatabaseRepository
import com.applicnation.eggnation.feature_eggnation.domain.repository.FunctionsRepository
import com.applicnation.eggnation.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

class DoClaimPrizeUC @Inject constructor(
    private val functionsRepository: FunctionsRepository,
    private val authenticationRepository: AuthenticationRepository,
    private val databaseRepository: DatabaseRepository,
) {
    /**
     *
     */
    operator fun invoke(
        prizeId: String,
        country: String,
        region: String,
        address: String
    ): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading<Boolean>())

        // TODO - check if prizeclaimed is true? if so, do not claim prize

        Timber.i("In doclaimprizeUC with $prizeId $country $region and $address")

        val email = authenticationRepository.getUserEmail()
        val userId = authenticationRepository.getUserId()

        if (email == null || userId == null) {
            Timber.wtf("!!!!User email or id is nul., problem")
            emit(Resource.Error<Boolean>(message = "Failed to claim prize"))
            return@flow
        }

//        try {
//            functionsRepository.sendMeClaimPrizeEmail(prizeId, email, country, region, address)
//        } catch (e: Exception) {
//            Timber.e("Failed to send email --> $e")
//            emit(Resource.Error<Boolean>(message = "Failed to claim prize"))
//            return@flow
//        }

        try {
            databaseRepository.updateWonPrizeClaimed(userId, prizeId, true)
        } catch (e: Exception) {
            Timber.e("Failed to update prizeClaimed Status --> $e")
            emit(Resource.Error<Boolean>(message = "Failed to claim prize"))
            return@flow
        }

        emit(Resource.Success<Boolean>(message = "Prize claimed! We will contact you shortly via email to confirm details"))
    }.flowOn(Dispatchers.IO) // TODO - I can keep this on the main dispacher (get rid of the flowOn)
}