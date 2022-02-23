package com.applicnation.eggnation.feature_eggnation.presentation.game.settings

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.applicnation.eggnation.feature_eggnation.domain.repository.AuthenticationRepository
import com.applicnation.eggnation.feature_eggnation.domain.use_case.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(
    private val userUseCases: UserUseCases
) : ViewModel() {

    private val _username = mutableStateOf("")
    val username: State<String> = _username

    private val _email = mutableStateOf("")
    val email: State<String> = _email

    private val _language = mutableStateOf("")
    val language: State<String> = _language

    init{
        _username.value = userUseCases.getUserUsernameUC()
        _email.value = userUseCases.getUserEmailUC()
        _language.value = "EN"
    }

    fun onEvent(event: SettingsScreenEvent) {
        when (event) {
            is SettingsScreenEvent.SignOut -> {
                userUseCases.signOutUserUC()
            }
        }
    }
}