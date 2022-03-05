package com.applicnation.eggnation.feature_eggnation.domain.use_case.prize_use_case.available_prize_use_case

import com.applicnation.eggnation.feature_eggnation.domain.modal.AvailablePrize
import com.applicnation.eggnation.feature_eggnation.domain.repository.DatabaseRepository
import com.applicnation.eggnation.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

class AvailablePrizeGetAllUC @Inject constructor(
    private val repository: DatabaseRepository
) {

    /**
     * @importantNote Firestore would be more convenient, see the note on getAvailablePrizeByRNG for why I am using realtime database (it is a financial decision)
     * Gets the list of all currently availablePrizes
     * @exception Exception All exceptions thrown from this catch block are UNEXPECTED
     */
    operator fun invoke(): Flow<Resource<ArrayList<AvailablePrize>>> = flow {
        emit(Resource.Loading<ArrayList<AvailablePrize>>())

        try{
            val prizes = repository.getAllAvailablePrizes()
            emit(Resource.Success<ArrayList<AvailablePrize>>(data = prizes, message = "No prizes available")) // If prizes is empty
        } catch (e: Exception) {
            Timber.e("Failed to fetch available prizes from realtime database: An unexpected error occurred --> $e")
            emit(Resource.Error<ArrayList<AvailablePrize>>(data = ArrayList(), message = "Failed to fetch prizes"))
        }
    }.flowOn(Dispatchers.IO)
}
