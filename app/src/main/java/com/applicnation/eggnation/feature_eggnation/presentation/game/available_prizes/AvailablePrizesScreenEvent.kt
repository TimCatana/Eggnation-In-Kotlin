package com.applicnation.eggnation.feature_eggnation.presentation.game.available_prizes

import com.applicnation.eggnation.R
import com.applicnation.eggnation.feature_eggnation.presentation.game.won_prizes.WonPrizesScreenEvent

sealed class AvailablePrizesScreenEvent {
    data class ShowPrizeInfo(
        val showInfo: Boolean,
        val prizeImage: Int = R.drawable.egg,
        val prizeTitle: String = "Failed to fetch Title",
        val prizeDesc: String = "Failed to fetch description"
    ) : AvailablePrizesScreenEvent()

    object FetchAvailablePrizes : AvailablePrizesScreenEvent()
}