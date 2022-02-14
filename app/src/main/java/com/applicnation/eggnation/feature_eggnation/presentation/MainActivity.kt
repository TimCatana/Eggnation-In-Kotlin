package com.applicnation.eggnation.feature_eggnation.presentation

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.applicnation.eggnation.feature_eggnation.data.remote.firebase.AdMob
import com.applicnation.eggnation.feature_eggnation.presentation.navigation.AuthNavGraph
import com.applicnation.eggnation.feature_eggnation.presentation.navigation.GameNavGraph
import com.applicnation.eggnation.ui.theme.EggNationTheme
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    lateinit var navController: NavHostController
    private lateinit var mAuth: FirebaseAuth

    private val adMob = AdMob(this)

    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO - sync ROOM database with online db here?, then the user will always get the most up to date info
        // TODO - if they want to pull to refresh they can, pull to refresh will just add info from online db to local ROOM db
        // TODO - maybe only have ROOM db for wonPrizes? cause AvailablePrizes uses realtime database for which reads are free?

        setContent {

            EggNationTheme {
                navController = rememberNavController()
                mAuth = FirebaseAuth.getInstance()

                val lol = true

                // android basics in kotlin
                // design meets code
                // (material you)


                // TODO - need a way to find out how to logout... it's not working with mAuth.currentUser

                if (mAuth.currentUser != null) {
                    GameNavGraph(
                        navController = navController,
                        adMob = adMob
                    )
                } else {
                    AuthNavGraph(navController = navController)
                }

                Log.d("eeeerrrr", "${mAuth.currentUser}")
            }
        }
    }
}
