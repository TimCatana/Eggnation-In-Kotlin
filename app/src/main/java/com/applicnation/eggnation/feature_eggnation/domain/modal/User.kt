package com.applicnation.eggnation.feature_eggnation.domain.modal

import java.util.*
import kotlin.collections.ArrayList

/**
 * @param username (String) the user's username
 * @param email (String) The user's email
 * @param prizes (ArrayList<Prizes>) The user's won prizes
 * @param dataCreated (Date) The time the user's account was created
 */
class User (
    var username: String = "",
    var email: String = "",
    var prizes: ArrayList<Any> = ArrayList(),
    var dateCreated: Date = Date(),
)