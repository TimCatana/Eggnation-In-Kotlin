package com.applicnation.eggnation.feature_eggnation.presentation.game.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.*
import com.applicnation.eggnation.R
import com.applicnation.eggnation.feature_eggnation.presentation.components.PrizeInfoCard
import com.applicnation.eggnation.feature_eggnation.presentation.game.home.components.HomeScreenBottomIcons
import com.applicnation.eggnation.feature_eggnation.presentation.game.home.components.HomeScreenCounterText
import com.applicnation.eggnation.feature_eggnation.presentation.game.home.components.animations.HomeScreenAnimationController
import com.applicnation.eggnation.feature_eggnation.presentation.navigation.GameScreen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val interactionSource = remember { MutableInteractionSource() }
    val context = LocalContext.current

    val winAnimatable = rememberLottieAnimatable()
    val winComposition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.winner)) // TODO - probably move thi to assets?

    val loseAnimatable = rememberLottieAnimatable()
    val loseComposition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.lost)) // TODO - probably move thi to assets?

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is HomeScreenViewModel.UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                    )
                }
                is HomeScreenViewModel.UiEvent.PlayAnimation -> {
                    playLottieAnimation(
                        animatable = if (event.isLoseAnimation) loseAnimatable else winAnimatable,
                        composition = if (event.isLoseAnimation) loseComposition else winComposition,
                        isLoseAnimation = event.isLoseAnimation,
                        viewModel = viewModel
                    )
                }
            }
        }
    }


    Scaffold(
        scaffoldState = scaffoldState,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Icon(
                imageVector = Icons.Filled.Settings,
                contentDescription = "settings icon",
                modifier = Modifier
                    .padding(start = 4.dp, top = 4.dp)
                    .clickable(
                        enabled = !viewModel.isAnimationPlaying.value,
                        interactionSource = interactionSource,
                        indication = null,
                    ) {
                        navController.navigate(GameScreen.Settings.route)
                    }
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxSize()
            ) {
                HomeScreenCounterText(counter = viewModel.tapCounter.value.toString())
                HomeScreenAnimationController(
                    viewModel = viewModel,
                    interactionSource = interactionSource,
                    context = context,
                    isAnimationPlaying = viewModel.isAnimationPlaying.value,
                    loseComposition = loseComposition,
                    loseAnimatable = loseAnimatable,
                    winComposition = winComposition,
                    winAnimatable = winAnimatable,
                    modifier = Modifier
                        .width(200.dp)
                        .height(200.dp) // TODO - decide on modifier
                )
                HomeScreenBottomIcons(
                    viewModel = viewModel,
                    navController = navController,
                    interactionSource = interactionSource
                )
            }

            if (viewModel.showWonPrize.value) {
                BackHandler(enabled = true) {
                    viewModel.onEvent(HomeScreenEvent.HideWonPrize)
                }
                PrizeInfoCard(
                    prizeTitle = viewModel.prize.value.prizeTitle,
                    prizeDesc = viewModel.prize.value.prizeDesc,
                    prizeType = viewModel.prize.value.prizeType,
                    prizeClaimed = false,
                    onDismissCard = { viewModel.onEvent(HomeScreenEvent.HideWonPrize) },
                    showClaimButton = false,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .background(Color.Red)
                        .width(400.dp)
                        .height(400.dp),
                )
            }
        }
    }
}

private suspend fun playLottieAnimation(
    animatable: LottieAnimatable,
    composition: LottieComposition?,
    isLoseAnimation: Boolean,
    viewModel: HomeScreenViewModel,
) {
    if(!isLoseAnimation) {
        viewModel.onEvent(HomeScreenEvent.ShowWonAnimation)
    }
    viewModel.onEvent(HomeScreenEvent.StartAnimation)

    animatable.animate(
        composition,
        continueFromPreviousAnimate = false,
    )
    if (animatable.isAtEnd) {
        animatable.snapTo(progress = 0f)
        viewModel.onEvent(HomeScreenEvent.StopAnimation)
        if(!isLoseAnimation) {
            viewModel.onEvent(HomeScreenEvent.ShowLoseAnimaton)
            viewModel.onEvent(HomeScreenEvent.ShowWonPrize)
        }
    }
}
