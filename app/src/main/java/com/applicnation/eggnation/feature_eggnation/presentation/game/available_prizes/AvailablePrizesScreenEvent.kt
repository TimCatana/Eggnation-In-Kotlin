package com.applicnation.eggnation.feature_eggnation.presentation.game.available_prizes

import com.applicnation.eggnation.feature_eggnation.presentation.game.won_prizes.WonPrizesScreenEvent

sealed class AvailablePrizesScreenEvent {
    data class SetPrizeInfo(val prizeImage: Int, val prizeTitle: String, val prizeDesc: String): AvailablePrizesScreenEvent()
    data class ShowPrizeInfo(val showInfo: Boolean): AvailablePrizesScreenEvent()
    object FetchAvailablePrizes: AvailablePrizesScreenEvent()
}