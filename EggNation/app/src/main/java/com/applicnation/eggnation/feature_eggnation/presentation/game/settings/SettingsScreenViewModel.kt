package com.applicnation.eggnation.feature_eggnation.presentation.game.settings

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applicnation.eggnation.feature_eggnation.domain.use_case.screen_use_cases.game.SettingsScreenUseCases
import com.applicnation.eggnation.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(
private val settingsScreenUseCases: SettingsScreenUseCases
) : ViewModel() {

    /**
     * States:
     * - email (String)
     * - languate (String)
     * - password (String)
     * - isPasswordError (Boolean)
     * - isLoading (Boolean)
     * - eventFlow (Flow)
     */
    private val _email = mutableStateOf("")
    val email: State<String> = _email

    private val _isEmailVerified = mutableStateOf(false)
    val isEmailVerified: State<Boolean> = _isEmailVerified

    private val _language = mutableStateOf("")
    val language: State<String> = _language

    private val _passwordText = mutableStateOf("")
    val passwordText: State<String> = _passwordText

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _isInit = mutableStateOf(false)
    val isInit: State<Boolean> = _isInit

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _isPasswordModelShowing = mutableStateOf(false)
    val isPasswordModelShowing: State<Boolean> = _isPasswordModelShowing

    init {
        viewModelScope.launch {
            _isInit.value = true
            settingsScreenUseCases.reloadUserUC()
            _email.value = settingsScreenUseCases.getUserEmailUC()
            _isEmailVerified.value = settingsScreenUseCases.getUserEmailVerificationStatusUC()
            _language.value = "EN"
            _isInit.value = false
        }
    }

    fun onEvent(event: SettingsScreenEvent) {
        when (event) {
            is SettingsScreenEvent.ShowPasswordModel -> {
                _isPasswordModelShowing.value = event.showPasswordModel
            }
            is SettingsScreenEvent.EnteredPassword -> {
                _passwordText.value = event.value
            }
            is SettingsScreenEvent.SignOut -> {
                settingsScreenUseCases.signOutUserUC()
                    .onEach { result ->
                        when (result) {
                            is Resource.Loading -> {
                                _isLoading.value = true
                            }
                            is Resource.Success -> {
                                _isLoading.value = false
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
            is SettingsScreenEvent.DeleteAccount -> {
                settingsScreenUseCases.deleteUserUC(event.password).onEach { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _isLoading.value = true
                        }
                        is Resource.Success -> {
                            _isLoading.value = false
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
            is SettingsScreenEvent.SendVerificationEmail -> {
                settingsScreenUseCases.sendUserVerificationEmailUC().onEach { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _isLoading.value = true
                        }
                        is Resource.Success -> {
                            _isLoading.value = false
                            _eventFlow.emit(
                                UiEvent.ShowSnackbar(
                                    message = result.message!!
                                )
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
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
    }
}