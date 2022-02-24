package com.applicnation.eggnation.feature_eggnation.presentation.game.settings

import android.util.Log
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.applicnation.eggnation.R
import com.applicnation.eggnation.feature_eggnation.presentation.navigation.GameScreen
import com.applicnation.eggnation.feature_eggnation.presentation.navigation.PolicyScreen
import com.applicnation.eggnation.feature_eggnation.presentation.navigation.SettingScreen
import com.google.common.cache.RemovalListener

// TODO - need to decide on a settings screen style

@Composable
fun SettingsScreen(
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
            Image(
                painter = painterResource(id = R.drawable.egg),
                contentDescription = "Profile Pircture",
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
                    .padding(0.dp, 10.dp)
                    .clip(CircleShape)
                    .border(color = Color.DarkGray, shape = CircleShape, width = 10.dp)
            )
            UserSettingsSection(navController = navController, viewModel = viewModel)
            ContactSettingsSection(navController = navController, viewModel = viewModel)
            PrivacySettingsSection(navController = navController, viewModel = viewModel)
            Button(onClick = {}) {
                Text(text = "Logout")
            }
        }
    }
}

@Composable
fun UserSettingsSection(
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
            settingsTitle = "Username",
            settingsInfo = viewModel.username.value,
            icon = Icons.Filled.Edit,
            isLast = false,
            onClick = {
                navController.navigate(SettingScreen.EditUsername.route)
            }
        )
        SettingsItem(
            settingsTitle = "Email",
            settingsInfo = viewModel.email.value,
            icon = Icons.Filled.ChevronRight,
            isLast = false,
            onClick = {
                navController.navigate(SettingScreen.EmailSettings.route)
            }
        )
        SettingsItem(
            settingsTitle = "Password",
            settingsInfo = "...",
            icon = Icons.Filled.Edit,
            isLast = false,
            onClick = {
                navController.navigate(SettingScreen.EditPassword.route)
            }
        )
        SettingsItem(
            settingsTitle = "Language",
            settingsInfo = viewModel.language.value,
            icon = Icons.Filled.ChevronLeft,
            isLast = true,
            onClick = {
                navController.navigate(SettingScreen.EditLanguage.route)
            }
        )
    }
}

@Composable
fun PrivacySettingsSection(
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
            settingsTitle = "Term & Conditions",
            icon = Icons.Filled.ChevronRight,
            isLast = false,
            onClick = {
                navController.navigate(PolicyScreen.TermsOfService.route)
            }
        )
        SettingsItem(
            settingsTitle = "Privacy Notice",
            icon = Icons.Filled.ChevronRight,
            isLast = true,
            onClick = {
                navController.navigate(PolicyScreen.PrivacyPolicy.route)
            }
        )
    }
}

@Composable
fun ContactSettingsSection(
    navController: NavController,
    viewModel: SettingsScreenViewModel = hiltViewModel(),
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 10.dp)
            .clip(RoundedCornerShape(20.dp)),
    ) {
        SettingsItem(
            settingsTitle = "Mynza Store",
            icon = Icons.Filled.ChevronRight,
            isLast = false,
            onClick = {}
        )
        SettingsItem(
            settingsTitle = "Contact Us",
            icon = Icons.Filled.ChevronRight,
            isLast = true,
            onClick = {})
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


@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 10.dp)
                .background(color = Color.Red)
        ) {
            settingsSectionPreview()
            settingsSectionPreview()
            settingsSectionPreview()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun settingsSectionPreview(
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp, 10.dp)
            .background(color = Color.Red)
            .clip(RoundedCornerShape(20.dp)),

        ) {
        SettingsItemPreview()
        SettingsItemPreview()
        SettingsItemPreview()
        SettingsItemPreview()

    }
}


@Preview(showBackground = true)
@Composable
fun SettingsItemPreview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(color = Color.Black)
    ) {
        Text(
            text = "Username",
            color = Color.White,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(10.dp, 0.dp)
        )
        Icon(
            imageVector = Icons.Filled.ChevronRight,
            contentDescription = "toggle password",
            modifier = Modifier
                .align(
                    Alignment.CenterEnd
                )
                .padding(10.dp, 0.dp)
                .background(Color.Blue)
        )
        Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(10.dp, 0.dp))
    }
}