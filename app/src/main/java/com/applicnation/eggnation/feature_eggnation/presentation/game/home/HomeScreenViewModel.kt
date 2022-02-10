package com.applicnation.eggnation.feature_eggnation.presentation.game.home

import android.app.Activity
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applicnation.eggnation.R
import com.applicnation.eggnation.feature_eggnation.domain.use_case.ads_use_case.AdUseCases
import com.applicnation.eggnation.feature_eggnation.domain.use_case.preference_use_case.PreferencesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val preferencesUseCases: PreferencesUseCases
//    private val adUseCases: AdUseCases TODO - get hilt working to for this... kinda difficult with the scoping
) : ViewModel() {


    private val _tapCounter = mutableStateOf(1000)
    val tapCounter: State<Int> = _tapCounter

    private val _eggSkin = mutableStateOf(R.drawable.egg)
    val eggSkin: State<Int> = _eggSkin

    private var getSkinJob: Job? = null

    init {
        getUserSkin()
    }

    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.DecrementCounter -> {
                _tapCounter.value--
            }
            is HomeScreenEvent.MainGameLogic -> {
                // TODO
            }
            is HomeScreenEvent.LoadAd -> {
//                adUseCases.adLoadUseCase
                event.adMob.loadInterstitialAd()
            }
            is HomeScreenEvent.PlayAd -> {
//                adUseCases.adPlayUseCase
                event.adMob.playInterstitialAd()
            }
        }
    }

    private fun getUserSkin() {
        getSkinJob?.cancel()
        getSkinJob = preferencesUseCases.preferencesGet()
            .map {
                _eggSkin.value = it.selectedSkin
            }
            .launchIn(viewModelScope)

    }

}