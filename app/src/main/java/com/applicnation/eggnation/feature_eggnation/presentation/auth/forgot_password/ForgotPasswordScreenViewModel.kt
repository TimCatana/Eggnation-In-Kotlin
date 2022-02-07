package com.applicnation.eggnation.feature_eggnation.presentation.auth.forgot_password

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordScreenViewModel @Inject constructor(
//    private val ResetUserPassword: ResetUserPassword TODO - make reset user password use case
): ViewModel() {

    /**
     * States:
     * - email (String)
     */
    private val _emailText = mutableStateOf("")
    val emailText: State<String> = _emailText

    /**
     * events
     */
    fun onEvent(event: ForgotPasswordScreenEvent) {
        when(event) {
            is ForgotPasswordScreenEvent.EnteredEmail -> {
                _emailText.value = event.value
            }
            is ForgotPasswordScreenEvent.ResetPassword -> {
                //TODO - Send reset email using Firebase Authentication
            }
        }
    }
}