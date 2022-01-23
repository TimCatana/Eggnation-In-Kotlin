package com.applicnation.eggnationkotlin.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.applicnation.eggnationkotlin.R
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)


        var btnforgot = findViewById<Button>(R.id.btn_forgot_password)

        btnforgot.setOnClickListener {
            val email = findViewById<EditText>(R.id.tv_forgot_password).toString()
            // check if email is empty and give a warning
            // show progress dialog to show that something is happening

            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener {

                    if(it.isSuccessful) {
                        Toast.makeText(this@ForgotPasswordActivity, "Email sent!", Toast.LENGTH_LONG).show()
                        finish() // goes back to login screen, finishes this activity
                    } else {
                        // show an error
                    }
                }
        }

    }


}