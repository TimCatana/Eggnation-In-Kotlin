package com.applicnation.eggnation.feature_eggnation.presentation.auth.forgot_password

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applicnation.eggnation.feature_eggnation.domain.use_case.screen_use_cases.auth.ForgotPasswordScreenUseCases
import com.applicnation.eggnation.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordScreenViewModel @Inject constructor(
    private val forgotPasswordScreenUseCases: ForgotPasswordScreenUseCases
) : ViewModel() {

    /**
     * States:
     * - email (String)
     * - isEmailError (Boolean)
     * - isLoading (Boolean)
     * - eventFlow (Flow)
     */
    private val _emailText = mutableStateOf("")
    val emailText: State<String> = _emailText

    private val _isEmailError = mutableStateOf(true)
    val isEmailError: State<Boolean> = _isEmailError

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    /**
     * events
     * - Email (Text Entered)
     * - Send Reset Password Email (Button Clicked)
     */
    fun onEvent(event: ForgotPasswordScreenEvent) {
        when (event) {
            is ForgotPasswordScreenEvent.EnteredEmail -> {
                _emailText.value = event.value
                validateEmail()
            }
            is ForgotPasswordScreenEvent.SendResetPasswordEmail -> {
                forgotPasswordScreenUseCases.sendUserPasswordResetEmailUC(_emailText.value)
                    .onEach { result ->
                        when (result) {
                            is Resource.Loading -> {
                                _isLoading.value = true
                            }
                            is Resource.Success -> {
                                _isLoading.value = false
                                _eventFlow.emit(
                                    UiEvent.ShowSuccessSnackBar(message = result.message)
                                )
                            }
                            is Resource.Error -> {
                                _isLoading.value = false
                                _eventFlow.emit(
                                    UiEvent.ShowErrorSnackBar(message = result.message)
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

    sealed class UiEvent {
        data class ShowErrorSnackBar(val message: String) : UiEvent()
        data class ShowSuccessSnackBar(val message: String) : UiEvent()
    }
}
