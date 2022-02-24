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

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    private var fetchPrzesJob: Job? = null

    init {
        // TODO - need to get the firebase authentication user id
        fetchPrzesJob = viewModelScope.launch(Dispatchers.IO) {
            _prizes.value =
                prizeUseCases.wonPrizeGetAllUC(
                    "stcRONGZHnWe6g74sHlpBl3xad92" // TODO need to change in production
                ) // TODO - get uid from auth  probably send in a empty string if userGetId returns null
        }

        // TODO - check if fetching is completed before displaying stuff. probably do this in actual composavble code through an if statement? But then I will need to make these mutableSatteOf's
        fetchPrzesJob?.isCompleted
    }


    fun onEvent(event: WonPrizesScreenEvent) {
        when (event) {
            is WonPrizesScreenEvent.ShowPrizeInfo -> {
                // TODO - only do the below if event.showInfo is true
                // TODO - fetch the prize title and name from database and after that's done set showInfo to value

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