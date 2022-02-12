package com.applicnation.eggnation.feature_eggnation.presentation.game.home

import android.app.Activity
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applicnation.eggnation.R
import com.applicnation.eggnation.feature_eggnation.domain.use_case.ads_use_case.AdUseCases
import com.applicnation.eggnation.feature_eggnation.domain.use_case.database_use_case.DatabaseUseCases
import com.applicnation.eggnation.feature_eggnation.domain.use_case.preference_use_case.PreferencesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val preferencesUseCases: PreferencesUseCases,
    private val databaseUseCases: DatabaseUseCases
//    private val adUseCases: AdUseCases TODO - get hilt working to for this... kinda difficult with the scoping
) : ViewModel() {

    private val _userWon = mutableStateOf(false)
    val userWon: State<Boolean> = _userWon

    private val _tapCounter = mutableStateOf(1000)
    val tapCounter: State<Int> = _tapCounter

    private val _eggSkin = mutableStateOf(R.drawable.egg)
    val eggSkin: State<Int> = _eggSkin

    private var getTapCountJob: Job? = null

    init {
        resetCountIfNeeded()
        getUserSkin()
        getCount()
    }

    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            HomeScreenEvent.IncrementGlobalCounter -> {
                viewModelScope.launch {
                    databaseUseCases.databaseIncrementGlobalCounter()
                }
            }
            is HomeScreenEvent.DecrementCounter -> {
                viewModelScope.launch {
                    preferencesUseCases.preferencesDecrementTapCount(_tapCounter.value)
                    getCount()
                }
            }
            is HomeScreenEvent.MainGameLogic -> {
                val rng = (0..5).random()

                viewModelScope.launch {
                    val prize = databaseUseCases.databaseGetAvailablePrizeByRNG(rng.toString())
                    Log.d("homescreee", "$prize")
                    _userWon.value = prize.prizeId.isNotBlank() // true if id contains characters, false if not
                }
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
        preferencesUseCases.preferencesGetSelectedSkin()
            .map {
                _eggSkin.value = it
            }
            .launchIn(viewModelScope)
    }

    private fun getCount() {
        getTapCountJob?.cancel()
        getTapCountJob = preferencesUseCases.preferencesGetTapCount()
            .map {
                Log.i("lol", "looooool")
                _tapCounter.value = it
            }
            .launchIn(viewModelScope)
    }

    private fun resetCountIfNeeded() {
        val currentTime = Date().time
        val dayInMillis: Long = 86_400_000
        val emptyPreferenceValue: Long = 0

        preferencesUseCases.preferencesGetLastResetTime()
            .map {
                if (it == emptyPreferenceValue) {
                    preferencesUseCases.preferencesUpdateLastResetTime(currentTime)
                }
                if ((currentTime - it) >= dayInMillis) {
                    preferencesUseCases.preferencesUpdateTapCount(1000)
                    preferencesUseCases.preferencesUpdateLastResetTime(currentTime)
                }
            }
            .launchIn(viewModelScope)
    }
}