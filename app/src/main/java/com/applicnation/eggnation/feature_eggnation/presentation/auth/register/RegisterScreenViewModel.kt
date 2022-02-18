package com.applicnation.eggnation.feature_eggnation.presentation.auth.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applicnation.eggnation.feature_eggnation.domain.use_case.user_use_case.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

                // confirm password becomes invalid if password changes because they will not match.
                if(!_isConfirmPasswordError.value) {
                    validateConfirmPassword()
                }
            }
            is RegisterScreenEvent.EnteredConfirmPassword -> {
                _confirmPasswordText.value = event.value
                validateConfirmPassword()
            }
            is RegisterScreenEvent.SignUp -> {
                viewModelScope.launch(Dispatchers.IO) {
                    userUseCases.signUpUserUC(event.email, event.password, event.username)
                }

                // TODO - check if username is unique in firebase. but probably do this on submit

                //TODO - Add the user to Firebase Firestore
                //TODO - Check for errors along the way
            }
        }
    }



    private fun validateUsername() {
        if(_usernameText.value.length >= 5) {
            _isUsernameError.value = false
        }
    }

    private fun validateEmail() {
        if(!_isEmailError.value) {
            if(!android.util.Patterns.EMAIL_ADDRESS.matcher(_emailText.value).matches()) {
                _isEmailError.value = true
            }
        } else {
            if(android.util.Patterns.EMAIL_ADDRESS.matcher(_emailText.value).matches()) {
                _isEmailError.value = false
            }
        }
    }

    private fun validatePassword() {
        val mWhiteSpaceChars = "\\s".toRegex()
        val mLowerCaseChars = "[a-z]".toRegex()
        val mUpperCaseChars = "[A-Z]".toRegex()
        val mNumberChars = "\\d".toRegex()

        var isError = false

        if(_passwordText.value == _confirmPasswordText.value) {
            isError = true
        }

        if(_passwordText.value.length < 8) {
            isError = true
        }

        if(_passwordText.value.contains(mWhiteSpaceChars)) {
            isError = true
        }

        if(!_passwordText.value.contains(mLowerCaseChars)) {
            isError = true
        }

        if(!_passwordText.value.contains(mUpperCaseChars)) {
            isError = true
        }

        if(!_passwordText.value.contains(mNumberChars)) {
            isError = true
        }

        _isPasswordError.value = isError
    }

    private fun validateConfirmPassword() {
        if(_isPasswordError.value) {
            _isConfirmPasswordError.value = true
        } else {
            _isConfirmPasswordError.value = (_confirmPasswordText.value != _passwordText.value)
        }

    }


}