package com.applicnation.eggnation.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle

@Composable
fun ErrorField(error: String) {
    Text(
        text = error,
        style = TextStyle(color = MaterialTheme.colors.error)
    )
}