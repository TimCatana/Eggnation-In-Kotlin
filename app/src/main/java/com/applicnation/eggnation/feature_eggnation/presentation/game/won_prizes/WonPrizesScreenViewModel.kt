package com.applicnation.eggnation.feature_eggnation.presentation.game.won_prizes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applicnation.eggnation.feature_eggnation.domain.modal.WonPrize
import com.applicnation.eggnation.feature_eggnation.domain.use_case.prize_use_case.PrizeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WonPrizesScreenViewModel @Inject constructor(
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


    private var fetchPrzesJob: Job? = null

    init {
        // TODO - need to get the firebase authentication user id
        fetchPrzesJob = viewModelScope.launch(Dispatchers.IO) {
            _prizes.value =
                prizeUseCases.wonPrizeGetAllUC("bKHSxBGQ4nPp4KKk7yIbLdOFalX2") // TODO - get uid from auth  probably send in a empty string if userGetId returns null
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
            WonPrizesScreenEvent.FetchAvailablePrizes -> {

            }
            is WonPrizesScreenEvent.SetPrizeInfo -> {
                _prizeTitleInfo.value = event.prizeTitle
                _prizeDescInfo.value = event.prizeDesc
                _prizeImageInfo.value = event.prizeImage
            }
        }
    }

}