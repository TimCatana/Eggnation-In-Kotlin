package com.applicnation.eggnation.feature_eggnation.presentation.auth.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applicnation.eggnation.feature_eggnation.domain.use_case.UserUseCases
import com.applicnation.eggnation.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RegisterScreenViewModel @Inject constructor(
    private val userUseCases: UserUseCases
) : ViewModel() {

    /**
     * States:
     * - email (String)
     * - isEmailError (Boolean)
     * - password (String)
     * - isPasswordError (Boolean)
     * - confirmPassword (String)
     * - isConfirmPasswordError (Boolean)
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

    private val _confirmPasswordText = mutableStateOf("")
    val confirmPasswordText: State<String> = _confirmPasswordText

    private val _isConfirmPasswordError = mutableStateOf(true)
    val isConfirmPasswordError: State<Boolean> = _isConfirmPasswordError

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    /**
     * events
     * - Email (Text Entered)
     * - Password (Text Entered)
     * - Confirm Password (Text Entered)
     * - Sign Up (Button Clicked)
     */
    fun onEvent(event: RegisterScreenEvent) {
        when (event) {
            is RegisterScreenEvent.EnteredEmail -> {
                _emailText.value = event.value
                validateEmail()
            }
            is RegisterScreenEvent.EnteredPassword -> {
                _passwordText.value = event.value
                validatePassword()
                validateConfirmPassword()
            }
            is RegisterScreenEvent.EnteredConfirmPassword -> {
                _confirmPasswordText.value = event.value
                validateConfirmPassword()
            }
            is RegisterScreenEvent.SignUp -> {
                userUseCases.signUpUserUC(_emailText.value, _passwordText.value)
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
                                            message = result.message!! // TODO - make sure Resource error and success have required messages and pass emtpy strings when not necesary
                                        )
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
        val whiteSpaceChars = "\\s".toRegex()
        val lowerCaseChars = "[a-z]".toRegex()
        val upperCaseChars = "[A-Z]".toRegex()
        val numberChars = "\\d".toRegex()

        var isError = false

        if (_passwordText.value.length < 8) {
//            Timber.d("password error = TRUE --> password length less than 8")
            isError = true
        }

        if (_passwordText.value.contains(whiteSpaceChars)) {
//            Timber.d("password error = TRUE --> password contains whitespace")
            isError = true
        }

        if (!_passwordText.value.contains(lowerCaseChars)) {
//            Timber.d("password error = TRUE --> password does not contain lowercase letters")
            isError = true
        }

        if (!_passwordText.value.contains(upperCaseChars)) {
//            Timber.d("password error = TRUE --> password does not contain uppercase letters")
            isError = true
        }

        if (!_passwordText.value.contains(numberChars)) {
//            Timber.d("password error = TRUE --> password does not contain numbers")
            isError = true
        }

        _isPasswordError.value = isError
    }

    private fun validateConfirmPassword() {
        _isConfirmPasswordError.value = _confirmPasswordText.value != _passwordText.value
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
    }
}
