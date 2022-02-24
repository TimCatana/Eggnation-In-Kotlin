package com.applicnation.eggnation.feature_eggnation.presentation.navigation

sealed class SettingScreen(val route: String) {
    object EditUsername: SettingScreen(route = "editUsernameScreen")
    object EditEmail: SettingScreen(route = "editEmailScreen")
    object EditPassword: SettingScreen(route = "editPasswordScreen")
    object EditLanguage: SettingScreen(route = "editLanguageScreen")
    object EmailSettings: SettingScreen(route = "editSettingsScreen")
}
