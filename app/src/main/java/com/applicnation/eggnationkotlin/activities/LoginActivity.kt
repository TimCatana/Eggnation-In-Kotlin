package com.applicnation.eggnationkotlin.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.applicnation.eggnationkotlin.R
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : BaseActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        var registerTV = findViewById<TextView>(R.id.tvRegister)
        registerTV.setOnClickListener {

            val goToRegisterActivity = Intent(this@LoginActivity, RegisterActivity::class.java)
            goToRegisterActivity.putExtra("key", "Sent String")
            startActivity(goToRegisterActivity)
        }

        var loginBtn = findViewById<Button>(R.id.btnLogin)
        var emailTV = findViewById<EditText>(R.id.etLoginEmail)
        var passwordTV = findViewById<EditText>(R.id.etLoginPassword)

        loginBtn.setOnClickListener {
            login(email = emailTV.text.toString(), password = passwordTV.text.toString())
        }



        // to use the onclick fun i creacte below you need to add:
        // <ViewId>.setOnCLickListener(this) for each component


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





    private fun validateLoginDetails() : Boolean {

        // no spaces before and after.
        // passwords must match
        // password > 8 chars
        // password contain Caps, no caps, and number
        // username must be unique (use firebase database to check this), prob gonna need a corroutine to await the process.
        // check if is valid email format? is there a library for this? If not, The google auth will fail anyway.


        return when {
            TextUtils.isEmpty(findViewById<EditText>(R.id.etLoginEmail).text.toString()) -> {
                showErrorSnackBar("error message", true)
                // will probably use showErrorSnackBar(resources.getString(R.string.name_of_string_from_res, true)
                false
            }
            /**
             * !checkbox.ischekced -> {
             *  show error snackbar
             * }
             */
            // Need to add terms and conditions/ privacy policy acceptance
            // Add checkbox for EU people to accept me showing them ads. Makes it simpler

            // for else statement, return true because the input is valie
            // once returned true, create the firebase auth account
            else -> false
        }

    }
}