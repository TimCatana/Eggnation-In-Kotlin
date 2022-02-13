package com.applicnation.eggnation.feature_eggnation.data.remote.firebase

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class Authentication {
    private val auth = FirebaseAuth.getInstance()

    suspend fun signInUser(email: String, password: String) {
            try{
                auth.signInWithEmailAndPassword(email, password).await()
            } catch (err: Exception) {
                // TODO - need to propogate some error to the UI
                Log.i("signUp", "Failed to signIn: ${err.message.toString()}")
            }
    }

    suspend fun signUpUser(email: String, password: String) {
        Log.i("signUp", "in authentucation class ${email}")
            try {
                auth.createUserWithEmailAndPassword(email, password).await()
            } catch (err: Exception) {
                // TODO - need to propogate some error to the UI
                Log.i("signUp", "Failed to create account: ${err.message.toString()}")
            }
    }

    suspend fun sendPasswordResetEmail(email: String) {
        try {
            auth.sendPasswordResetEmail(email).await()
            // TODO - propogate message saying that the email was sent
        } catch (e: Exception) {
            // TODO - need to propogate some error to the UI
            Log.i("signUp", "Failed to create account: ${e.message.toString()}")
        }
    }

    suspend fun verifyUserEmail(email: String) {
        // TODO - send user verification email
    }

    fun getUserId(): String? {
        return auth.currentUser?.uid
    }

//    fun signOut() {
//        try{
//            auth.signOut()
//        } catch (err: Exception) {
//            Log.i("Authentication", "Failed to signIn: ${err.message.toString()}")
//        }
//    }
//
//    fun verifyUserEmail(scope: CoroutineScope, email: String, password: String) {
//        scope.launch {
//            try {
//                // TODO - make a deep link in the email that will send the user to a specific fragment that will tell the app that the user has clicked the verification link
//                auth.currentUser?.sendEmailVerification()?.await()
//            } catch (err: Exception) {
//                Log.i("Authentication", "Failed to send email verification: ${err.message.toString()}")
//            }
//        }
//    }

//    fun updateUserPassword(scope: CoroutineScope, newPassword: String) {
//        scope.launch {
//            try {
//                auth.currentUser?.updatePassword(newPassword)?.await()
//            } catch (err: Exception) {
//                Log.i("Authentication", "Failed to change password: ${err.message.toString()}")
//            }
//        }
//    }
//
//    fun updateUserEmail(scope: CoroutineScope, newEmail: String) {
//        scope.launch {
//            try {
//                auth.currentUser?.updateEmail(newEmail)?.await()
//            } catch (err: Exception) {
//                Log.i("Authentication", "Failed to change email: ${err.message.toString()}")
//            }
//        }
//
//    }
//
//    fun updateUserUserName(scope: CoroutineScope, newUsername: String) {
//        val profileUpdates = userProfileChangeRequest {
//            displayName = newUsername
//        }
//
//        scope.launch {
//            try {
//                auth.currentUser?.updateProfile(profileUpdates)?.await()
//            } catch (err: Exception) {
//                Log.i("Authentication", "Failed to update user display name: ${err.message.toString()}")
//            }
//        }
//    }

        // TODO - docs say that the user needs to have recently signed in for this to work. I might need to reauthenticate when the user hits the delete button to
//    // avoid this error. See https://firebase.google.com/docs/auth/android/manage-users#re-authenticate_a_user
//    fun deleteUserAccout(scope: CoroutineScope) {
//        scope.launch {
//            try {
//                auth.currentUser?.delete()?.await()
//            } catch (err: Exception) {
//                Log.i("Authentication", "failed to delete user: ${err.message.toString()}")
//            }
//        }
//    }


}