package com.applicnation.eggnationkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager.*
import com.applicnation.eggnationkotlin.authStack.AuthActivity
import com.applicnation.eggnationkotlin.gameStack.GameActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        goToCorrectScreen()
    }

    private fun goToCorrectScreen() {
        mAuth = FirebaseAuth.getInstance()
        mAuth.signOut()
        var mfirebaseUser: FirebaseUser? = mAuth.currentUser;
        Log.i("MainActivity", "firebase user is: $mfirebaseUser")

        if(mfirebaseUser != null) {
            // go to home screen
            startActivity(Intent(this@MainActivity, GameActivity::class.java))
            finish()
        } else {
            // go to login screen
            startActivity(Intent(this@MainActivity, AuthActivity::class.java))
            finish()
        }
    }
}
