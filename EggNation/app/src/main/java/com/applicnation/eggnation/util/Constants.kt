package com.applicnation.eggnation.util

import android.app.Activity
import android.net.Uri
import android.webkit.MimeTypeMap
import com.applicnation.eggnation.R

object Constants {
    const val SPLASH_SCREEN_DURATION = 0L

    /**
     * Firestore Collection Names
     */
    const val USERS_COLLECTION: String = "users"
    const val WON_PRIZE_COLLECTION: String = "wonPrizes"
    const val ALL_WON_PRIZE_COLLECTION: String = "allWonPrizes"

    /**
     * Firestore Document Field Names
     */
    const val USER_EMAIL_FIELD: String = "email"
    const val USER_USERNAME_FIELD: String = "username"
    const val USER_DATE_CREATED_FIELD: String = "dateCreated"
    const val PRIZE_CLAIMED_FIELD: String = "prizeClaimed"

    /**
     * Realtime Database Nodes
     */
    const val PRIZE_NODE = "availablePrizes"
    const val GLOBAL_COUNTER_NODE = "globalCount"

    /**
     * Preferences Constants
     */
    const val PREFS_NAME: String = "AppPrefs"
    const val PREFS_SELECTED_SKIN_DEFAULT: Int = R.drawable.egg
    const val PREFS_TAP_COUNT_DEFAULT: Int = 1000
    const val PREFS_LAST_RESET_TIME_DEFAULT: Long = 0
    const val PREFS_RECEIVES_NOTIFICATIONS_DEFAULT: Boolean = true


//    const val USERNAME: String = "logged_in_username"

//    fun getFileExtension(activity: Activity, uri: Uri?): String? {
//        return MimeTypeMap
//               .getSingleton()
//               .getExtensionFromMimeType(activity.contentResolver.getType(uri!!))
//    }

}