package com.applicnation.eggnation.ui.auth.stack.common.composables

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.font.FontWeight

@Composable
fun HeaderText(
    text: String,
    constraintId: Any
) {
    Text(
        modifier = Modifier.layoutId(constraintId),
        text = text,
        color = Color.Green,
        fontSize = MaterialTheme.typography.h3.fontSize,
        fontWeight = FontWeight.Bold
    )
}