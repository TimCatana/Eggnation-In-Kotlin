package com.applicnation.eggnation.feature_eggnation.presentation.game.home

sealed class HomeScreenEvent {
    data class DecrementCounter(val value: String): HomeScreenEvent()
    object MainGameLogic: HomeScreenEvent() // TODO - I'm not sure what this use case should look like yet
//    object LoadAd: HomeScreenEvent()
//    object PlayAd: HomeScreenEvent()
}