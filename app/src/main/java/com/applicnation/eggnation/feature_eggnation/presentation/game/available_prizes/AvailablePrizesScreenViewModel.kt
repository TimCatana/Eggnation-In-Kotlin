package com.applicnation.eggnation.feature_eggnation.presentation.game.available_prizes

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.applicnation.eggnation.feature_eggnation.domain.modal.Prize
import com.applicnation.eggnation.feature_eggnation.presentation.game.won_prizes.WonPrizesScreenEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AvailablePrizesScreenViewModel @Inject constructor(
//    private val preferencesUseCases: PreferencesUseCases
//private val databaseUseCases: DatabaseUseCases
) : ViewModel() {

    private val _showPrizeInfo = mutableStateOf(false)
    val showPrizeInfo = _showPrizeInfo

    private val _prize = mutableStateOf(Prize())
    val prize = _prize

//    private val _prizeTitle = mutableStateOf("")
//    val prizeTitle = _showPrizeInfo
//
//    private val _prizeDescription = mutableStateOf("")
//    val prizeDescription = _prizeDescription

    init {
        // fetch prizes from database
    }


    fun onEvent(event: AvailablePrizesScreenEvent) {
        when (event) {
            is AvailablePrizesScreenEvent.ShowPrizeInfo -> {
                // TODO - only do the below if event.showInfo is true
                // TODO - fetch the prize title and name from database and after that's done set showInfo to value

                _showPrizeInfo.value = event.showInfo
            }
        }
    }

}