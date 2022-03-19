package com.applicnation.eggnation.feature_eggnation.presentation.game.home.components

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimatable
import com.applicnation.eggnation.feature_eggnation.presentation.game.home.HomeScreenEvent
import com.applicnation.eggnation.feature_eggnation.presentation.game.home.HomeScreenViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreenSideEffects(
    scaffoldState: ScaffoldState,
    viewModel: HomeScreenViewModel,
    loseComposition: LottieComposition?,
    loseAnimatable: LottieAnimatable,
    winComposition: LottieComposition?,
    winAnimatable: LottieAnimatable,
) {
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is HomeScreenViewModel.UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                    )
                }
                is HomeScreenViewModel.UiEvent.PlayLoseAnimation -> {
                    viewModel.onEvent(HomeScreenEvent.SetAnimationPlaying(true))
                    loseAnimatable.animate(
                        loseComposition,
                        continueFromPreviousAnimate = false,
                    )
                    if (loseAnimatable.isAtEnd) {
                        loseAnimatable.snapTo(progress = 0f)
                        viewModel.onEvent(HomeScreenEvent.SetAnimationPlaying(false))
                    }
                }
                is HomeScreenViewModel.UiEvent.PlayWinAnimation -> {
                    viewModel.onEvent(HomeScreenEvent.ShowWonAnimation)
                    viewModel.onEvent(HomeScreenEvent.SetAnimationPlaying(true))
                    winAnimatable.animate(
                        winComposition,
                        continueFromPreviousAnimate = false,
                    )
                    if (winAnimatable.isAtEnd) {
                        viewModel.onEvent(HomeScreenEvent.ShowLoseAnimaton)
                        viewModel.onEvent(HomeScreenEvent.SetAnimationPlaying(false))
                    }
                }
            }
        }
    }
}