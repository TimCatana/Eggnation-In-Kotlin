package com.applicnation.eggnationkotlin.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.applicnation.eggnationkotlin.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    // TODO - add binding to make it easier to access elements
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        var loginTV = findViewById<TextView>(R.id.tvLogin)
        loginTV.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
        }



        val bundle: Bundle? = intent.extras

        bundle?.let{
            val msgFromLogin = bundle?.getString("key")
            Toast.makeText(this@RegisterActivity, msgFromLogin, Toast.LENGTH_LONG).show()
        }







        var registerBtn = findViewById<Button>(R.id.btnRegister)
        var userNameTV = findViewById<EditText>(R.id.etRegisterUsername)
        var emailTV = findViewById<EditText>(R.id.etRegisterEmail)
        var passwordTV = findViewById<EditText>(R.id.etRegisterPassword)

        // TODO - make button to register and register user.
        registerBtn.setOnClickListener {
            register(username = userNameTV.text.toString(), email = emailTV.text.toString(), password = passwordTV.text.toString())
        }
    }



    private fun register(username: String?, email: String?, password: String?) {
        if(email === null || password === null || username === null) {
            Toast.makeText(this@RegisterActivity, "Something went wrong while reading email and password", Toast.LENGTH_LONG).show()
        }

        // TODO - check to make sure inputs are valid and sanatized

        auth.createUserWithEmailAndPassword(email!!, password!!)
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    // Add user to firestore database
                        val user: MutableMap<String, Any> = HashMap()
                        user["username"] = username!!
                        user["email"] = email!!
                        user["prizes"] = ArrayList<Any>()
                        user["created"] = Date()

                        firestore
                            .collection("users")
                            .document(auth.currentUser!!.uid)
                            .set(user)

                    Toast.makeText(this@RegisterActivity, "Successfully registered User", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this@RegisterActivity, HomeActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this@RegisterActivity, "Failed to register user. Please try again", Toast.LENGTH_LONG).show()
                }
            }
    }




}