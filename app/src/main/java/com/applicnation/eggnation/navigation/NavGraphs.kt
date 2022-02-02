package com.applicnation.eggnation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.applicnation.eggnation.auth.stack.ForgotPasswordScreen
import com.applicnation.eggnation.auth.stack.LoginScreen
import com.applicnation.eggnation.auth.stack.RegisterScreen
import com.applicnation.eggnation.game.stack.*

@Composable
fun AuthNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = AuthScreen.Login.route
    ) {
        composable(route = AuthScreen.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(route = AuthScreen.Register.route) {
            RegisterScreen(navController = navController)
        }
        composable(route = AuthScreen.ForgotPassword.route) {
            ForgotPasswordScreen(navController = navController)
        }

    }
}

@Composable
fun GameNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = GameScreen.Home.route
    ) {
        composable(route = GameScreen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(route = GameScreen.Store.route) {
            StoreScreen(navController = navController)
        }
        composable(route = GameScreen.WonPrizes.route) {
            WonPrizesScreen(navController = navController)
        }
        composable(route = GameScreen.AvailablePrizes.route) {
            AvailablePrizeScreen(navController = navController)
        }
        composable(route = GameScreen.Settings.route) {
            SettingsScreen(navController = navController)
        }
        composable(route = GameScreen.EmailVerification.route) {
            EmailVerificationScreen(navController = navController)
        }
    }
}