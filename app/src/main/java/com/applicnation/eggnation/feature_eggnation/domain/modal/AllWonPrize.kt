package com.applicnation.eggnation.feature_eggnation.domain.modal


/**
 * @param winnerUserId (String) The Id of the user who won
 * @param prizeTitle (String) The title of the prize (iPhone 13, Mynza shirt, Promo code, etc...)
 * @param prizeDesc (String) The description of the prize (can and should be decently long)
 * @param prizeType (String) The type of the prize (phone, laptop, etc...). Used to determine what image to display to user
 * @param prizeTier (String) One of bronze, silver, gold, platinum, diamond
 */
data class AllWonPrize(
    var winnerUserId: String = "",
    var prizeTitle: String = "",
    var prizeDesc: String = "",
    var prizeType: String = "",
    var prizeTier: String = "",
)