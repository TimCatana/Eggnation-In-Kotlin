package com.applicnation.eggnation.feature_eggnation.presentation.auth.register

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applicnation.eggnation.feature_eggnation.domain.use_case.authentication_use_case.AuthUseCases
import com.applicnation.eggnation.feature_eggnation.domain.use_case.authentication_use_case.UserSignUp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterScreenViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
): ViewModel() {

    /**
     * States:
     * - username (String)
     * - email (String)
     * - password (String)
     * - confirmPassword (String)
     */
    private val _usernameText = mutableStateOf("")
    val usernameText: State<String> = _usernameText

    private val _emailText = mutableStateOf("")
    val emailText: State<String> = _emailText

    private val _passwordText = mutableStateOf("")
    val passwordText: State<String> = _passwordText

    private val _confirmPasswordText = mutableStateOf("")
    val confirmPasswordText: State<String> = _confirmPasswordText

    /**
     * events
     */
    fun onEvent(event: RegisterScreenEvent) {
        when(event) {
            is RegisterScreenEvent.EnteredUsername -> {
                _usernameText.value = event.value
            }
            is RegisterScreenEvent.EnteredEmail -> {
                _emailText.value = event.value
            }
            is RegisterScreenEvent.EnteredPassword -> {
                _passwordText.value = event.value
            }
            is RegisterScreenEvent.EnteredConfirmPassword -> {
                _confirmPasswordText.value = event.value
            }
            is RegisterScreenEvent.SignUp -> {
                viewModelScope.launch {
                    Log.i("signUp", "in viewmodel function: ${event.email}")
                    authUseCases.userSignUp(email = event.email, password = event.password)
                }

                //TODO - Add the user to Firebase Firestore
                //TODO - Check for errors along the way
            }
        }
    }


}