package com.applicnation.eggnation.feature_eggnation.presentation.game.home

import android.app.Activity
import android.content.Context
import com.applicnation.eggnation.feature_eggnation.data.remote.firebase.AdMob

sealed class HomeScreenEvent {
    object IncrementGlobalCounter: HomeScreenEvent()
    object DecrementCounter: HomeScreenEvent()
    object MainGameLogic: HomeScreenEvent()
    data class LoadAd(val context: Activity?): HomeScreenEvent()
    data class PlayAd(val context: Activity?): HomeScreenEvent()

    object ShowWonAnimation: HomeScreenEvent()
    object ShowLoseAnimaton: HomeScreenEvent()

    object ShowWonPrize: HomeScreenEvent()
    object HideWonPrize: HomeScreenEvent()

    data class SetAnimationPlaying(val isPlaying: Boolean): HomeScreenEvent()
}