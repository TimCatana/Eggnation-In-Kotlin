package com.applicnation.eggnation.feature_eggnation.domain.modal

import java.util.*

/**
 * @param prizeId (String) A unique string id
 * @param prizeTitle (String) The title of the prize (iPhone 13, Mynza shirt, Promo code, etc...)
 * @param prizeDesc (String) The description of the prize (can and should be decently long)
 * @param prizeType (String) The type of the prize (phone, laptop, etc...). Used to determine what image to display to user
 * @param prizeTier (String) One of bronze, silver, gold, platinum, diamond
 * @param prizeDateWon (Date) The date that the prize was won
 * @param prizeClaimed (Boolean) Whether the prize was claimed or not
 */
data class WonPrize(
   var prizeId: String = "",
   var prizeTitle: String = "",
   var prizeDesc: String = "",
   var prizeType: String = "",
   var prizeTier: String = "",
   var prizeDateWon: Date = Date(),
   var prizeClaimed: Boolean = false,
)
