package com.applicnation.eggnation.feature_eggnation.presentation.game.store

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applicnation.eggnation.feature_eggnation.domain.use_case.preference_use_case.PreferencesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoreScreenViewModel @Inject constructor(
    private val preferencesUseCases: PreferencesUseCases
) : ViewModel() {

    fun onEvent(event: StoreScreenEvent) {
        when (event) {
            is StoreScreenEvent.SelectSkin -> {
                viewModelScope.launch {
                    preferencesUseCases.preferencesUpdateSelectedSkin(event.skin)
                }
            }
        }
    }

}