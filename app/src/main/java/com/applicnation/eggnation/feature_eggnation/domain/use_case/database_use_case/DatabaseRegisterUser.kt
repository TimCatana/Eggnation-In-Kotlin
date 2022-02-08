package com.applicnation.eggnation.feature_eggnation.domain.use_case.database_use_case

import com.applicnation.eggnation.feature_eggnation.domain.modal.User
import com.applicnation.eggnation.feature_eggnation.domain.repository.FirestoreRepository
import javax.inject.Inject

class DatabaseRegisterUser @Inject constructor(
    private val repository: FirestoreRepository
) {

    suspend operator fun invoke(userId: String, userInfo: User) {
        repository.registerUser(userId, userInfo)
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