package com.applicnation.eggnationkotlin.firestore

import android.app.Activity
import android.util.Log
import com.applicnation.eggnationkotlin.ui.activities.RegisterActivity
import com.applicnation.eggnationkotlin.models.User
import com.applicnation.eggnationkotlin.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class FirestoreClass {
    private val firestore = FirebaseFirestore.getInstance()

    fun registerUser(activity: RegisterActivity, userInfo: User, userId: String) {

        firestore.collection(Constants.USERSCOL)
            .document(userId)
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
//                activity.userRegistrationSuccess() <- function from Registration activity
            }
            .addOnFailureListener {
//                activity.hideProgressDialog() <- function from activity
//                log error
            }

    }

    fun getCurrentUserId(): String {
        val currentUser = FirebaseAuth.getInstance().currentUser

        var currentUserId = ""
        if (currentUser != null) {
            currentUserId = currentUser.uid
        }

        return currentUserId
    }

    fun getUserDetails(activity: Activity) {
        firestore.collection(Constants.USERSCOL)
            .document(getCurrentUserId())
            .get()
            .addOnSuccessListener {
                Log.i(activity.javaClass.simpleName, it.toString())

                val user = it.toObject(User::class.java)!!

                // Similar to async storage
//                val sharedPreferences = activity.getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE)
//                val editor: SharedPreferences.Editor = sharedPreferences.edit()
//                editor.putString(Constants.USERNAME, "${user.username}")
//                editor.apply()

//                TO GET THE SHARED PREFERENCES FROM SOMEWHERE ELSE USE:
//                val sharedPreferences = getSharedPreferences(preferences-key, Context)
//                val username = sharedPreferences.getString(key, default value)!!


            }
    }

//    }

}


// update prizes
//    fun updateUserProfileData(activity: Activity, userHashMap: HashMap<String, Any>) {
//        firestore.collection(Constants.USERSCOL)
//            .document(getCurrentUserId())
//            .update(userHashMap)
//            .addOnSuccessListener {
//
//            }
//            .addOnFailureListener{
//
//            }

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
