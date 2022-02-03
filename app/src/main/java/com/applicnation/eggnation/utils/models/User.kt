package com.applicnation.eggnation.utils.models

import java.util.*
import kotlin.collections.ArrayList

class User (
    var username: String = "",
    var email: String = "",
    var prizes: ArrayList<Any> = ArrayList(),
    var dateCreated: Date = Date(),
)