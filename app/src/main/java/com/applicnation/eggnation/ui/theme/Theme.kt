package com.applicnation.eggnation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val GamePalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200,
    background = StoreBG

    /* Other default colors to override
     background = Color.White,
     surface = Color.White,
     onPrimary = Color.White,
     onSecondary = Color.Black,
     onBackground = Color.Black,
     onSurface = Color.Black,
     */
)

@Composable
fun EggNationTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    MaterialTheme(
        colors = GamePalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}