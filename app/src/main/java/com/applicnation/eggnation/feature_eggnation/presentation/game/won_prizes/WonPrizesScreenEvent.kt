package com.applicnation.eggnation.feature_eggnation.presentation.game.won_prizes

import com.applicnation.eggnation.feature_eggnation.presentation.game.store.StoreScreenEvent

sealed class WonPrizesScreenEvent {
    data class ShowPrizeInfo(val showInfo: Boolean): WonPrizesScreenEvent()
//    data class ClaimPrize() TODO - need all the user info (email, shipping address, etc...)
}