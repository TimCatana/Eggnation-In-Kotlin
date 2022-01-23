package com.applicnation.eggnationkotlin.ui.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.applicnation.eggnationkotlin.R
import com.applicnation.eggnationkotlin.firestore.FirestoreClass
import com.applicnation.eggnationkotlin.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

// add View.OnClickListener to overide the onclick. see the commented out function below
class RegisterActivity : BaseActivity() { // Base Activity inherits from AppCompatActivity
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
            // use onBackPressed() to avoid making to many running activities
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
        }


        val bundle: Bundle? = intent.extras

        bundle?.let {
            val msgFromLogin = bundle?.getString("key")
            Toast.makeText(this@RegisterActivity, msgFromLogin, Toast.LENGTH_LONG).show()
        }


        var registerBtn = findViewById<Button>(R.id.btnRegister)
        var userNameTV = findViewById<EditText>(R.id.etRegisterUsername)
        var emailTV = findViewById<EditText>(R.id.etRegisterEmail)
        var passwordTV = findViewById<EditText>(R.id.etRegisterPassword)

        // TODO - make button to register and register user.
        registerBtn.setOnClickListener {
            register(
                username = userNameTV.text.toString(),
                email = emailTV.text.toString(),
                password = passwordTV.text.toString()
            )
        }
    }


    private fun register(username: String?, email: String?, password: String?) {

        // TODO - add the validateRegisterDetails instead of the below null checks

        if (email === null || password === null || username === null) {
            Toast.makeText(
                this@RegisterActivity,
                "Something went wrong while reading email and password",
                Toast.LENGTH_LONG
            ).show()
        }

        // TODO - check to make sure inputs are valid and sanatized

//                    showProgressDialog("registering...")
        auth.createUserWithEmailAndPassword(email!!, password!!)
            .addOnCompleteListener {
                if (it.isSuccessful) {

                    val firebaseUser: FirebaseUser = it.result!!.user!!

                    val user = User(
                        username = username!!,
                        email = email!!
                    )

                    FirestoreClass().registerUser(this@RegisterActivity, user, firebaseUser.uid)


//                    val user: HashMap<String, Any> = HashMap()
//                    // Add user to firestore database
//                        user["username"] = username!!
//                    user["email"] = email!!
//                    user["prizes"] = ArrayList<Any>()
//                    user["created"] = Date()
//
//                        firestore
//                            .collection("users")
//                            .document(auth.currentUser!!.uid)
//                            .set(user)

                    Toast.makeText(
                        this@RegisterActivity,
                        "Successfully registered User",
                        Toast.LENGTH_LONG
                    ).show()

                    // hide progress bar
                    startActivity(Intent(this@RegisterActivity, HomeActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Failed to register user. Please try again ${it.exception!!.message.toString()}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }


    private fun validateRegisterDetails(): Boolean {

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


    // this can be used if you have a lot of onclick listeners. This will override onclick and you can have all the code in one place for different buttons

//    override fun onClick(view: View) {
//        if(view != null) {
//            when(view.id) {
//
////                R.id.<id> -> { run some onclick code }
//
//            }
//        }
//    }



    fun userRegistrationSuccess() {

        hideProgressDialog()

        // show toast that user registered


    }


}