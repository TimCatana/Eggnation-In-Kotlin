package com.applicnation.eggnation.feature_eggnation.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition
import com.applicnation.eggnation.R

@Composable
fun TestScreen() {
    var nonce by remember { mutableStateOf(1) }
    var show by remember { mutableStateOf(false) }

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.winner))
    val animatable = rememberLottieAnimatable()

    LaunchedEffect(composition, nonce) {
        composition ?: return@LaunchedEffect
        show = true
        animatable.animate(
            composition,
            continueFromPreviousAnimate = false,
        )

        if (animatable.isAtEnd) {
            show = false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red)
    ) {

        if(show) {
            LottieAnimation(
                composition,
                animatable.progress
            )
        }

        Button(onClick = { nonce++ }) {
            Text(text = "lol")
        }

    }
}