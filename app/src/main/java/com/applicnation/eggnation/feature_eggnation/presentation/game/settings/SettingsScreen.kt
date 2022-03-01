package com.applicnation.eggnation.feature_eggnation.presentation.game.settings

import android.graphics.Color.rgb
import android.provider.Settings
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
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
import com.applicnation.eggnation.feature_eggnation.domain.modal.AvailablePrize
import com.applicnation.eggnation.feature_eggnation.presentation.auth.login.LoginScreenEvent
import com.applicnation.eggnation.feature_eggnation.presentation.auth.register.RegisterScreenEvent
import com.applicnation.eggnation.feature_eggnation.presentation.auth.register.RegisterScreenViewModel
import com.applicnation.eggnation.feature_eggnation.presentation.components.StandardTextField
import com.applicnation.eggnation.feature_eggnation.presentation.game.available_prizes.AvailablePrizeItemInfoCard
import com.applicnation.eggnation.feature_eggnation.presentation.game.available_prizes.AvailablePrizesScreenEvent
import com.applicnation.eggnation.feature_eggnation.presentation.game.available_prizes.AvailablePrizesScreenViewModel
import com.applicnation.eggnation.feature_eggnation.presentation.navigation.AuthScreen
import com.applicnation.eggnation.feature_eggnation.presentation.navigation.GameScreen
import com.applicnation.eggnation.feature_eggnation.presentation.navigation.PolicyScreen
import com.applicnation.eggnation.feature_eggnation.presentation.navigation.SettingScreen
import com.applicnation.eggnation.ui.theme.SettingsBG
import com.applicnation.eggnation.ui.theme.SettingsItemBG
import com.google.common.cache.RemovalListener
import kotlinx.coroutines.flow.collectLatest

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
                is SettingsScreenViewModel.UiEvent.ChangeStacks -> {
                    viewModel.onEvent(SettingsScreenEvent.EnteredPassword(""))
                    viewModel.onEvent(SettingsScreenEvent.ShowPasswordModel(false, ""))
                    navController.navigate(event.screen)
                }
                is SettingsScreenViewModel.UiEvent.GoToLoginScreen -> {
                    navController.navigate(AuthScreen.Login.route) {
                        popUpTo(GameScreen.Home.route) {
                            inclusive = true
                        }
                    }
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
                    .padding(10.dp, 10.dp)
                    .background(color = SettingsBG),
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
                Button(onClick = {
                    viewModel.onEvent(SettingsScreenEvent.SignOut)
                }) {
                    Text(text = "Logout")
                }
            }

            if (viewModel.isPasswordModelShowing.value) {
                PasswordModel(
                    viewModel = viewModel,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .background(Color.White)
                        .width(400.dp)
                        .height(400.dp), // TODO - make width and height based on screen dimensions
                )
            }
        }
    }// TODO add a delete account option
}

@Composable
private fun PasswordModel(
    viewModel: SettingsScreenViewModel,
    modifier: Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = "exit",
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 4.dp, top = 4.dp)
                .clickable {
                    viewModel.onEvent(SettingsScreenEvent.EnteredPassword(""))
                    viewModel.onEvent(SettingsScreenEvent.ShowPasswordModel(false, ""))
                }
        )
        StandardTextField(
            text = viewModel.passwordText.value,
            onValueChange = {
                viewModel.onEvent(SettingsScreenEvent.EnteredPassword(it))
            },
            isError = false,
            errorText = "Please enter a password",
            keyboardType = KeyboardType.Password,
            label = "password" // TODO - use string resources
        )
        Button(onClick = {
            viewModel.onEvent(SettingsScreenEvent.EditProfile(viewModel.passwordText.value))
        }) {
            Text(text = "Confirm")
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
                viewModel.onEvent(
                    SettingsScreenEvent.ShowPasswordModel(
                        true,
                        SettingScreen.EditUsername.route
                    )
                )
            }
        )
        SettingsItem(
            settingsTitle = "Email",
            settingsInfo = viewModel.email.value,
            icon = Icons.Filled.Edit,
            isLast = false,
            onClick = {
                viewModel.onEvent(
                    SettingsScreenEvent.ShowPasswordModel(
                        true,
                        SettingScreen.EditEmail.route
                    )
                )
            }
        )
        SettingsItem(
            settingsTitle = "Verification Status",
            settingsInfo = "",
            icon = Icons.Filled.CheckBox,
            isLast = false,
            onClick = {}
        )
        SettingsItem(
            settingsTitle = "Password",
            settingsInfo = "...",
            icon = Icons.Filled.Edit,
            isLast = false,
            onClick = {
                viewModel.onEvent(
                    SettingsScreenEvent.ShowPasswordModel(
                        true,
                        SettingScreen.EditPassword.route
                    )
                )
//                navController.navigate(SettingScreen.EditPassword.route)
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