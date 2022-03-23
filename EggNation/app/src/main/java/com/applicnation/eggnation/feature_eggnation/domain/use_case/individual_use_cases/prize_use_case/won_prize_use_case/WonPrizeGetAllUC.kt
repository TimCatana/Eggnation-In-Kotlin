package com.applicnation.eggnation.feature_eggnation.domain.use_case.individual_use_cases.prize_use_case.won_prize_use_case

import com.applicnation.eggnation.feature_eggnation.domain.modal.WonPrize
import com.applicnation.eggnation.feature_eggnation.domain.repository.AuthenticationRepository
import com.applicnation.eggnation.feature_eggnation.domain.repository.DatabaseRepository
import com.applicnation.eggnation.util.Resource
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class WonPrizeGetAllUC @Inject constructor(
    private val repository: DatabaseRepository,
    private val authenticationRepository: AuthenticationRepository,
) {
    // TODO - clean up documentation
    // TODO - make sure all flows emit correct values and if necessary, return@flow at correct spots

    /**
     * Gets the list of all won prizes for the user's account
     * @param userId The user's user ID (usually always from Firebase auth). documents are named after the user's uid
     * @exception FirebaseFirestoreException This is the only exception Firestore can throw as of now (according to documentation)
     * @exception Exception All exceptions thrown from this catch block are UNEXPECTED
     * // TODO - there may also be other specific exceptions thrown, but I think I only need to tell the user that it failed to fetch
     * // TODO - the specific error catch blocks are used to make debugging easier when there is a problem
     * @return ArrayList<WonPrize> The list of won prizes
     */
    operator fun invoke(): Flow<Resource<ArrayList<WonPrize>>> = flow {
        emit(Resource.Loading<ArrayList<WonPrize>>(message = ""))

        val userId = authenticationRepository.getUserId()

        if (userId == null) {
            Timber.wtf("!!!! Failed to fetch won prizes: UserId is null.")
            emit(
                Resource.Error<ArrayList<WonPrize>>(
                    data = ArrayList(),
                    message = "Failed to fetch prizes"
                )
            )
            return@flow
        }

        try {
            val prizes = repository.getAllWonPrizes(userId)
            emit(Resource.Success<ArrayList<WonPrize>>(data = prizes, message = "No prizes available")) // If prizes is empty
        } catch (e: FirebaseFirestoreException) {
            Timber.e("Failed to add prize to firestore: An unexpected FIRESTORE error occurred --> $e")
            emit(Resource.Error<ArrayList<WonPrize>>(data = ArrayList(), message = "Failed to fetch prizes"))
        } catch (e: Exception) {
            Timber.e("Failed to add prize to firestore: An unexpected error occurred --> $e")
            emit(Resource.Error<ArrayList<WonPrize>>(data = ArrayList(), message = "Failed to fetch prizes"))
        }

    }.flowOn(Dispatchers.IO)
}
