package com.applicnation.eggnation.feature_eggnation.presentation.edit_settings.email_settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.applicnation.eggnation.R
import com.applicnation.eggnation.feature_eggnation.presentation.game.settings.ContactSettingsSection
import com.applicnation.eggnation.feature_eggnation.presentation.game.settings.PrivacySettingsSection
import com.applicnation.eggnation.feature_eggnation.presentation.game.settings.SettingsItem
import com.applicnation.eggnation.feature_eggnation.presentation.game.settings.SettingsScreenViewModel
import com.applicnation.eggnation.feature_eggnation.presentation.navigation.SettingScreen

@Composable
fun EmailSettingsScreen(
    navController: NavController,
    viewModel: SettingsScreenViewModel = hiltViewModel()
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 10.dp)
                .background(color = Color.Black),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EmailSettingsSection(navController = navController, viewModel = viewModel)
        }
    }
}

@Composable
fun EmailSettingsSection(
    navController: NavController,
    viewModel: SettingsScreenViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 10.dp)
            .clip(RoundedCornerShape(20.dp)),
    ) {
        SettingsItem(
            settingsTitle = "Change Email",
            settingsInfo = viewModel.email.value,
            icon = Icons.Filled.Edit,
            isLast = false,
            onClick = {
                navController.navigate(SettingScreen.EditEmail.route)
            }
        )
        SettingsItem(
            settingsTitle = "Not Verified",
            settingsInfo = viewModel.email.value,
            icon = Icons.Filled.ChevronRight,
            isLast = true,
            onClick = {
                // TODO - send verification email
            }
        )
    }
}


@Composable
fun SettingsItem(
    settingsTitle: String,
    settingsInfo: String = "",
    isLast: Boolean,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(color = Color.DarkGray)
            .clickable {
                onClick()
            }
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .align(
                    Alignment.CenterStart
                )
        ) {
            Text(
                text = settingsTitle,
                color = Color.White,
                modifier = Modifier
                    .padding(10.dp, 0.dp)
            )
            Row() {
                Text(
                    text = settingsInfo,
                    color = Color.White,
                    modifier = Modifier
                        .padding(10.dp, 0.dp)
                )
                Icon(
                    imageVector = icon,
                    contentDescription = "toggle password",
                    modifier = Modifier
                        .padding(10.dp, 0.dp)
                        .background(Color.Blue)
                )
            }
        }

        if (!isLast) {
            Divider(
                color = Color.Gray, thickness = 1.dp, modifier = Modifier
                    .padding(10.dp, 0.dp)
                    .align(
                        Alignment.BottomCenter
                    )
            )
        }

    }
}