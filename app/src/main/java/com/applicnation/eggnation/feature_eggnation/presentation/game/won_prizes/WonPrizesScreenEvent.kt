package com.applicnation.eggnation.feature_eggnation.presentation.game.won_prizes

import com.applicnation.eggnation.R
import com.applicnation.eggnation.feature_eggnation.presentation.game.available_prizes.AvailablePrizesScreenEvent

sealed class WonPrizesScreenEvent {
    data class SetPrizeInfo(val prizeImage: Int, val prizeTitle: String, val prizeDesc: String) :
        WonPrizesScreenEvent()

    data class ShowPrizeInfo(
        val showInfo: Boolean, val prizeImage: Int = R.drawable.egg,
        val prizeTitle: String = "Failed to fetch Title",
        val prizeDesc: String = "Failed to fetch description"
    ) : WonPrizesScreenEvent()

    object FetchAvailablePrizes : WonPrizesScreenEvent()
    object ClaimPrize : WonPrizesScreenEvent()
//    data class ClaimPrize() TODO - need all the user info (email, shipping address, etc...)
}