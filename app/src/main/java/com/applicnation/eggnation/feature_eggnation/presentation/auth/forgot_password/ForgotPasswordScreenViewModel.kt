package com.applicnation.eggnation.feature_eggnation.presentation.auth.forgot_password

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applicnation.eggnation.feature_eggnation.domain.use_case.authentication_use_case.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordScreenViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
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
            is ForgotPasswordScreenEvent.SendResetPasswordEmail -> {
                viewModelScope.launch {
                    authUseCases.userPasswordReset(event.email)
                }
                //TODO - Send reset email using Firebase Authentication
            }
        }
    }
}