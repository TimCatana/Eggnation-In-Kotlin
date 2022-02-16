package com.applicnation.eggnation.feature_eggnation.domain.modal

// TODO - might add the images I want to show in cloud storage
// TODO - might need two models, one for won prizes and one for available prizes
/**
 * @param prizeId (String) A unique string id
 * @param prizeTitle (String) The title of the prize (iPhone 13, Mynza shirt, Promo code, etc...)
 * @param prizeDesc (String) The description of the prize (can and should be decently long)
 * @param prizeType (String) The type of the prize (phone, laptop, etc...). Used to determine what image to display to user
 * @param prizeTier (String) One of bronze, silver, gold, platinum, diamond
 */
data class AvailablePrize (
    var prizeId: String = "",
    var prizeTitle: String = "",
    var prizeDesc: String = "",
    var prizeType: String = "",
    var prizeTier: String = "",
)
