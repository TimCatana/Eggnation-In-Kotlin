package com.applicnation.eggnation.feature_eggnation.presentation.navigation

const val PRIZE_ID_ARG_KEY = "prizeId"

sealed class GameScreen(val route: String) {
    object Home: GameScreen(route = "homeScreen")
    object WonPrizes: GameScreen(route = "wonPrizesScreen")
    object AvailablePrizes: GameScreen(route = "availablePrizesScreen")
    object ClaimPrize: GameScreen(route = "claimPrizeScreen/{$PRIZE_ID_ARG_KEY}") {
        fun passPrizeId(prizeId: String): String {
            return this.route.replace(oldValue = "{$PRIZE_ID_ARG_KEY}", newValue = prizeId)
        }
    }
    object Settings: GameScreen(route = "settingsScreen")
}
