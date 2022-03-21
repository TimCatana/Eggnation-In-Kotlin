package com.applicnation.eggnation.feature_eggnation.presentation.game.home

import android.app.Activity
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applicnation.eggnation.feature_eggnation.domain.modal.AvailablePrize
import com.applicnation.eggnation.feature_eggnation.domain.use_case.AdUseCases
import com.applicnation.eggnation.feature_eggnation.domain.use_case.AllPreferencesUseCases
import com.applicnation.eggnation.feature_eggnation.domain.use_case.PrizeUseCases
import com.applicnation.eggnation.feature_eggnation.domain.use_case.MainGameLogicUseCases
import com.applicnation.eggnation.util.Resource
import com.applicnation.eggnation.util.getActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val preferencesUseCases: AllPreferencesUseCases,
    private val mainGameLogicUseCases: MainGameLogicUseCases,
    private val prizeUseCases: PrizeUseCases,
    private val adUseCases: AdUseCases,
) : ViewModel() {

    private val _tapCounter = mutableStateOf(1000)
    val tapCounter: State<Int> = _tapCounter

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _showWonPrize = mutableStateOf<Boolean>(false)
    val showWonPrize: State<Boolean> = _showWonPrize

    // TODO - probably make the below three it's own component with viewModel in the future
    private val _prize = mutableStateOf(AvailablePrize())
    val prize: State<AvailablePrize> = _prize

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    // Lottie
    private val _showLoseAnimation = mutableStateOf<Boolean>(true)
    val showLoseAnimation: State<Boolean> = _showLoseAnimation

    private val _isAnimationPlaying = mutableStateOf<Boolean>(false)
    val isAnimationPlaying: State<Boolean> = _isAnimationPlaying

    init {
        resetCountIfNeeded()
        getCount()
    }

    // TODO - launch database stuff in IO dispatcher
    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.IncrementGlobalCounter -> {
                viewModelScope.launch(Dispatchers.IO) {
                    mainGameLogicUseCases.incrementGlobalCounterUC()
                }
            }
            is HomeScreenEvent.MainGameLogic -> {
                if (!mainGameLogicUseCases.internetConnectivityUC(event.context)) {
                    viewModelScope.launch { _eventFlow.emit(UiEvent.ShowSnackbar("Must be connected to the internet")) }
                } else {
                    decrementCounter()

                    if (_tapCounter.value % 20 == 0) {
                        playAd(event.context.getActivity())
                    } else {
                        loadAd(event.context.getActivity())
                        mainGameLogic()
                    }
                }
            }
            is HomeScreenEvent.StartAnimation -> {
                _isAnimationPlaying.value = true
            }
            is HomeScreenEvent.StopAnimation -> {
                _isAnimationPlaying.value = false
            }
            is HomeScreenEvent.ShowLoseAnimaton -> {
                _showLoseAnimation.value = true
            }
            is HomeScreenEvent.ShowWonAnimation -> {
                _showLoseAnimation.value = false
            }
            is HomeScreenEvent.ShowWonPrize -> {
                _showWonPrize.value = true
            }
            is HomeScreenEvent.HideWonPrize -> {
                _showWonPrize.value = false
            }
        }
    }

    /**
     * Decrements the local counter
     * // TODO - need to fix this up, the way it's set up now can cause out of sync
     */
    private fun decrementCounter() {
        viewModelScope.launch {
            preferencesUseCases.decrementTapCountPrefUC().collectLatest {
                _tapCounter.value = it
            }
        }
    }

    /**
     * Does the main game logic.
     */
    private fun mainGameLogic() {
        mainGameLogicUseCases.doGameLogicUC().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _isLoading.value = true
                }
                is Resource.Success -> {
                    _isLoading.value = false
                    if (result.data == null) {
                        Timber.i("playing lose animation")
                        _eventFlow.emit(UiEvent.PlayAnimation(true))
                    } else {
                        Timber.i("playing won animation... the prize won is ${result.data}")
                        _prize.value = result.data
                        _eventFlow.emit(UiEvent.PlayAnimation(false))
                    }
                }
                is Resource.Error -> {
                    _isLoading.value = false
                    _eventFlow.emit(UiEvent.PlayAnimation(true))
                }
            }

            // TODO - re-enable egg image button while this is running
        }.launchIn(viewModelScope)
    }


    /**
     * If ad fails to show, then show the lose animation.
     * If ad shows, then no need to play any animation
     * @note activityContext should never be null
     */
    private fun playAd(activityContext: Activity?) {
        if (activityContext != null) {
            val adSuccessful = adUseCases.adPlayUseCase(activityContext)
            if (!adSuccessful) {
                viewModelScope.launch { _eventFlow.emit(UiEvent.PlayAnimation(true)) }
            }
        } else {
            viewModelScope.launch { _eventFlow.emit(UiEvent.PlayAnimation(false)) }
        }
    }

    /**
     * @note activityContext should never be null
     */
    private fun loadAd(activityContext: Activity?) {
        if (activityContext != null) {
            adUseCases.adLoadUseCase(activityContext)
        }
    }

    private fun getCount() {
        preferencesUseCases.getTapCountPrefUC()
            .map { _tapCounter.value = it }
            .launchIn(viewModelScope)
    }

    /**
     * Resets the counter every 12 hours
     */
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

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        data class PlayAnimation(val isLoseAnimation: Boolean) : UiEvent()
    }
}