package com.applicnation.eggnation.feature_eggnation.presentation.game.settings

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applicnation.eggnation.feature_eggnation.domain.repository.AuthenticationRepository
import com.applicnation.eggnation.feature_eggnation.domain.use_case.UserUseCases
import com.applicnation.eggnation.feature_eggnation.presentation.auth.login.LoginScreenEvent
import com.applicnation.eggnation.feature_eggnation.presentation.auth.register.RegisterScreenViewModel
import com.applicnation.eggnation.feature_eggnation.presentation.navigation.AuthScreen
import com.applicnation.eggnation.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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

    private val _passwordText = mutableStateOf("")
    val passwordText: State<String> = _passwordText

    private val _screenToNavTo = mutableStateOf("")
    val screenToNavTo: State<String> = _screenToNavTo

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _isPasswordModelShowing = mutableStateOf(false)
    val  isPasswordModelShowing: State<Boolean> =  _isPasswordModelShowing

    init{
        _username.value = userUseCases.getUserUsernameUC()
        _email.value = userUseCases.getUserEmailUC()
        _language.value = "EN"
    }

    fun onEvent(event: SettingsScreenEvent) {
        when (event) {
            is SettingsScreenEvent.SignOut -> {
                userUseCases.signOutUserUC()
                    .onEach { result ->
                        when (result) {
                            is Resource.Loading -> {
                                _isLoading.value = true
                            }
                            is Resource.Success -> {
                                _isLoading.value = false
                                _eventFlow.emit(
                                    UiEvent.GoToLoginScreen
                                )
                            }
                            is Resource.Error -> {
                                _isLoading.value = false
                                _eventFlow.emit(
                                    UiEvent.ShowSnackbar(
                                        message = result.message!!
                                    )
                                )
                            }
                        }
                    }.launchIn(viewModelScope)
            }
            is SettingsScreenEvent.EditProfile -> {
                userUseCases.reauthenticateUser(event.password)
                    .onEach { result ->
                        when (result) {
                            is Resource.Loading -> {
                                _isLoading.value = true
                            }
                            is Resource.Success -> {
                                _isLoading.value = false
                                _eventFlow.emit(
                                    UiEvent.ChangeStacks(_screenToNavTo.value)
                                )
                            }
                            is Resource.Error -> {
                                _isLoading.value = false
                                _eventFlow.emit(
                                    UiEvent.ShowSnackbar(
                                        message = result.message!!
                                    )
                                )
                            }
                        }
                    }.launchIn(viewModelScope)
            }
            is SettingsScreenEvent.ShowPasswordModel -> {
                _isPasswordModelShowing.value = event.showPasswordModel
                _screenToNavTo.value = event.navScreen
            }
            is SettingsScreenEvent.EnteredPassword -> {
                _passwordText.value = event.value
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        data class ChangeStacks(val screen: String) : UiEvent()
        object GoToLoginScreen: UiEvent()
    }
}