package com.applicnation.eggnation.feature_eggnation.presentation.auth.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applicnation.eggnation.feature_eggnation.domain.use_case.screen_use_cases.auth.LoginScreenUseCases
import com.applicnation.eggnation.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val loginScreenUseCases: LoginScreenUseCases
) : ViewModel() {

    /**
     * States:
     * - email (String)
     * - isEmailError(Boolean)
     * - password (String)
     * - isPasswordError (Boolean)
     * - isLoading (Boolean)
     * - eventFlow (Flow)
     */
    private val _emailText = mutableStateOf("")
    val emailText: State<String> = _emailText

    private val _isEmailError = mutableStateOf(true)
    val isEmailError: State<Boolean> = _isEmailError

    private val _passwordText = mutableStateOf("")
    val passwordText: State<String> = _passwordText

    private val _isPasswordError = mutableStateOf(true)
    val isPasswordError: State<Boolean> = _isPasswordError

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    /**
     * Events:
     * - Email (Text Entered)
     * - Password (Text Entered)
     * - Sign In (Button Clicked)
     */
    fun onEvent(event: LoginScreenEvent) {
        when (event) {
            is LoginScreenEvent.EnteredEmail -> {
                _emailText.value = event.value
                validateEmail()
            }
            is LoginScreenEvent.EnteredPassword -> {
                _passwordText.value = event.value
                validatePassword()
            }
            is LoginScreenEvent.SignIn -> {
                loginScreenUseCases.signInUserUC(_emailText.value, _passwordText.value)
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
                                    UiEvent.ShowSnackBar(message = result.message)
                                )
                            }
                        }
                    }.launchIn(viewModelScope)
            }
        }
    }

    private fun validateEmail() {
        _isEmailError.value =
            !android.util.Patterns.EMAIL_ADDRESS.matcher(_emailText.value).matches()
    }

    private fun validatePassword() {
        _isPasswordError.value = _passwordText.value.isEmpty()
    }

    sealed class UiEvent {
        data class ShowSnackBar(val message: String) : UiEvent()
    }
}
