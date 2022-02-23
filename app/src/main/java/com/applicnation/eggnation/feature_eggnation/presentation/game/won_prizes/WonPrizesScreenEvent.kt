package com.applicnation.eggnation.feature_eggnation.presentation.game.won_prizes

sealed class WonPrizesScreenEvent {
    data class SetPrizeInfo(val prizeImage: Int, val prizeTitle: String, val prizeDesc: String): WonPrizesScreenEvent()
    data class ShowPrizeInfo(val showInfo: Boolean): WonPrizesScreenEvent()
    object FetchAvailablePrizes: WonPrizesScreenEvent()
    object ClaimPrize: WonPrizesScreenEvent()
//    data class ClaimPrize() TODO - need all the user info (email, shipping address, etc...)
}