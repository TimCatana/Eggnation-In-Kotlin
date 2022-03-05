package com.applicnation.eggnation.feature_eggnation.presentation.navigation

sealed class GameScreen(val route: String) {
    object Test: GameScreen(route = "testScreen")

    object Home: GameScreen(route = "homeScreen")
    object Store: GameScreen(route = "storeScreen")
    object WonPrizes: GameScreen(route = "wonPrizesScreen")
    object AvailablePrizes: GameScreen(route = "availablePrizesScreen")
    object ClaimPrize: GameScreen(route = "claimPrizeScreen")
    object Settings: GameScreen(route = "settingsScreen")
    object EmailVerification: GameScreen(route = "verifyEmailScreen")
}
