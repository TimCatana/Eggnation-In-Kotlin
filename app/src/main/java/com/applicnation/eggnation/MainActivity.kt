package com.applicnation.eggnation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.applicnation.eggnation.navigation.AuthNavGraph
import com.applicnation.eggnation.navigation.GameNavGraph
import com.applicnation.eggnation.ui.theme.EggNationTheme

class MainActivity : ComponentActivity() {
    lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EggNationTheme {
                navController = rememberNavController()
                val test = false // replace with firebase auth later on
                
                if(test) {
                    AuthNavGraph(navController = navController)
                } else {
                    GameNavGraph(navController = navController)
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