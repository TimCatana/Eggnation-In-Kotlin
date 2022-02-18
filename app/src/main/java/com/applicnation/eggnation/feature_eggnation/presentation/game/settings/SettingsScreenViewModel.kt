package com.applicnation.eggnation.feature_eggnation.presentation.game.settings

import androidx.lifecycle.ViewModel
import com.applicnation.eggnation.feature_eggnation.domain.use_case.user_use_case.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(
    private val userUseCases: UserUseCases
) : ViewModel() {



    fun onEvent(event: SettingsScreenEvent) {
        when (event) {
            is SettingsScreenEvent.SignOut -> {
                userUseCases.signOutUserUC()
            }
        }
    }
}