package com.applicnation.eggnation.feature_eggnation.presentation.game.home

import android.content.Context
import com.applicnation.eggnation.feature_eggnation.data.remote.firebase.AdMob

sealed class HomeScreenEvent {
    object DecrementCounter: HomeScreenEvent()
    object MainGameLogic: HomeScreenEvent() // TODO - I'm not sure what this use case should look like yet
    data class LoadAd(val adMob: AdMob): HomeScreenEvent()
    data class PlayAd(val adMob: AdMob): HomeScreenEvent()
//     TODO - when I figure out the hilt problem I will make loadAd and playAd objects with no params

}