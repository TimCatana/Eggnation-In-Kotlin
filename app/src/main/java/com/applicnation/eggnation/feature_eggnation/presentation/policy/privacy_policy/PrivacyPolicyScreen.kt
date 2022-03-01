package com.applicnation.eggnation.feature_eggnation.presentation.policy.privacy_policy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.applicnation.eggnation.ui.theme.SettingsBG

// TODO - add all privacy policy text to this screen
@Composable
fun PrivacyPolicyScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier.fillMaxSize().background(color = SettingsBG)
    ) {
        Text(text = "Privacy Policy Screen", color = Color.White)
    }
}