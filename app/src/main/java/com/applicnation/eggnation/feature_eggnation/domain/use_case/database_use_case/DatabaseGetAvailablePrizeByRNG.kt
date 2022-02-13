package com.applicnation.eggnation.feature_eggnation.domain.use_case.database_use_case

import com.applicnation.eggnation.feature_eggnation.domain.modal.AvailablePrize
import com.applicnation.eggnation.feature_eggnation.domain.repository.DatabaseRepository

class DatabaseGetAvailablePrizeByRNG(
    private val repository: DatabaseRepository
) {

    suspend operator fun invoke(rng: String): AvailablePrize {
        return repository.getAvailablePrizeByRNG(rng)
    }

    // TODO - this is the structure for getting data
//    operator fun invoke(): Flow<Resource<List<Prize>>> = flow {
//        try {
//            emit(Resource.Loading())
//            val prizes = repository.getPrizes()
//            emit(Resource.Success(prizes))
//        } catch (e: Exception) {
//            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
//        }
//    }
}