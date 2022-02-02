package com.applicnation.eggnationkotlin.firebase

import android.app.Activity
import android.util.Log
import com.applicnation.eggnation.models.Prize
import com.applicnation.eggnation.models.User
import com.applicnation.eggnation.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.lang.Exception

// TODO - check that scopes are working properly by logging the scope
class Firestore {
    private val TAG = "FireStoreClass"
    private val firestore = FirebaseFirestore.getInstance()

    /**
     * Registers the user to the FireStore database.
     * @param scope The coroutine scope. the coroutine lifecycle should be as long as the fragment
     *              or viewmodel
     * @param userInfo The User object
     * @param userId The User Id generated by Firebase Authentication. Used as the document name
     */
    fun registerUser(scope: CoroutineScope, userInfo: User, userId: String) {
        val userDocument = firestore.collection(Constants.USERSCOL).document(userId)

        scope.launch(Dispatchers.IO) {
            Log.i(TAG, "adding user to database via coroutine")
            try {
                userDocument
                    .set(userInfo, SetOptions.merge())
                    .await()
                Log.i(TAG, "Successfully added user to firestore")
            } catch (err: Exception) {
                Log.i(TAG, "Failed to add user to firestore: $err")
            }
        }
    }

    fun updateUserEmail(scope: CoroutineScope, userId: String, newEmail: String) {
        val userDocument = firestore.collection(Constants.USERSCOL).document(userId)

        scope.launch(Dispatchers.IO) {
            try {
                userDocument.update("email", newEmail).await()
            } catch (err: Exception) {
                Log.i(TAG, "Failed to update user email: $err")
            }
        }
    }

    fun updateUserUserName(scope: CoroutineScope, userId: String, newUserName: String) {
        val userDocument = firestore.collection(Constants.USERSCOL).document(userId)

        scope.launch(Dispatchers.IO) {
            try {
                userDocument.update("username", newUserName).await()
            } catch (err: Exception) {
                Log.i(TAG, "Failed to update user email: $err")
            }
        }
    }

    fun getCurrentUserId(): String {
        val currentUser = FirebaseAuth.getInstance().currentUser
        return currentUser?.uid ?: ""
    }

    fun addWonPrizeToUserAccount(scope: CoroutineScope, prizeId: String,  prizeType: String, prizeName: String, userId: String) {
        val userDocument = firestore.collection(Constants.USERSCOL).document(userId)

        val prize = Prize().apply {
            this.prizeId = prizeId
            this.prizeName = prizeName
            this.prizeType = prizeType
        }
        userDocument.update("prizes", FieldValue.arrayUnion(prize))
    }


}



// If you want to use cloud storage
//fun uploadImageToCloudStorage(activity: Activity, imageFileURI: Uri?) {
//    val sRef: StorageReference = FirebaseStorage.getInstance.reference.child(
//        COnstants.USER_PROFILE_IMAGE + System.currentTimMillis() + "." + Constrants.getFileExtension(activity, imageFileURI)
//    )

//sRef.putFile(imageFileUri!!)
//.addOnSuccessListener { task ->
//
//    Log(task.metadata!!.reference!!.downloadUrl.toString())
//
//    it.metadata!!.reference!!.downoadUrl.addOnSuccessListener {uri -> Log(uri.tostring)}
//
//}
//.addOnFailureListener { e ->
//
//    when (activity) {
//        is <activituyName -> {do something}
//    }
//
//    Log(activity.javaClass.simplename, e.message, e)
//}
//}
