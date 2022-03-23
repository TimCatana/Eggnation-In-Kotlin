package com.applicnation.eggnation.feature_eggnation.presentation.game.home.components

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimatable
import com.airbnb.lottie.compose.LottieAnimation
import com.applicnation.eggnation.feature_eggnation.presentation.game.home.HomeScreenEvent
import com.applicnation.eggnation.feature_eggnation.presentation.game.home.HomeScreenViewModel

@Composable
fun HomeScreenLoseAnimation(
    interactionSource: MutableInteractionSource,
    loseComposition: LottieComposition?,
    loseAnimatable: LottieAnimatable,
    isAnimationPlaying: Boolean,
    isWonPrizeShowing: Boolean,
    viewModel: HomeScreenViewModel,
    context: Context,
    modifier: Modifier
) {
    LottieAnimation(
        loseComposition,
        loseAnimatable.progress,
        modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = !isAnimationPlaying && !isWonPrizeShowing
            ) {
                viewModel.onEvent(HomeScreenEvent.IncrementGlobalCounter)
                viewModel.onEvent(HomeScreenEvent.MainGameLogic(context))
            }
    )
}