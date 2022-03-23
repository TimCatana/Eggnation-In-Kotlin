package com.applicnation.eggnation.feature_eggnation.presentation.game.home

import android.content.Context

sealed class HomeScreenEvent {
    object IncrementGlobalCounter: HomeScreenEvent()
    data class MainGameLogic(val context: Context): HomeScreenEvent()
    object ShowWonAnimation: HomeScreenEvent()
    object ShowLoseAnimation: HomeScreenEvent()
    object ShowWonPrize: HomeScreenEvent()
    object HideWonPrize: HomeScreenEvent()
    object StartAnimation: HomeScreenEvent()
    object StopAnimation: HomeScreenEvent()
}