package com.applicnation.eggnation.feature_eggnation.presentation.game.won_prizes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applicnation.eggnation.feature_eggnation.domain.modal.WonPrize
import com.applicnation.eggnation.feature_eggnation.domain.use_case.screen_use_cases.game.WonPrizeScreenUseCases
import com.applicnation.eggnation.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WonPrizesScreenViewModel @Inject constructor(
    private val wonPrizesScreenUseCases: WonPrizeScreenUseCases
) : ViewModel() {

    private val _showPrizeInfo = mutableStateOf(false)
    val showPrizeInfo: State<Boolean> = _showPrizeInfo

    private val _prizes = mutableStateOf(ArrayList<WonPrize>())
    val prizes: State<ArrayList<WonPrize>> = _prizes

    private val _prize = mutableStateOf(WonPrize())
    val prize: State<WonPrize> = _prize

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        wonPrizesScreenUseCases.wonPrizeGetAllUC()
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
                _prize.value = event.prize
                _showPrizeInfo.value = true
            }
            is WonPrizesScreenEvent.HidePrizeInfo -> {
                _showPrizeInfo.value = false
                _prize.value = WonPrize(
                    prizeTitle = "Failed to fetch prize title",
                    prizeDesc = "Failed to fetch prize description",
                    prizeId = "",
                    prizeClaimed = true,
                )
            }
            is WonPrizesScreenEvent.FetchAvailablePrizes -> {
                ///TODO()
            }
            is WonPrizesScreenEvent.GoToClaimScreenIfValid -> {
                val isEmailVerified = wonPrizesScreenUseCases.getUserEmailVerificationStatusUC()

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