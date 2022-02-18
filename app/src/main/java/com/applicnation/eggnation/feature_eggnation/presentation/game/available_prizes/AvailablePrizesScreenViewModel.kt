package com.applicnation.eggnation.feature_eggnation.presentation.game.available_prizes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applicnation.eggnation.feature_eggnation.domain.modal.AvailablePrize
import com.applicnation.eggnation.feature_eggnation.domain.use_case.prize_use_case.PrizeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AvailablePrizesScreenViewModel @Inject constructor(
//    private val preferencesUseCases: PreferencesUseCases
    private val prizeUseCases: PrizeUseCases
) : ViewModel() {

    private val _showPrizeInfo = mutableStateOf(false)
    val showPrizeInfo: State<Boolean> = _showPrizeInfo

    private val _prizes = mutableStateOf(ArrayList<AvailablePrize>())
    val prizes: State<ArrayList<AvailablePrize>> = _prizes

    // TODO - probably make the below three it's own component with viewModel in the future
    private val _prizeTitleInfo = mutableStateOf("")
    val prizeTitleInfo: State<String> = _prizeTitleInfo

    private val _prizeDescInfo = mutableStateOf("")
    val prizeDescInfo: State<String> = _prizeDescInfo

    private val _prizeImageInfo = mutableStateOf(0)
    val prizeImageInfo: State<Int> = _prizeImageInfo

    private var fetchPrzesJob: Job? = null

    init {
        fetchPrzesJob = viewModelScope.launch {
            _prizes.value = prizeUseCases.availablePrizeGetAllUC()
        }
        // TODO - find a way to wait for init block to finish
        // TODO - probably use one of the states (like iActive or isFinished etc...) but place this somewhere else and maybe show a loading bar when it is active but not completed.
        fetchPrzesJob?.isCompleted
    }


    fun onEvent(event: AvailablePrizesScreenEvent) {
        when (event) {
            is AvailablePrizesScreenEvent.ShowPrizeInfo -> {
                // TODO - only do the below if event.showInfo is true
                // TODO - fetch the prize title and name from database and after that's done set showInfo to value

                _showPrizeInfo.value = event.showInfo
            }
            // TODO - add prize refresh event
            AvailablePrizesScreenEvent.FetchAvailablePrizes -> {
                TODO()
            }
            is AvailablePrizesScreenEvent.SetPrizeInfo -> {
                _prizeTitleInfo.value = event.prizeTitle
                _prizeDescInfo.value = event.prizeDesc
                _prizeImageInfo.value = event.prizeImage
            }
        }
    }

}