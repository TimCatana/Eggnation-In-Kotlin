package com.applicnation.eggnation.feature_eggnation.presentation.edit_settings.email_settings

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.applicnation.eggnation.feature_eggnation.domain.use_case.UserUseCases
import com.applicnation.eggnation.feature_eggnation.presentation.game.settings.SettingsScreenEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EmailSettingsScreenViewModel @Inject constructor(
    private val userUseCases: UserUseCases
) : ViewModel() {

    private val _email = mutableStateOf("")
    val email: State<String> = _email

    private val _language = mutableStateOf("")
    val language: State<String> = _language

    init{
        _email.value = userUseCases.getUserEmailUC()
    }

    fun onEvent(event: SettingsScreenEvent) {
        when (event) {
            is SettingsScreenEvent.SignOut -> {
                userUseCases.signOutUserUC()
            }
        }
    }
}