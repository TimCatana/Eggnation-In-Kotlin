package com.applicnation.eggnation.feature_eggnation.presentation.edit_settings.edit_email

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applicnation.eggnation.feature_eggnation.domain.use_case.screen_use_cases.edit_settings.EditEmailScreenUseCases
import com.applicnation.eggnation.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class EditEmailScreenViewModel @Inject constructor(
    private val editEmailScreenUseCases: EditEmailScreenUseCases
) : ViewModel() {

    /**
     * States:
     * - email (String)
     * - isEmailError (Boolean)
     * - password (String)
     * - isPasswordModalShowing (Boolean)
     * - isLoading (Boolean)
     * - eventFlow (Flow)
     */
    private val _emailText = mutableStateOf("")
    val emailText: State<String> = _emailText

    private val _isEmailError = mutableStateOf(true)
    val isEmailError: State<Boolean> = _isEmailError

    private val _passwordText = mutableStateOf("")
    val passwordText: State<String> = _passwordText

    private val _isPasswordModelShowing = mutableStateOf(false)
    val isPasswordModelShowing: State<Boolean> = _isPasswordModelShowing

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    /**
     * events
     * - Email (Text Entered)
     * - Password (Text Entered)
     * - ShowPasswordModal (Button Clicked)
     * - HidePasswordModal (Button Clicked)
     * - UpdateEmail (Button Clicked)
     */
    fun onEvent(event: EditEmailScreenEvent) {
        when (event) {
            is EditEmailScreenEvent.EnteredEmail -> {
                _emailText.value = event.value
                validateEmail()
            }
            is EditEmailScreenEvent.EnteredPassword -> {
                _passwordText.value = event.value
            }
            is EditEmailScreenEvent.ShowPasswordModal -> {
                _isPasswordModelShowing.value = true
            }
            is EditEmailScreenEvent.HidePasswordModal -> {
                _isPasswordModelShowing.value = false
            }
            is EditEmailScreenEvent.UpdateEmail -> {
                editEmailScreenUseCases.updateUserEmailAddressUC(
                    password = _passwordText.value,
                    newEmail = _emailText.value
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

    private fun validateEmail() {
        _isEmailError.value =
            !android.util.Patterns.EMAIL_ADDRESS.matcher(_emailText.value).matches()
    }

    sealed class UiEvent {
        data class ShowSnackBar(val message: String) : UiEvent()
        object ChangeStacks : UiEvent()
    }
}
