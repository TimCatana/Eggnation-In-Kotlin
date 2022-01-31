package com.applicnation.eggnationkotlin.authStack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.applicnation.eggnationkotlin.R

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("AuthActivity", "displaying auth activity")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
    }
}