package com.applicnation.eggnation.util

import android.app.Activity
import android.net.Uri
import android.webkit.MimeTypeMap

object Constants {
    const val SPLASH_SCREEN_DURATION = 0L


    /**
     * Firestore Collection Names
     */
    const val USERS_COLLECTION: String = "users"
    const val WON_PRIZE_COLLECTION: String = "wonPrizes"

    /**
     * Firestore Document Names
     */
    const val USER_EMAIL_FIELD: String = "email"
    const val USER_USERNAME_FIELD: String = "username"
    const val USER_DATE_CREATED_FIELD: String = "dateCreated"

    /**
     * Realtime Database Nodes
     */
    const val PRIZE_NODE = "availablePrizes"
    const val GLOBAL_COUNTER_NODE = "globalCount"


    const val APP_PREFERENCES: String = "AppPrefs"
    const val USERNAME: String = "logged_in_username"

//    fun getFileExtension(activity: Activity, uri: Uri?): String? {
//        return MimeTypeMap
//               .getSingleton()
//               .getExtensionFromMimeType(activity.contentResolver.getType(uri!!))
//    }

}