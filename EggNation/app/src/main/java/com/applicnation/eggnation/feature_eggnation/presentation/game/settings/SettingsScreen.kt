package com.applicnation.eggnation.feature_eggnation.presentation.game.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.applicnation.eggnation.R
import com.applicnation.eggnation.feature_eggnation.presentation.components.PasswordModal
import com.applicnation.eggnation.feature_eggnation.presentation.components.StandardTextField
import com.applicnation.eggnation.feature_eggnation.presentation.navigation.PolicyScreen
import com.applicnation.eggnation.feature_eggnation.presentation.navigation.SettingScreen
import com.applicnation.eggnation.ui.theme.SettingsBG
import com.applicnation.eggnation.ui.theme.SettingsItemBG
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

// TODO - need to decide on a settings screen style

@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsScreenViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is SettingsScreenViewModel.UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                    )
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = SettingsBG)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp, 40.dp)
                    .background(color = SettingsBG),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if(!viewModel.isInit.value) {
                    Text(text = "EGGNATION", color = Color.White)
                    UserSettingsSection(navController = navController, viewModel = viewModel)
                    ContactSettingsSection(navController = navController, viewModel = viewModel)
                    PrivacySettingsSection(navController = navController)
                    Button(onClick = {
                        viewModel.onEvent(SettingsScreenEvent.SignOut)
                    }) {
                        Text(text = "Logout")
                    }
                    Button(onClick = {
                        viewModel.onEvent(SettingsScreenEvent.ShowPasswordModel(true))
                    }) {
                        Text(text = "Delete")
                    }
                }
            }

            if(viewModel.isInit.value) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            if (viewModel.isPasswordModelShowing.value) {
                PasswordModal(
                    // TODO - make width and height based on screen dimensions
                    modifier = Modifier
                        .align(Alignment.Center)
                        .width(300.dp)
                        .height(200.dp),
                    cancelModal = {
                        viewModel.onEvent(SettingsScreenEvent.ShowPasswordModel(false))
                    },
                    onDone = { viewModel.onEvent(SettingsScreenEvent.DeleteAccount(viewModel.passwordText.value)) },
                    onTextChange = { viewModel.onEvent(SettingsScreenEvent.EnteredPassword(it)) },
                    text = viewModel.passwordText.value,
                )
            }

            if (viewModel.isLoading.value) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
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
            settingsTitle = "Email",
            settingsInfo = viewModel.email.value,
            icon = Icons.Filled.Edit,
            isLast = false,
            onClick = {
                navController.navigate(SettingScreen.EditEmail.route)
            }
        )
        SettingsItem(
            settingsTitle = "Verification Status",
            settingsInfo = if (viewModel.isEmailVerified.value) {
                "verified"
            } else {
                "not verified"
            },
            icon = if (viewModel.isEmailVerified.value) {
                Icons.Filled.Check
            } else {
                Icons.Filled.Send
            },
            isLast = false,
            onClick = if (viewModel.isEmailVerified.value) {
                {}
            } else {
                { viewModel.onEvent(SettingsScreenEvent.SendVerificationEmail) }
            },
        )
        SettingsItem(
            settingsTitle = "Password",
            settingsInfo = "●●●●●●●●",
            icon = Icons.Filled.Edit,
            isLast = false,
            onClick = {
                navController.navigate(SettingScreen.EditPassword.route)
            }
        )
        SettingsItem(
            settingsTitle = "Language",
            settingsInfo = viewModel.language.value,
            icon = Icons.Filled.ChevronRight,
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
            .background(color = SettingsItemBG)
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
                    tint = Color.White,
                    contentDescription = "toggle password",
                    modifier = Modifier
                        .padding(10.dp, 0.dp)
                )
            }
        }

        if (!isLast) {
            Divider(
                color = Color.Gray, thickness = 1.dp, modifier = Modifier
                    .padding(4.dp, 0.dp)
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