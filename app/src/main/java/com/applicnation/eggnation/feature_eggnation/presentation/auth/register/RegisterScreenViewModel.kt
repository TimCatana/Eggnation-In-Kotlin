package com.applicnation.eggnation.feature_eggnation.presentation.auth.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applicnation.eggnation.feature_eggnation.domain.use_case.user_use_case.UserUseCases
import com.applicnation.eggnation.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RegisterScreenViewModel @Inject constructor(
    private val userUseCases: UserUseCases
) : ViewModel() {

    /**
     * States:
     * - username (String)
     * - email (String)
     * - password (String)
     * - confirmPassword (String)
     */
    private val _usernameText = mutableStateOf("")
    val usernameText: State<String> = _usernameText

    private val _isUsernameError = mutableStateOf(true)
    val isUsernameError: State<Boolean> = _isUsernameError

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

    private val _signUpResultStatus = mutableStateOf("")
    val signUpResultStatus: State<String> = _signUpResultStatus

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    // TODO - add isLoading state

    /**
     * events
     */
    fun onEvent(event: RegisterScreenEvent) {
        when (event) {
            is RegisterScreenEvent.EnteredUsername -> {
                _usernameText.value = event.value
                validateUsername()
            }
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
                // TODO - check if username is unique in firebase database here
                userUseCases.signUpUserUC(event.email, event.password, event.username)
                    .onEach { result ->
                        when (result) {
                            is Resource.Loading -> {
                                _isLoading.value = true
                            }
                            is Resource.Success -> {
                                _isLoading.value = false
                                _eventFlow.emit(
                                    UiEvent.Login
                                )
                            }
                            is Resource.Error -> {
                                _signUpResultStatus.value = result.message!!
                                _isLoading.value = false
                                _eventFlow.emit(
                                    UiEvent.ShowSnackbar(
                                        message = result.message
                                    )
                                )
                            }
                        }
                    }.launchIn(viewModelScope)
            }
        }
    }


    private fun validateUsername() {
        _isUsernameError.value = _usernameText.value.length < 5
    }

    private fun validateEmail() {
        _isEmailError.value =
            !android.util.Patterns.EMAIL_ADDRESS.matcher(_emailText.value).matches()
    }

    private fun validatePassword() {
        val mWhiteSpaceChars = "\\s".toRegex()
        val mLowerCaseChars = "[a-z]".toRegex()
        val mUpperCaseChars = "[A-Z]".toRegex()
        val mNumberChars = "\\d".toRegex()

        var isError = false

        if (_passwordText.value.length < 8) {
            Timber.d("password error = TRUE --> password length less than 8")
            isError = true
        }

        if (_passwordText.value.contains(mWhiteSpaceChars)) {
            Timber.d("password error = TRUE --> password contains whitespace")
            isError = true
        }

        if (!_passwordText.value.contains(mLowerCaseChars)) {
            Timber.d("password error = TRUE --> password does not contain lowercase letters")
            isError = true
        }

        if (!_passwordText.value.contains(mUpperCaseChars)) {
            Timber.d("password error = TRUE --> password does not contain uppercase letters")
            isError = true
        }

        if (!_passwordText.value.contains(mNumberChars)) {
            Timber.d("password error = TRUE --> password does not contain numbers")
            isError = true
        }

        _isPasswordError.value = isError
    }

    private fun validateConfirmPassword() {
        _isConfirmPasswordError.value = _confirmPasswordText.value != _passwordText.value
    }


    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        object Login : UiEvent()
    }


}