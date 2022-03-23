package com.applicnation.eggnation.feature_eggnation.presentation.edit_settings.edit_password


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applicnation.eggnation.feature_eggnation.domain.use_case.screen_use_cases.edit_settings.EditPasswordScreenUseCases
import com.applicnation.eggnation.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class EditPasswordScreenViewModel @Inject constructor(
    private val editPasswordScreenUseCases: EditPasswordScreenUseCases
) : ViewModel() {

    /**
     * States:
     * - password (String)
     * - isPasswordError (Boolean)
     * - confirmPassword (String)
     * - isConfirmPasswordError (Boolean)
     * - isPasswordModalShowing (Boolean)
     * - validationPassword (String)
     * - isLoading (Boolean)
     * - eventFlow (Flow)
     */
    private val _passwordText = mutableStateOf("")
    val passwordText: State<String> = _passwordText

    private val _isPasswordError = mutableStateOf(true)
    val isPasswordError: State<Boolean> = _isPasswordError

    private val _confirmPasswordText = mutableStateOf("")
    val confirmPasswordText: State<String> = _confirmPasswordText

    private val _isConfirmPasswordError = mutableStateOf(true)
    val isConfirmPasswordError: State<Boolean> = _isConfirmPasswordError

    private val _isPasswordModelShowing = mutableStateOf(false)
    val isPasswordModelShowing: State<Boolean> = _isPasswordModelShowing

    private val _validationPasswordText = mutableStateOf("")
    val validationPasswordText: State<String> = _validationPasswordText

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    /**
     * events
     * - Password (Text Entered)
     * - ConfirmPassword (Text Entered)
     * - ValidationPassword (TextEntered)
     * - ShowPasswordModal (Button Clicked)
     * - HidePasswordModal (Button Clicked)
     * - Update Password (Button Clicked)
     */
    fun onEvent(event: EditPasswordScreenEvent) {
        when (event) {
            is EditPasswordScreenEvent.EnteredPassword -> {
                _passwordText.value = event.value
                validatePassword()
                validateConfirmPassword()
            }
            is EditPasswordScreenEvent.EnteredConfirmPassword -> {
                _confirmPasswordText.value = event.value
                validateConfirmPassword()
            }
            is EditPasswordScreenEvent.EnteredValidationPassword -> {
                _validationPasswordText.value = event.value
            }
            is EditPasswordScreenEvent.ShowPasswordModel -> {
                _isPasswordModelShowing.value = true
            }
            is EditPasswordScreenEvent.HidePasswordModel -> {
                _isPasswordModelShowing.value = false
            }
            is EditPasswordScreenEvent.UpdatePassword -> {
                editPasswordScreenUseCases.updateUserPasswordUC(
                    password = _validationPasswordText.value,
                    newPassword = _passwordText.value
                )
                    .onEach { result ->
                        when (result) {
                            is Resource.Loading -> {
                                _isLoading.value = true
                            }
                            is Resource.Success -> {
                                _isLoading.value = false
                                _eventFlow.emit(UiEvent.ChangeStacks)
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

    private fun validatePassword() {
        val whiteSpaceChars = "\\s".toRegex()
        val lowerCaseChars = "[a-z]".toRegex()
        val upperCaseChars = "[A-Z]".toRegex()
        val numberChars = "\\d".toRegex()

        var isError = false

        if (_passwordText.value.length < 8) {
            isError = true
        }

        if (_passwordText.value.contains(whiteSpaceChars)) {
            isError = true
        }

        if (!_passwordText.value.contains(lowerCaseChars)) {
            isError = true
        }

        if (!_passwordText.value.contains(upperCaseChars)) {
            isError = true
        }

        if (!_passwordText.value.contains(numberChars)) {
            isError = true
        }

        _isPasswordError.value = isError
    }

    private fun validateConfirmPassword() {
        _isConfirmPasswordError.value = _confirmPasswordText.value != _passwordText.value
    }

    sealed class UiEvent {
        data class ShowSnackBar(val message: String) : UiEvent()
        object ChangeStacks : UiEvent()
    }
}
