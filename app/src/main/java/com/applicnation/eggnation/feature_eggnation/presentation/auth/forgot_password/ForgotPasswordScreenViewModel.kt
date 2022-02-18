package com.applicnation.eggnation.feature_eggnation.presentation.auth.forgot_password

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
class ForgotPasswordScreenViewModel @Inject constructor(
    private val userUseCases: UserUseCases
): ViewModel() {

    /**
     * States:
     * - email (String)
     */
    private val _emailText = mutableStateOf("")
    val emailText: State<String> = _emailText

    private val _isEmailError = mutableStateOf(true)
    val isEmailError: State<Boolean> = _isEmailError

    /**
     * events
     */
    fun onEvent(event: ForgotPasswordScreenEvent) {
        when(event) {
            is ForgotPasswordScreenEvent.EnteredEmail -> {
                _emailText.value = event.value
                validateEmail()
            }
            is ForgotPasswordScreenEvent.SendResetPasswordEmail -> {
                viewModelScope.launch(Dispatchers.IO) {
                    userUseCases.sendUserPasswordResetEmailUC(event.email)
                }
                //TODO - Send reset email using Firebase Authentication
            }
        }
    }

    // TODO - this can be _isEmailError.value = !matcher logic
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
}