package com.applicnation.eggnation.feature_eggnation.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.applicnation.eggnation.feature_eggnation.presentation.game.claim_prize.ClaimPrizeScreenViewModel
import com.applicnation.eggnation.feature_eggnation.presentation.navigation.AuthNavGraph
import com.applicnation.eggnation.feature_eggnation.presentation.navigation.AuthScreen
import com.applicnation.eggnation.feature_eggnation.presentation.navigation.GameNavGraph
import com.applicnation.eggnation.feature_eggnation.presentation.navigation.GameScreen
import com.applicnation.eggnation.ui.theme.EggNationTheme
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity() : ComponentActivity() {
    lateinit var navController: NavHostController
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onStart() {
        super.onStart()
        viewModel.mAuth.addAuthStateListener(viewModel.authStateListener)
    }

    @ExperimentalMaterialApi
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            EggNationTheme {
                navController = rememberNavController()

                if(viewModel.isLoggedIn.value) {
                    GameNavGraph(
                        navController = navController,
                        startDestination = "lol"
                    )
                } else {
                    AuthNavGraph(
                        navController = navController,
                        startDestination = "lol"
                    )
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.mAuth.removeAuthStateListener(viewModel.authStateListener)
    }
}
