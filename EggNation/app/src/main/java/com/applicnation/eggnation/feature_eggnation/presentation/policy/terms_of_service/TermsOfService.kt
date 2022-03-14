package com.applicnation.eggnation.feature_eggnation.presentation.policy.terms_of_service

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.applicnation.eggnation.ui.theme.SettingsBG

// TODO - add all terms of service text here
@Composable
fun TermsOfServiceScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier.fillMaxSize().background(color = SettingsBG)
    ) {
        Text(text = "Terms or service Screen", color = Color.White)
    }
}