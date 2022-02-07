package com.applicnation.eggnation.feature_eggnation.data.firebase

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.lang.Exception

// TODO - check that scopes are working properly by logging the scope
class Authentication {
    private val auth = FirebaseAuth.getInstance()

    // TODO - set coroutines on IO thread?
    // TODO - also update username when registered
    fun createUserAccount(scope: CoroutineScope, email: String, password: String) {
        scope.launch(Dispatchers.IO) {
            try {
                auth.createUserWithEmailAndPassword(email, password).await()
            } catch (err: Exception) {
                Log.i("Authentication", "Failed to create account: ${err.message.toString()}")
            }
        }
    }

    fun getUserId(): String {
        return auth.currentUser?.uid ?: ""
    }

    fun signIn(scope: CoroutineScope, email: String, password: String) {
        scope.launch {
            try{
                auth.signInWithEmailAndPassword(email, password).await()
            } catch (err: Exception) {
                Log.i("Authentication", "Failed to signIn: ${err.message.toString()}")
            }
        }
    }

    fun signOut() {
        try{
            auth.signOut()
        } catch (err: Exception) {
            Log.i("Authentication", "Failed to signIn: ${err.message.toString()}")
        }
    }

    fun verifyUserEmail(scope: CoroutineScope, email: String, password: String) {
        scope.launch {
            try {
                // TODO - make a deep link in the email that will send the user to a specific fragment that will tell the app that the user has clicked the verification link
                auth.currentUser?.sendEmailVerification()?.await()
            } catch (err: Exception) {
                Log.i("Authentication", "Failed to send email verification: ${err.message.toString()}")
            }
        }
    }

    fun resetUserPasswordViaEmail(scope: CoroutineScope, email: String) {
        scope.launch {
            try {
                auth.sendPasswordResetEmail(email).await()
            } catch (err: Exception) {
                Log.i("Authentication", "Failed to send email: ${err.message.toString()}")
            }
        }
    }

    fun updateUserPassword(scope: CoroutineScope, newPassword: String) {
        scope.launch {
            try {
                auth.currentUser?.updatePassword(newPassword)?.await()
            } catch (err: Exception) {
                Log.i("Authentication", "Failed to change password: ${err.message.toString()}")
            }
        }
    }

    fun updateUserEmail(scope: CoroutineScope, newEmail: String) {
        scope.launch {
            try {
                auth.currentUser?.updateEmail(newEmail)?.await()
            } catch (err: Exception) {
                Log.i("Authentication", "Failed to change email: ${err.message.toString()}")
            }
        }

    }

    fun updateUserUserName(scope: CoroutineScope, newUsername: String) {
        val profileUpdates = userProfileChangeRequest {
            displayName = newUsername
        }

        scope.launch {
            try {
                auth.currentUser?.updateProfile(profileUpdates)?.await()
            } catch (err: Exception) {
                Log.i("Authentication", "Failed to update user display name: ${err.message.toString()}")
            }
        }
    }


    // TODO - docs say that the user needs to have recently signed in for this to work. I might need to reauthenticate when the user hits the delete button to
    // avoid this error. See https://firebase.google.com/docs/auth/android/manage-users#re-authenticate_a_user
    fun deleteUserAccout(scope: CoroutineScope) {
        scope.launch {
            try {
                auth.currentUser?.delete()?.await()
            } catch (err: Exception) {
                Log.i("Authentication", "failed to delete user: ${err.message.toString()}")
            }
        }
    }

}
