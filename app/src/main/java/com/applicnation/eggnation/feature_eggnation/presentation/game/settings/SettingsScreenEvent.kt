package com.applicnation.eggnation.feature_eggnation.presentation.game.settings

import com.applicnation.eggnation.feature_eggnation.data.remote.firebase.AdMob

sealed class SettingsScreenEvent {
    object SignOut: SettingsScreenEvent()
}