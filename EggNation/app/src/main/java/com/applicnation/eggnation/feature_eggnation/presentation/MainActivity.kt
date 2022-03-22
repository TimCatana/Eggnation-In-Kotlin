package com.applicnation.eggnation.feature_eggnation.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.applicnation.eggnation.feature_eggnation.presentation.navigation.AuthNavGraph
import com.applicnation.eggnation.feature_eggnation.presentation.navigation.GameNavGraph
import com.applicnation.eggnation.ui.theme.EggNationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity() : ComponentActivity() {
    lateinit var navController: NavHostController
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onStart() {
        super.onStart()
        viewModel.mAuth.addAuthStateListener(viewModel.authStateListener)
    }

    @ExperimentalFoundationApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            EggNationTheme {
                navController = rememberNavController()

                if(viewModel.isLoggedIn.value) {
                    GameNavGraph(
                        navController = navController,
                    )
                } else {
                    AuthNavGraph(
                        navController = navController,
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
