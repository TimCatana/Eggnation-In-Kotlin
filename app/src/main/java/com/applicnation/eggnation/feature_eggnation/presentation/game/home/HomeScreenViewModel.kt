package com.applicnation.eggnation.feature_eggnation.presentation.game.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applicnation.eggnation.R
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
    private val prizeUseCases: PrizeUseCases
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
    private val _isAnimationPlaying = mutableStateOf<Boolean>(false)
    val isAnimationPlaying: State<Boolean> = _isAnimationPlaying

//    private val _isLoseAnimationPlaying = mutableStateOf<Boolean>(false)
//    val isLoseAnimationPlaying: State<Boolean> = _isLoseAnimationPlaying

    private val _animationToPlay = mutableStateOf<Int>(R.raw.winner)
    val animationToPlay: State<Int> = _animationToPlay




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
//                            _animationToPlay.value = R.raw.winner

                            if (result.data == null) {
//                                _animationToPlay.value = R.raw.winner // TODO change to lost
                                _isAnimationPlaying.value = true

//                                _eventFlow.emit(
//                                    // TODO - emit play lose animation and get rid of snackbar
//                                    UiEvent.ShowSnackbar(
//                                        message = result.message ?: "LOLL"
//                                    )
//                                )
                            } else {
                                Timber.i("PRIZE WON! Should show image card now ${result.data}")
                                _prizeTitleInfo.value = result.data.prizeTitle
                                _prizeDescInfo.value = result.data.prizeDesc
                                _prizeTypeInfo.value = result.data.prizeType

                                // TODO - emit play win animation here and get rid of snackbar
//                                _animationToPlay.value = R.raw.lost
                                _isAnimationPlaying.value = true

//                                _showWonPrize.value = true // TODO - make sure this comes after the animation - miight make a different event for this

//                                _eventFlow.emit(
//                                    UiEvent.ShowSnackbar(
//                                        message = result.message ?: "LOLL"
//                                    )
//                                )
                            }

                        }
                        is Resource.Error -> {
                            _isLoading.value = false
                            // TODO - play lose animation and get rid of snackbar

                            _eventFlow.emit(
                                UiEvent.ShowSnackbar(
                                    message = result.message ?: "LOLL"
                                )
                            )
                        }
                    }

                    // TODO - re-enable egg image button while this is running
                }.launchIn(viewModelScope)
            }
            is HomeScreenEvent.PlayAnimation -> {
                _isAnimationPlaying.value = event.isPlaying
            }
            is HomeScreenEvent.LoadAd -> {
//                adUseCases.adLoadUseCase
                event.adMob.loadInterstitialAd()
            }
            is HomeScreenEvent.PlayAd -> {
//                adUseCases.adPlayUseCase
                event.adMob.playInterstitialAd()
            }
            HomeScreenEvent.DismissWonPrizeCard -> {
                _showWonPrize.value = false
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

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        object PlayLoseAnimation : UiEvent()
        object PlayWinAnimation : UiEvent()
    }
}