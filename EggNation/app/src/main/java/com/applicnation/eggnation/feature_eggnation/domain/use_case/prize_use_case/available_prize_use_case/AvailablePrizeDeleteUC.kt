package com.applicnation.eggnation.feature_eggnation.domain.use_case.prize_use_case.available_prize_use_case

import com.applicnation.eggnation.feature_eggnation.domain.repository.DatabaseRepository
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class AvailablePrizeDeleteUC @Inject constructor(
    private val repository: DatabaseRepository
) {

    suspend operator fun invoke(rng: String): Boolean {
        try {
            repository.deleteAvailablePrize(rng)
            return true
        } catch (e: Exception) {
            Timber.e("Failed to delete prize from realtime Database --> $e")
            return false
        }
    }
}