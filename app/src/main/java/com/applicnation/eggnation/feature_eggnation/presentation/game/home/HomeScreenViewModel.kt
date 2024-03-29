package com.applicnation.eggnation.feature_eggnation.presentation.game.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applicnation.eggnation.R
import com.applicnation.eggnation.feature_eggnation.domain.use_case.AdUseCases
import com.applicnation.eggnation.feature_eggnation.domain.use_case.AllPreferencesUseCases
import com.applicnation.eggnation.feature_eggnation.domain.use_case.PrizeUseCases
import com.applicnation.eggnation.feature_eggnation.domain.use_case.MainGameLogicUseCases
import com.applicnation.eggnation.feature_eggnation.presentation.auth.register.RegisterScreenViewModel
import com.applicnation.eggnation.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
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
    private val adUseCases: AdUseCases // TODO - get hilt working to for this... kinda difficult with the scoping
) : ViewModel() {

    private val _userWon = mutableStateOf(false)
    val userWon: State<Boolean> = _userWon

    private val _tapCounter = mutableStateOf(1000)
    val tapCounter: State<Int> = _tapCounter

    private val _eggSkin = mutableStateOf(R.drawable.egg)
    val eggSkin: State<Int> = _eggSkin

    private var getTapCountJob: Job? = null
    private var getSkinJob: Job? = null

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _showWonPrize = mutableStateOf<Boolean>(false)
    val showWonPrize: State<Boolean> = _showWonPrize

    // TODO - probably make the below three it's own component with viewModel in the future
    private val _prizeTitleInfo = mutableStateOf("")
    val prizeTitleInfo: State<String> = _prizeTitleInfo

    private val _prizeDescInfo = mutableStateOf("")
    val prizeDescInfo: State<String> = _prizeDescInfo

    private val _prizeTypeInfo = mutableStateOf("")
    val prizeTypeInfo: State<String> = _prizeTypeInfo

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    // Lottie
    private val _showWinAnimation = mutableStateOf<Boolean>(false)
    val showWinAnimation: State<Boolean> = _showWinAnimation

    private val _showLoseAnimation = mutableStateOf<Boolean>(true)
    val showLoseAnimation: State<Boolean> = _showLoseAnimation

    private val _isAnimationPlaying = mutableStateOf<Boolean>(false)
    val isAnimationPlaying: State<Boolean> = _isAnimationPlaying


    init {
        resetCountIfNeeded()
        getUserSkin()
        getCount()
    }


//    if (viewModel.tapCounter.value % 5 == 0) {
//        viewModel.onEvent(HomeScreenEvent.PlayAd(ctx.getActivity()))
//        // TODO - play lost animation, user get's no chance to win when ad is played. sry bro
//    } else {
//        viewModel.onEvent(HomeScreenEvent.LoadAd(ctx.getActivity()))
//        viewModel.onEvent(HomeScreenEvent.MainGameLogic)
//    }


    // TODO - launch database stuff in IO dispatcher
    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            HomeScreenEvent.IncrementGlobalCounter -> {
                viewModelScope.launch(Dispatchers.IO) {
                    mainGameLogicUseCases.incrementGlobalCounterUC()
                }
            }
            is HomeScreenEvent.DecrementCounter -> {
                viewModelScope.launch(Dispatchers.IO) {
                    preferencesUseCases.decrementTapCountPrefUC(_tapCounter.value)
                    getCount()
                }
            }
            is HomeScreenEvent.MainGameLogic -> {
                // TODO - load add if not loaded (once I get hilt working for this)

                // TODO - disable egg image button while this is running
                mainGameLogicUseCases.doGameLogicUC().onEach { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _isLoading.value = true
                        }
                        is Resource.Success -> {
                            _isLoading.value = false

                            if (result.data == null) {
                                Timber.i("playing lose aniatin")
                                _eventFlow.emit(
                                    UiEvent.PlayLoseAnimation
                                )
                            } else {
                                Timber.i("PRIZE WON! Should show image card now ${result.data}")
                                _prizeTitleInfo.value = result.data.prizeTitle
                                _prizeDescInfo.value = result.data.prizeDesc
                                _prizeTypeInfo.value = result.data.prizeType

                                _eventFlow.emit(
                                    UiEvent.PlayWinAnimation
                                )
                            }
                        }
                        is Resource.Error -> {
                            _isLoading.value = false
                            _eventFlow.emit(
                                UiEvent.PlayLoseAnimation
                            )
                        }
                    }

                    // TODO - re-enable egg image button while this is running
                }.launchIn(viewModelScope)
            }
            is HomeScreenEvent.SetAnimationPlaying -> {
                _isAnimationPlaying.value = event.isPlaying
            }
            is HomeScreenEvent.ShowWonPrize -> {
                _showWonPrize.value = event.isShowing
            }
            is HomeScreenEvent.LoadAd -> {
                if (event.context != null) {
                    adUseCases.adLoadUseCase(event.context)
                }
            }
            is HomeScreenEvent.PlayAd -> {
                if (event.context != null) {
                    var adPlayed = adUseCases.adPlayUseCase(event.context)
                    if (!adPlayed) {
                        viewModelScope.launch {
                            _eventFlow.emit(
                                UiEvent.PlayLoseAnimation
                            )
                        }
                    }
                } else {
                    viewModelScope.launch {
                        _eventFlow.emit(
                            UiEvent.PlayLoseAnimation
                        )
                    }
                }
            }
            HomeScreenEvent.DismissWonPrizeCard -> {
                _showWonPrize.value = false
            }
            is HomeScreenEvent.ShowWonAnimation -> {
                _showLoseAnimation.value = !event.isShowing
                _showWinAnimation.value = event.isShowing
            }
        }
    }

    private fun loadAd() {

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

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        object PlayLoseAnimation : UiEvent()
        object PlayWinAnimation : UiEvent()
    }
}