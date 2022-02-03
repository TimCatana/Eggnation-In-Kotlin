package com.applicnation.eggnation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.applicnation.eggnation.firebase.Authentication
import com.applicnation.eggnation.navigation.AuthNavGraph
import com.applicnation.eggnation.navigation.GameNavGraph
import com.applicnation.eggnation.ui.theme.EggNationTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : ComponentActivity() {
    lateinit var navController: NavHostController
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EggNationTheme {
                navController = rememberNavController()
                mAuth = FirebaseAuth.getInstance()

                if (mAuth.currentUser != null) {
                    GameNavGraph(navController = navController)
                } else {
                    AuthNavGraph(navController = navController)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    EggNationTheme {
        Greeting("Android")
    }
}