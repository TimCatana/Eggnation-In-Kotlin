package com.applicnation.eggnation.feature_eggnation.presentation.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.applicnation.eggnation.feature_eggnation.data.remote.firebase.AdMob
import com.applicnation.eggnation.feature_eggnation.presentation.auth.forgot_password.ForgotPasswordScreen
import com.applicnation.eggnation.feature_eggnation.presentation.auth.login.LoginScreen
import com.applicnation.eggnation.feature_eggnation.presentation.auth.register.RegisterScreen
import com.applicnation.eggnation.feature_eggnation.presentation.edit_settings.edit_email.EditEmailScreen
import com.applicnation.eggnation.feature_eggnation.presentation.edit_settings.edit_language.EditLanguageScreen
import com.applicnation.eggnation.feature_eggnation.presentation.edit_settings.edit_password.EditPasswordScreen
import com.applicnation.eggnation.feature_eggnation.presentation.edit_settings.edit_username.EditUsernameScreen
import com.applicnation.eggnation.feature_eggnation.presentation.game.available_prizes.AvailablePrizesScreen
import com.applicnation.eggnation.feature_eggnation.presentation.game.claim_prize.ClaimPrizeScreen
import com.applicnation.eggnation.feature_eggnation.presentation.game.home.HomeScreen
import com.applicnation.eggnation.feature_eggnation.presentation.game.settings.SettingsScreen
import com.applicnation.eggnation.feature_eggnation.presentation.game.store.StoreScreen
import com.applicnation.eggnation.feature_eggnation.presentation.game.verify_email.VerifyEmailScreen
import com.applicnation.eggnation.feature_eggnation.presentation.game.won_prizes.WonPrizesScreen
import com.applicnation.eggnation.feature_eggnation.presentation.policy.privacy_policy.PrivacyPolicyScreen
import com.applicnation.eggnation.feature_eggnation.presentation.policy.terms_of_service.TermsOfServiceScreen
import com.google.rpc.context.AttributeContext

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun GameNavGraph(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = GameScreen.Home.route
    ) {
        composable(route = GameScreen.Home.route) {
            HomeScreen(
                navController = navController,
            )
        }
        composable(route = GameScreen.Store.route) {
            StoreScreen(navController = navController)
        }
        composable(route = GameScreen.WonPrizes.route) {
            WonPrizesScreen(navController = navController)
        }
        composable(route = GameScreen.AvailablePrizes.route) {
            AvailablePrizesScreen(navController = navController)
        }
        composable(route = GameScreen.ClaimPrize.route) {
            ClaimPrizeScreen(navController = navController)
        }
        composable(route = GameScreen.Settings.route) {
            SettingsScreen(navController = navController)
        }
        composable(route = GameScreen.EmailVerification.route) {
            VerifyEmailScreen(navController = navController)
        }

        composable(route = SettingScreen.EditUsername.route) {
            EditUsernameScreen(navController = navController)
        }
        composable(route = SettingScreen.EditEmail.route) {
            EditEmailScreen(navController = navController)
        }
        composable(route = SettingScreen.EditPassword.route) {
            EditPasswordScreen(navController = navController)
        }
        composable(route = SettingScreen.EditLanguage.route) {
            EditLanguageScreen(navController = navController)
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
fun AuthNavGraph(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = AuthScreen.Login.route
    ) {
        /**
         *
         */
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