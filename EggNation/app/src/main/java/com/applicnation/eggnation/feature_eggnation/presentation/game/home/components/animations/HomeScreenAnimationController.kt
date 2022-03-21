package com.applicnation.eggnation.feature_eggnation.presentation.game.home.components.animations

import android.content.Context
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimatable
import com.applicnation.eggnation.feature_eggnation.presentation.game.home.HomeScreenViewModel
import com.applicnation.eggnation.feature_eggnation.presentation.game.home.components.HomeScreenLoseAnimation
import com.applicnation.eggnation.feature_eggnation.presentation.game.home.components.HomeScreenWinComposition

@Composable
fun HomeScreenAnimationController(
    viewModel: HomeScreenViewModel,
    interactionSource: MutableInteractionSource,
    loseComposition: LottieComposition?,
    loseAnimatable: LottieAnimatable,
    isAnimationPlaying: Boolean,
    context: Context,
    winComposition: LottieComposition?,
    winAnimatable: LottieAnimatable,
    modifier: Modifier // TODO - decide on modifier
) {
    if (viewModel.showLoseAnimation.value) {
        HomeScreenLoseAnimation(
            interactionSource = interactionSource,
            loseComposition = loseComposition,
            loseAnimatable = loseAnimatable,
            isAnimationPlaying = isAnimationPlaying,
            viewModel = viewModel,
            context = context,
        )
    } else {
        HomeScreenWinComposition(
            winComposition = winComposition,
            winAnimatable = winAnimatable,
            modifier = modifier
        )
    }
}