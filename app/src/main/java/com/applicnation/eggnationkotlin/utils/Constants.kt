package com.applicnation.eggnationkotlin.utils

import android.app.Activity
import android.net.Uri
import android.webkit.MimeTypeMap

object Constants {
    const val KEY = "aKey"
    const val USERSCOL: String = "users"
    const val PRIZESDOC: String = "prizes"

    const val APP_PREFERENCES: String = "AppPrefs"
    const val USERNAME: String = "logged_in_username"

//    fun getFileExtension(activity: Activity, uri: Uri?): String? {
//        return MimeTypeMap
//               .getSingleton()
//               .getExtensionFromMimeType(activity.contentResolver.getType(uri!!))
//    }

}