package com.applicnation.eggnation.feature_eggnation.domain.modal

import java.util.*

/**
 * @param username (String) the user's username
 * @param email (String) The user's email
 * @param dateCreated (Date) The time the user's account was created
 * @note wonPrizes will be a collection added later to the database
 * @note In the future, if I want to use my own authentication features (I doubt I ever will), I will need to add:
 *       emailVerified: Boolean = false
 *       profilePicture: String = "" // should be the default profile picture uri
 *       salt: String = "" (this should be a hashed string)
 *       passwordWithSalt: String = "" (this should be a hashed string)
 */
class User (
    var username: String = "",
    var email: String = "",
    var dateCreated: Date = Date(),
)
