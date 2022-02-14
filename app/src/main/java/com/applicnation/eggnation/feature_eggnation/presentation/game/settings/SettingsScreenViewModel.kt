package com.applicnation.eggnation.feature_eggnation.presentation.game.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applicnation.eggnation.feature_eggnation.domain.use_case.authentication_use_case.AuthUseCases
import com.applicnation.eggnation.feature_eggnation.domain.use_case.preference_use_case.PreferencesUseCases
import com.applicnation.eggnation.feature_eggnation.presentation.game.store.StoreScreenEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
) : ViewModel() {



    fun onEvent(event: SettingsScreenEvent) {
        when (event) {
            is SettingsScreenEvent.SignOut -> {
                    authUseCases.userSignOut()
                Log.d("rrrr", "rerererooooooooooooooooooooooo")
            }
        }
    }
}