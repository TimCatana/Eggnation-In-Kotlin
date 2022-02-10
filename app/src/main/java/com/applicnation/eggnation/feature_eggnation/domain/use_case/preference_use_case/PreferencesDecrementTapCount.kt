package com.applicnation.eggnation.feature_eggnation.domain.use_case.preference_use_case

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.applicnation.eggnation.feature_eggnation.data.local.PreferencesManager
import com.applicnation.eggnation.feature_eggnation.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferencesDecrementTapCount @Inject constructor(
    private val preferencesManager: PreferencesRepository

) {

    // TODO - I want to have a specific decrement tap counter function that relied on the stored value to decrement. This ensures a single source of truth
    suspend operator fun invoke(tapCount: Int) {
//        preferencesManager.decrementTapCounter()
        if(tapCount > 0) {
            preferencesManager.updateTapCount(tapCount - 1)
        }

        /**
         * Below is to set error bounds
         */
        if(tapCount < 0) {
            preferencesManager.updateTapCount(0)
        }
        if(tapCount > 1000) {
            preferencesManager.updateTapCount(1000)

        }

    }
}