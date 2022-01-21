package com.applicnation.eggnationkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        var registerTV = findViewById<TextView>(R.id.tvRegister)
        registerTV.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }

        var loginBtn = findViewById<Button>(R.id.btnLogin)
        var emailTV = findViewById<EditText>(R.id.etLoginEmail)
        var passwordTV = findViewById<EditText>(R.id.etLoginPassword)

        loginBtn.setOnClickListener {
            login(email = emailTV.text.toString(), password = passwordTV.text.toString())
        }
    }





    private fun login(email: String?, password: String?) {
        if(email === null || password === null) {
            Toast.makeText(this@LoginActivity, "Something went wrong while reading email and password", Toast.LENGTH_LONG).show()
        }

        // TODO - check to make sure inputs are valid and sanatized

        auth.signInWithEmailAndPassword(email!!, password!!)
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    // Add user to firestore database
                    Toast.makeText(this@LoginActivity, "Successfully Logged In ", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this@LoginActivity, "Failed to register user. Please try again", Toast.LENGTH_LONG).show()
                }
            }
    }
}