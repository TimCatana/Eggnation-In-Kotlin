package com.applicnation.eggnationkotlin.models

import java.util.*
import kotlin.collections.ArrayList

class User (
    val username: String = "",
    val email: String = "",
    val prizes: ArrayList<Any> = ArrayList(),
    val dateCreated: Date = Date(),
)