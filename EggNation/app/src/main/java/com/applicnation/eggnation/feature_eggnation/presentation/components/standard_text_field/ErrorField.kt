package com.applicnation.eggnation.feature_eggnation.presentation.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle

@Composable
fun ErrorField(error: String, modifier: Modifier) {
    Text(
        text = error,
        style = TextStyle(color = MaterialTheme.colors.error),
        modifier = modifier
    )
}