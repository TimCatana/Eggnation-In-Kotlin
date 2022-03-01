package com.applicnation.eggnation.feature_eggnation.presentation

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.applicnation.eggnation.feature_eggnation.data.remote.firebase.AdMob
import com.applicnation.eggnation.feature_eggnation.presentation.navigation.AuthScreen
import com.applicnation.eggnation.feature_eggnation.presentation.navigation.GameNavGraph
import com.applicnation.eggnation.feature_eggnation.presentation.navigation.GameScreen
import com.applicnation.eggnation.ui.theme.EggNationTheme
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    lateinit var navController: NavHostController
    private var mAuth = FirebaseAuth.getInstance()

    @ExperimentalMaterialApi
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val startDest: String

        if (mAuth.currentUser != null) {
            startDest = GameScreen.Home.route
        } else {
            startDest = AuthScreen.Login.route
        }

        setContent {
            EggNationTheme {
                navController = rememberNavController()
                GameNavGraph(
                    navController = navController,
                    startDestination = startDest
                )
            }
        }
    }
}
