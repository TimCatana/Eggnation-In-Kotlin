package com.applicnation.eggnation.util

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.activity.ComponentActivity

/**
 * Get the activity
 */
fun Context.getActivity(): Activity? = when (this) {
    is ComponentActivity -> this
    is ContextWrapper -> baseContext.getActivity() as Activity
    else -> null
}