package com.applicnation.eggnation.feature_eggnation.presentation.game.won_prizes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applicnation.eggnation.feature_eggnation.domain.modal.WonPrize
import com.applicnation.eggnation.feature_eggnation.domain.repository.AuthenticationRepository
import com.applicnation.eggnation.feature_eggnation.domain.use_case.MainGameLogicUseCases
import com.applicnation.eggnation.feature_eggnation.domain.use_case.PrizeUseCases
import com.applicnation.eggnation.feature_eggnation.domain.use_case.UserUseCases
import com.applicnation.eggnation.feature_eggnation.presentation.auth.register.RegisterScreenViewModel
import com.applicnation.eggnation.feature_eggnation.presentation.game.available_prizes.AvailablePrizesScreenViewModel
import com.applicnation.eggnation.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class WonPrizesScreenViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val mainGameLogicUseCases: MainGameLogicUseCases,
    private val userUseCases: UserUseCases,
    private val prizeUseCases: PrizeUseCases
//    private val preferencesUseCases: PreferencesUseCases
) : ViewModel() {

    private val _showPrizeInfo = mutableStateOf(false)
    val showPrizeInfo: State<Boolean> = _showPrizeInfo

    private val _prizes = mutableStateOf(ArrayList<WonPrize>())
    val prizes: State<ArrayList<WonPrize>> = _prizes

    // TODO - probably make the below three it's own component with viewModel in the future
    private val _prizeTitleInfo = mutableStateOf("")
    val prizeTitleInfo: State<String> = _prizeTitleInfo

    private val _prizeDescInfo = mutableStateOf("")
    val prizeDescInfo: State<String> = _prizeDescInfo

    private val _prizeImageInfo = mutableStateOf(0)
    val prizeImageInfo: State<Int> = _prizeImageInfo

    private val _prizeIdInfo = mutableStateOf("")
    val prizeIdInfo: State<String> = _prizeIdInfo

    private val _prizeClaimedInfo = mutableStateOf(true)
    val prizeClaimedInfo: State<Boolean> = _prizeClaimedInfo

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        prizeUseCases.wonPrizeGetAllUC()
            .onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        _isLoading.value = true
                    }
                    is Resource.Success -> {
                        _isLoading.value = false
                        _prizes.value = result.data!!
                    }
                    is Resource.Error -> {
                        _isLoading.value = false
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = result.message!!
                            )
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }


    fun onEvent(event: WonPrizesScreenEvent) {
        when (event) {
            is WonPrizesScreenEvent.ShowPrizeInfo -> {
                // TODO - only do the below if event.showInfo is true
                // TODO - fetch the prize title and name from database and after that's done set showInfo to value
                _prizeTitleInfo.value = event.prizeTitle
                _prizeDescInfo.value = event.prizeDesc
                _prizeImageInfo.value = event.prizeImage
                _prizeIdInfo.value = event.prizeId
                _prizeClaimedInfo.value = event.prizeClaimed

                _showPrizeInfo.value = event.showInfo

                // TODO - set title and desc to "" after the model is dismissed, I will check for "" and show an error message if no text is loaded (maybe)
            }
            is WonPrizesScreenEvent.FetchAvailablePrizes -> {

            }
            is WonPrizesScreenEvent.SetPrizeInfo -> {
                _prizeTitleInfo.value = event.prizeTitle
                _prizeDescInfo.value = event.prizeDesc
                _prizeImageInfo.value = event.prizeImage
            }
            is WonPrizesScreenEvent.ClaimPrize -> {
                val isEmailVerified = userUseCases.getUserEmailVerificationStatusUC()

                if (isEmailVerified) {
                    viewModelScope.launch {
                        _eventFlow.emit(
                            UiEvent.NavToClaimPrizeScreen
                        )
                    }
                } else {
                    viewModelScope.launch {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = "You need to verify your email before you can claim your prize"
                            )
                        )
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        object NavToClaimPrizeScreen : UiEvent()
    }

}