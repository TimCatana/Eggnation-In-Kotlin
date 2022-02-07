package com.applicnation.eggnation.feature_eggnation.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.applicnation.eggnation.feature_eggnation.presentation.auth.forgot_password.ForgotPasswordScreen
import com.applicnation.eggnation.game.stack.*
import com.applicnation.eggnation.policies.PrivacyPolicyScreen
import com.applicnation.eggnation.policies.TermsOfServiceScreen
import com.applicnation.eggnation.feature_eggnation.presentation.auth.login.LoginScreen
import com.applicnation.eggnation.feature_eggnation.presentation.auth.register.RegisterScreen
import com.applicnation.eggnation.feature_eggnation.presentation.game.home.HomeScreen

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

        /**
         * @note these screens are in both stacks. The user will not be visiting these often though
         */
        composable(route = PolicyScreen.PrivacyPolicy.route) {
            PrivacyPolicyScreen(navController = navController)
        }
        composable(route = PolicyScreen.TermsOfService.route) {
            TermsOfServiceScreen(navController = navController)
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

        /**
         * @note these screens are in both stacks. The user will not be visiting these often though
         */
        composable(route = PolicyScreen.PrivacyPolicy.route) {
            PrivacyPolicyScreen(navController = navController)
        }
        composable(route = PolicyScreen.TermsOfService.route) {
            TermsOfServiceScreen(navController = navController)
        }
    }
}