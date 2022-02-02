package com.applicnation.eggnationkotlin.authStack

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.applicnation.eggnationkotlin.R
import com.applicnation.eggnationkotlin.databinding.FragmentRegisterBinding
import com.applicnation.eggnationkotlin.firebase.Authentication
import com.applicnation.eggnationkotlin.firebase.Firestore
import com.applicnation.eggnationkotlin.models.User

/**
 * this fragment inflates thw view using the parameter
 */

// TODO - move the non-UI stuff to a viewmodel later on
// TODO - make sure all inputs are sanitized
class RegisterFragment : Fragment(R.layout.fragment_register) {
    private val firestore: Firestore = Firestore()
    private val auth: Authentication = Authentication()

    private val mWhiteSpaceChars = "\\s".toRegex()
    private val mLowerCaseChars = "[a-z]".toRegex()
    private val mUpperCaseChars = "[A-Z]".toRegex()
    private val mNumberChars = "\\d".toRegex()

    private var _binding: FragmentRegisterBinding? = null
    private val binding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRegisterBinding.bind(view)

        binding.btnRegister.setOnClickListener {
            if(isValidInput()) {
                val userEmail = binding.etRegisterEmail.text.toString()
                val userName = binding.etRegisterUsername.text.toString()
                val userPassword = binding.etRegisterPassword.text.toString()

                val newUser = User().apply{
                    this.username = userName
                    this.email = userEmail
                }

                auth.createUserAccount(lifecycleScope, userEmail, userPassword)
                // TODO - check if user already exists in auth, and if they do, do not add them to database because this will overwrite their data
                Log.i("RegisterFragment", "uid is: $${auth.getUserId()}")
                firestore.registerUser(lifecycleScope, newUser, auth.getUserId())
            } else {
                Toast.makeText(context, "Invalid Input", Toast.LENGTH_SHORT).show()
            }

        }

        binding.tvLogin.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    /**
     * Checks to see if the user valid inputs
     * - Username is unique
     * - Password is strong and meets requirements
     * - Email is in a valid email pattern (i.e. <chars>@<chars>.<chars>)
     */
    private fun isValidInput(): Boolean {
        return (isValidPassword() && isUniqueUsername() && isValidEmail())
    }

    /**
     * Queries the database (IDK which one yet) to see if username exists already
     * @return (Boolean) Whether the username inputted is unique or not
     */
    private fun isUniqueUsername(): Boolean {
        val userUserName = binding.etRegisterUsername.text.toString()
        // TODO - iterate through a list of usernames in the database and check for uniqueness
        return true
    }

    /**
     * Checks to see if email is in a valid format
     * @Note This does NOT check if the email exists and is a valid email address
     * @Note Firebase Authentication will deal with duplicate email addresses
     * @return (Boolean) Whether the email inputted is in a valid format or not
     */
    private fun isValidEmail(): Boolean {
        val userEmail = binding.etRegisterEmail.text.toString()
        // TODO - send UI message saying to input a valid email address
        return android.util.Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()
    }

    /**
     * Checks to see if the password the user inputted a valid, strong password
     * - Password and ConfirmPassword must match
     * - Password must not be empty
     * - Password must be at least 8 characters long
     * - Password must not contain whitespace
     * - Password must contain lowercase, uppercase and integer characters
     * @return (Boolean) Whether or not the password input is valid
     */
    private fun isValidPassword(): Boolean {
        val userPassword = binding.etRegisterPassword.text.toString()
        // TODO - need to add a 'confirm password' section and test against that

        var isValid = true

        // TODO - test the password input against the confirm password input

        Log.i("RegisterFragment", "$userPassword")


        if(userPassword.isBlank()) {
            Log.i("RegisterFragment", "isBlank")
            // TODO - send UI message to tell the user to enter a password
            isValid = false
        }

        if(userPassword.length < 8) {
            Log.i("RegisterFragment", "is less than 8 chars long")
            // TODO - send UI message to tell the user to make password longer
            isValid = false
        }

        if(userPassword.contains(mWhiteSpaceChars)) {
            Log.i("RegisterFragment", "contains whitespace")
            // TODO - send UI message to tell the user to get rid of whitespace
            isValid = false
        }

        if(!(userPassword.contains(mLowerCaseChars)) || !(userPassword.contains(mUpperCaseChars)) || !(userPassword.contains(mNumberChars)) ) {
            Log.i("RegisterFragment", "does not contain lowercase uppercase and numbers")
            // TODO - send UI message to tell the user to get include lowercase, uppercase and numbers in password
            isValid = false
        }

        return isValid
    }

    /**
     * Destroy the binding to avoid memory leaks
     */
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
