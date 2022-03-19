package com.applicnation.eggnation.feature_eggnation.presentation.game.home.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimatable
import com.airbnb.lottie.compose.LottieAnimation

@Composable
fun HomeScreenWinComposition(
    winComposition: LottieComposition?,
    winAnimatable: LottieAnimatable,
    modifier: Modifier
) {
    LottieAnimation(
        winComposition,
        winAnimatable.progress,
        modifier = modifier
    )
}