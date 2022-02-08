package com.applicnation.eggnation.feature_eggnation.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.applicnation.eggnation.feature_eggnation.presentation.navigation.AuthNavGraph
import com.applicnation.eggnation.feature_eggnation.presentation.navigation.GameNavGraph
import com.applicnation.eggnation.ui.theme.EggNationTheme
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    lateinit var navController: NavHostController
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EggNationTheme {
                navController = rememberNavController()
                mAuth = FirebaseAuth.getInstance()

                val lol = false

                // android basics in kotlin
                // design meets code
                // (material you)

                if (lol) {
                    GameNavGraph(navController = navController)
                } else {
                    AuthNavGraph(navController = navController)
                }
            }
        }
    }
}
