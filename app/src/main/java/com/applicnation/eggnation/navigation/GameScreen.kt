package com.applicnation.eggnation.navigation

sealed class GameScreen(val route: String) {
    object Home: GameScreen(route = "homeScreen")
    object Store: GameScreen(route = "storeScreen")
    object WonPrizes: GameScreen(route = "wonPrizesScreen")
    object AvailablePrizes: GameScreen(route = "availablePrizesScreen")
    object Settings: GameScreen(route = "settingsScreen")
    object EmailVerification: GameScreen(route = "verifyEmailScreen")
}
