package com.applicnation.eggnation.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

//val fontName = FontFamily(
//    Font(R.font.quicksand_light, FontWeight.Light),
//    Font(R.font.quicksand_regular, FontWeight.Normal),
//    Font(R.font.quicksand_medium, FontWeight.Medium),
//    Font(R.font.quicksand_semibold, FontWeight.SemiBold),
//    Font(R.font.quicksand_bold, FontWeight.Bold),
//)

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)