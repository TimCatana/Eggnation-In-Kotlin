package com.applicnation.eggnation.feature_eggnation.presentation.game.settings

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.applicnation.eggnation.feature_eggnation.presentation.game.home.HomeScreenViewModel

// TODO - need to decide on a settings screen style

@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsScreenViewModel = hiltViewModel()
){
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Button(onClick = {
            viewModel.onEvent(SettingsScreenEvent.SignOut)
            Log.d("rrrr", "rererereeeeeeeeeeeee")
        }) {
            Text(text = "Logout")
        }
    }
}