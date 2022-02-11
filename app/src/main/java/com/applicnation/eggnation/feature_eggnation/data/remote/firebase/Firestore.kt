package com.applicnation.eggnation.feature_eggnation.data.remote.firebase

import android.util.Log
import com.applicnation.eggnation.feature_eggnation.domain.modal.Prize
import com.applicnation.eggnation.feature_eggnation.domain.modal.User
import com.applicnation.eggnation.util.Constants
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import java.lang.Exception

// TODO - check that scopes are working properly by logging the scope
// TODO - need to refactor this because coroutines are used differently in compose
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
    suspend fun registerUser(userInfo: User, userId: String) {
        val userDocument = firestore.collection(Constants.USERSCOL).document(userId)

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

    suspend fun updateUserEmail(userId: String, newEmail: String) {
        val userDocument = firestore.collection(Constants.USERSCOL).document(userId)

        try {
            userDocument.update("email", newEmail).await()
        } catch (err: Exception) {
            Log.i(TAG, "Failed to update user email: $err")
        }
    }

    suspend fun updateUserUsername(userId: String, newUserName: String) {
        val userDocument = firestore.collection(Constants.USERSCOL).document(userId)

        try {
            userDocument.update("username", newUserName).await()
        } catch (err: Exception) {
            Log.i(TAG, "Failed to update user email: $err")
        }
    }


    suspend fun addWonPrizeToUserAccount(
        prizeId: String,
        prizeType: String,
        prizeName: String,
        prizeDesc: String,
        priseTier: String,
        userId: String
    ) {
        val userDocument = firestore.collection(Constants.USERSCOL).document(userId)

        val prize = Prize().apply {
            this.prizeId = prizeId
            this.prizeName = prizeName
            this.prizeType = prizeType
            this.prizeDesc = prizeDesc
            this.prizeTier = prizeTier
        }

        try{
            userDocument.update("prizes", FieldValue.arrayUnion(prize)).await()
        } catch(err: Exception) {

        }
    }

//    fun getCurrentUserId(): String {
//        val currentUser = FirebaseAuth.getInstance().currentUser
//        return currentUser?.uid ?: ""
//    }

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
