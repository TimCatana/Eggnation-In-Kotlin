package com.applicnation.eggnation.feature_eggnation.presentation.game.home.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun HomeScreenCounterText(
    counter: String
) {
    Text(
        text = counter,
        style = MaterialTheme.typography.h3,
    )
}