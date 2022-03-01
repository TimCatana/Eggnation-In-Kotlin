package com.applicnation.eggnation.feature_eggnation.presentation.edit_settings.edit_username


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
import javax.inject.Inject


@HiltViewModel
class EditUsernameScreenViewModel @Inject constructor(
    private val userUseCases: UserUseCases
) : ViewModel() {

    /**
     * States:
     * - email (String)
     */
    private val _usernameText = mutableStateOf("")
    val usernameText: State<String> = _usernameText

    private val _isUsernameError = mutableStateOf(true)
    val isUsernameError: State<Boolean> = _isUsernameError

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    /**
     * events
     */
    fun onEvent(event: EditUsernameScreenEvent) {
        when (event) {
            is EditUsernameScreenEvent.EnteredUsername -> {
                _usernameText.value = event.value
                validateUsername()
            }
            is EditUsernameScreenEvent.UpdateUsername -> {
                userUseCases.updateUserUsernameUC(newUsername = event.newUsername)
                    .onEach { result ->
                        when (result) {
                            is Resource.Loading -> {
                                _isLoading.value = true
                            }
                            is Resource.Success -> {
                                _isLoading.value = false
                                _eventFlow.emit(
                                    UiEvent.ShowSnackbar(
                                        message = result.message ?: "Username updated successfully"
                                    )
                                )
                            }
                            is Resource.Error -> {
                                _isLoading.value = false
                                _eventFlow.emit(
                                    UiEvent.ShowSnackbar(
                                        message = result.message ?: "Username failed to update"
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

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
    }
}