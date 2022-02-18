package com.applicnation.eggnation.feature_eggnation.data.remote.firebase

import com.applicnation.eggnation.feature_eggnation.domain.modal.User
import com.applicnation.eggnation.feature_eggnation.domain.modal.WonPrize
import com.applicnation.eggnation.util.Constants
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import java.lang.Exception
import kotlin.collections.ArrayList

// TODO - check that scopes are working properly by logging the scope
// TODO - need to refactor this because coroutines are used differently in compose
class Firestore {
    private val firestore = FirebaseFirestore.getInstance()

    /**
     * Registers the user to the FireStore database.
     * @param userInfo The User object
     * @param userId The User Id generated by Firebase Authentication. Used as the document name
     * @note Firestore will use a cached local database if phone goes offline and then re-syncs the online database at a later date.
     *       Because of this, Firestore writes should hardly ever fail
     * @note Errors are caught and dealt with in use-case
     */
    suspend fun registerUser(userId: String, userEmail: String, userUsername: String) {
        val user = User().apply {
            this.email = userEmail
            this.username = userUsername
        }

        firestore
            .collection(Constants.USERS_COLLECTION)
            .document(userId)
            .set(user)
            .await()
    }

    /**
     * Updates the user email in FireStore.
     * @param userId The user's user ID (usually always from Firebase auth). user documents are named after the user's uid
     * @param newEmail The new email the email field should be updated to
     * @note Errors are caught and dealt with in use-case
     */
    suspend fun updateUserEmail(userId: String, newEmail: String) {
        firestore
            .collection(Constants.USERS_COLLECTION)
            .document(userId)
            .update(Constants.USER_EMAIL_FIELD, newEmail)
            .await()
    }

    /**
     * Updates the user username in FireStore.
     * @param userId The user's user ID (usually always from Firebase auth). user documents are named after the user's uid
     * @param newUsername The new username the email field should be updated to
     * @note Errors are caught and dealt with in use-case
     */
    suspend fun updateUserUsername(userId: String, newUsername: String) {
        firestore
            .collection(Constants.USERS_COLLECTION)
            .document(userId)
            .update(Constants.USER_USERNAME_FIELD, newUsername)
            .await()
    }

    suspend fun deleteUser() {
        // TODO - The docs reccommend using a cloud function to delete database information plus client side deletions are a secutiry hazard
    }

    /**
     * Adds a prize to the user's account.
     * @param userId The uid of the user used to add the prize to the correct account in firestore
     * @param prizeId The unique id of the prize won
     * @param prizeTitle The title of the prize
     * @param prizeDesc The description of the prize
     * @param prizeTier The tier of the prize
     * @note prizeDateWon in prize object will default to current time
     * @note prizeClaimed in prize object will default to false
     * @note Errors are caught and dealt with in use-case
     */
    suspend fun addWonPrizeToUserAccount(
        userId: String,
        prizeId: String,
        prizeTitle: String,
        prizeDesc: String,
        prizeTier: String,
    ) {
        val prizeDocument = firestore.collection(Constants.USERS_COLLECTION)
            .document(userId)
            .collection(Constants.WON_PRIZE_COLLECTION)
            .document(prizeId)

        val prize = WonPrize().apply {
            this.prizeId = prizeId
            this.prizeTitle = prizeTitle
            this.prizeDesc = prizeDesc
            this.prizeTier = prizeTier
        }

        prizeDocument.set(prize).await()
    }

    /**
     * Gets the list of all won prizes for the user's account.
     * @param userId The user's user ID (usually always from Firebase auth). documents are named after the user's uid
     * @note Errors are caught and dealt with in use-case
     * @return ArrayList<WonPrize> The list of won prizes
     */
    suspend fun getAllWonPrizes(userId: String): ArrayList<WonPrize> {
        val prizeList = ArrayList<WonPrize>()
        val prizesSnapshot = firestore
            .collection(Constants.USERS_COLLECTION)
            .document(userId)
            .collection(Constants.WON_PRIZE_COLLECTION)
            .get()
            .await()

        prizesSnapshot.forEach {
            val prize = it.toObject(WonPrize::class.java)
            prizeList.add(prize)
        }

        return prizeList
    }

    /**
     * Gets a specific won prize for the user's account by it's prizeId
     * @param userId The user's user ID (usually always from Firebase auth). user documents are named after the user's uid
     * @param prizeId The prize's prize ID (I will always have gotten a list of all prizes before I get a specific prize. Each prize
     *                document has the prizeId contained inside it, and that is where I get the ID from). prize documents are names
     *                after the prize's id
     * @note Errors are caught and dealt with in use-case
     * @return A single WonPrize object fetched from the database
     */
    suspend fun getWonPrizeById(userId: String, prizeId: String): WonPrize? {
        val prizeSnapshot = firestore
            .collection(Constants.USERS_COLLECTION)
            .document(userId)
            .collection(Constants.WON_PRIZE_COLLECTION)
            .document(prizeId)
            .get()
            .await()

        return prizeSnapshot.toObject(WonPrize::class.java)
    }

    /**
     * Updates the prize claimed boolean for the prize in the user's account.
     * This should only ever update the prize claimed field to "true"
     * @param userId The user's user ID (usually always from Firebase auth). user documents are named after the user's uid
     * @param prizeId The prize's prize ID (I will always have gotten a list of all prizes before I get a specific prize. Each prize
     *                document has the prizeId contained inside it, and that is where I get the ID from). prize documents are names
     *                after the prize's id
     * @note Errors are caught and dealt with in use-case
     */
    suspend fun updateWonPrizeClaimed(userId: String, prizeId: String, prizeClaimed: Boolean) {
        firestore
            .collection(Constants.USERS_COLLECTION)
            .document(userId)
            .collection(Constants.WON_PRIZE_COLLECTION)
            .document(prizeId)
            .update(Constants.PRIZE_CLAIMED_FIELD, prizeClaimed)
            .await()
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
