package com.applicnation.eggnation.feature_eggnation.presentation.game.settings

import androidx.lifecycle.ViewModel
import com.applicnation.eggnation.feature_eggnation.domain.use_case.authentication_use_case.AllAuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(
    private val authUseCases: AllAuthUseCases
) : ViewModel() {



    fun onEvent(event: SettingsScreenEvent) {
        when (event) {
            is SettingsScreenEvent.SignOut -> {
                    authUseCases.signOutUserUC()
            }
        }
    }
}