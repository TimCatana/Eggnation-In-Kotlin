package com.applicnation.eggnation.feature_eggnation.presentation.auth.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applicnation.eggnation.feature_eggnation.domain.use_case.user_use_case.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

// private cal savedStateHandle: SavedStateHandle - contains navigation parameters

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val userUseCases: UserUseCases
) : ViewModel() {

    /**
     * States:
     * - email (String)
     * - password (String)
     */
    private val _emailText = mutableStateOf("")
    val emailText: State<String> = _emailText

    private val _isEmailError = mutableStateOf(true)
    val isEmailError: State<Boolean> = _isEmailError

    private val _passwordText = mutableStateOf("")
    val passwordText: State<String> = _passwordText

    private val _isPasswordError = mutableStateOf(true)
    val isPasswordError: State<Boolean> = _isPasswordError

    /**
     * events
     */
    fun onEvent(event: LoginScreenEvent) {
        when (event) {
            is LoginScreenEvent.EnteredEmail -> {
                _emailText.value = event.value
                validateEmail()
            }
            is LoginScreenEvent.EnteredPassword -> {
                _passwordText.value = event.value
                validatePassword()
            }
            is LoginScreenEvent.SignIn -> {
                viewModelScope.launch(Dispatchers.IO) {
                    userUseCases.signInUserUC(event.email, event.password)
                    // TODO - need to propogate potential errors somehow
                }
            }
        }
    }

    private fun validateEmail() {
        _isEmailError.value = !android.util.Patterns.EMAIL_ADDRESS.matcher(_emailText.value).matches()
    }

    private fun validatePassword() {
        _isPasswordError.value = _passwordText.value.isEmpty()
    }

}


//    init {
//        getPrizes()
//    }

//    private val _state = mutableStateOf(StateClass())
//    val state: State<LoginScreenState> = _state

//    private fun registerUser() {
//        registerUserUseCase()
//    }

//    private fun getePrizes() {
//        getPrizesUseCase().onEach { result ->
//    when(result) {
//        is Resource.Success -> {
////          _state.value = LoginScreenState(prizes = result.data ?: emptyList())
//        }
//        is Resource.Error -> {
////          _state.value = LoginScreenState(error = result.message ?: "An unexpected error occured")
//        }
//        is Resource.Loading -> {
//          _state.value = LoginScreenState(isloading = true)
//        }
//    }
//    }.launchIn(viewModelScope) // launch a coroutine in viewmodel scope


//@Composable
//fun Screen(
//    viewModel: LoginScreenViewModel = hiltViewModel()
//) {
//    val state = viewModel.state.value // accesses teh state var holds the model to the state. Now you have access to the state in the UI
//    Box{
//
//    }
//}