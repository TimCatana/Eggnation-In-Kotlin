package com.applicnation.eggnation.feature_eggnation.presentation.game.won_prizes

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applicnation.eggnation.feature_eggnation.domain.use_case.preference_use_case.PreferencesUseCases
import com.applicnation.eggnation.feature_eggnation.presentation.game.store.StoreScreenEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WonPrizesScreenViewModel @Inject constructor(
//    private val preferencesUseCases: PreferencesUseCases
) : ViewModel() {

    private val _showPrizeInfo = mutableStateOf(false)
    val showPrizeInfo = _showPrizeInfo

    private val _prizeTitle = mutableStateOf("")
    val prizeTitle = _showPrizeInfo

    private val _prizeDescription = mutableStateOf("")
    val prizeDescription = _prizeDescription


    fun onEvent(event: WonPrizesScreenEvent) {
        when (event) {
            is WonPrizesScreenEvent.ShowPrizeInfo -> {
                // TODO - only do the below if event.showInfo is true
                // TODO - fetch the prize title and name from database and after that's done set showInfo to value



                _showPrizeInfo.value = event.showInfo

                // TODO - set title and desc to "" after the model is dismissed, I will check for "" and show an error message if no text is loaded (maybe)
            }
        }
    }

}