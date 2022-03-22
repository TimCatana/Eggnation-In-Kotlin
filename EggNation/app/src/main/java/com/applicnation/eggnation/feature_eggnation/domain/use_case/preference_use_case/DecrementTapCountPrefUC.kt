package com.applicnation.eggnation.feature_eggnation.domain.use_case.preference_use_case

import com.applicnation.eggnation.feature_eggnation.domain.repository.PreferencesRepository
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class DecrementTapCountPrefUC @Inject constructor(
    private val preferencesManager: PreferencesRepository

) {

    /**
     * TODO - add documentation
     */
    suspend operator fun invoke(tapCount: Int): Boolean {

        try {
            if (tapCount in 1..1000) {
                preferencesManager.updateTapCount(tapCount - 1)
            }

            // Potential Error Bounds
            if (tapCount < 0) {
                preferencesManager.updateTapCount(0)
            }
            if (tapCount > 1000) {
                preferencesManager.updateTapCount(1000)
            }

            return true
        } catch (e: Exception) {
            Timber.e("Failed to update counter")
            return false
        }


        // TODO - in the future, I want something like this. But I can't figure out the scoping
//        preferencesManager.getTapCount().map {
//            if (it in 1..1000) {
//                preferencesManager.updateTapCount(tapCount - 1)
//            }
//            /**
//             * Below is to set error bounds
//             */
//            if (it < 0) {
//                preferencesManager.updateTapCount(0)
//            }
//            if (it > 1000) {
//                preferencesManager.updateTapCount(1000)
//            }
//        }
    }
}
