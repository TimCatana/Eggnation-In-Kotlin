package com.applicnation.eggnation.feature_eggnation.presentation.game.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applicnation.eggnation.R
import com.applicnation.eggnation.feature_eggnation.domain.use_case.database_use_case.AllDatabaseUseCases
import com.applicnation.eggnation.feature_eggnation.domain.use_case.preference_use_case.AllPreferencesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val preferencesUseCases: AllPreferencesUseCases,
    private val databaseUseCases: AllDatabaseUseCases
//    private val adUseCases: AdUseCases TODO - get hilt working to for this... kinda difficult with the scoping
) : ViewModel() {

    private val _userWon = mutableStateOf(false)
    val userWon: State<Boolean> = _userWon

    private val _tapCounter = mutableStateOf(1000)
    val tapCounter: State<Int> = _tapCounter

    private val _eggSkin = mutableStateOf(R.drawable.egg)
    val eggSkin: State<Int> = _eggSkin

    private var getTapCountJob: Job? = null
    private var getSkinJob: Job? = null

    init {
        resetCountIfNeeded()
        getUserSkin()
        getCount()
    }


    // TODO - launch database stuff in IO dispatcher
    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            HomeScreenEvent.IncrementGlobalCounter -> {
                viewModelScope.launch(Dispatchers.IO) {
                    databaseUseCases.incrementGlobalCounterUC()
                }
            }
            is HomeScreenEvent.DecrementCounter -> {
                viewModelScope.launch(Dispatchers.IO) {
                    preferencesUseCases.decrementTapCountPrefUC(_tapCounter.value)
                    getCount()
                }
            }
            is HomeScreenEvent.MainGameLogic -> {
                val rng = (0..5).random()

                viewModelScope.launch(Dispatchers.IO) {
                    val prize = databaseUseCases.availablePrizeGetByRNGUC(rng.toString())
                    _userWon.value = prize.prizeId.isNotBlank()
                    // above is true if it contains characters, false if not
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
        preferencesUseCases.getSelectedSkinPrefUC()
            .map {
                _eggSkin.value = it
            }
            .launchIn(viewModelScope)
    }

    private fun getCount() {
        getTapCountJob?.cancel()
        getTapCountJob = preferencesUseCases.getTapCountPrefUC()
            .map {
                _tapCounter.value = it
            }
            .launchIn(viewModelScope)
    }

    private fun resetCountIfNeeded() {
        val currentTime = Date().time
        val dayInMillis: Long = 86_400_000
        val emptyPreferenceValue: Long = 0

        preferencesUseCases.getLastResetTimePrefUC()
            .map {
                if (it == emptyPreferenceValue) {
                    preferencesUseCases.updateLastResetTimePrefUC(currentTime)
                }
                if ((currentTime - it) >= dayInMillis) {
                    preferencesUseCases.updateTapCountPrefUC(1000)
                    preferencesUseCases.updateLastResetTimePrefUC(currentTime)
                }
            }
            .launchIn(viewModelScope)
    }
}