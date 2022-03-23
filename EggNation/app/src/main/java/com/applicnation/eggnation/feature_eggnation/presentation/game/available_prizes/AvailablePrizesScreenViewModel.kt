package com.applicnation.eggnation.feature_eggnation.presentation.game.available_prizes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applicnation.eggnation.feature_eggnation.domain.modal.AvailablePrize
import com.applicnation.eggnation.feature_eggnation.domain.use_case.screen_use_cases.game.AvailablePrizeScreenUseCases
import com.applicnation.eggnation.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AvailablePrizesScreenViewModel @Inject constructor(
    private val availablePrizeScreenUseCases: AvailablePrizeScreenUseCases
) : ViewModel() {

    private val _showPrizeInfo = mutableStateOf(false)
    val showPrizeInfo: State<Boolean> = _showPrizeInfo

    private val _prizes = mutableStateOf(ArrayList<AvailablePrize>())
    val prizes: State<ArrayList<AvailablePrize>> = _prizes

    private val _prize = mutableStateOf(AvailablePrize())
    val prize: State<AvailablePrize> = _prize

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        availablePrizeScreenUseCases.availablePrizeGetAllUC()
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


    fun onEvent(event: AvailablePrizesScreenEvent) {
        when (event) {
            is AvailablePrizesScreenEvent.ShowPrizeInfo -> {
                _prize.value = event.prize
                _showPrizeInfo.value = true
            }
            is AvailablePrizesScreenEvent.HidePrizeInfo -> {
                _showPrizeInfo.value = false
                // below is just a default value in case some error occurs
                _prize.value = AvailablePrize(
                    prizeTitle = "Failed to fetch prize title",
                    prizeDesc = "Failed to fetch prize description"
                )
            }
            is AvailablePrizesScreenEvent.FetchAvailablePrizes -> {
                TODO()
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
    }
}