package com.applicnation.eggnation.feature_eggnation.domain.modal

// TODO - might add the images I want to show in cloud storage
// TODO - might need two models, one for won prizes and one for available prizes
/**
 * @param prizeId (String) The id of the prize. Should be a randon long string
 * @param prizeName (String) The name of the prize (e.g. "Airpods Pro" or "Pixel 6")
 * @param prizeType (String) The type of prize (e.g. "phone", "laptop" or "clothing")
 * @param prizeDescription (String) The description of the prize (specs, etc...)
 * @param prizeTier (String) One of a list of tier types (Bronze, Silver, Gold, Platinum)
 */
data class AvailablePrize (
    var prizeId: String = "",
    var prizeName: String = "",
    var prizeType: String = "",
    var prizeDesc: String = "",
    var prizeTier: String = "",
)

data class WonPrize (
    var prizeId: String = "",
    var prizeName: String = "",
    var prizeType: String = "",
    var prizeDesc: String = "",
    var prizeTier: String = "",
    var prizeClaimed: Boolean = false
    // TODO add prizedateWon
)



//object WonPrizeSupplier {
//    val wonPrizes = arrayListOf(
//        Prize("c83mar983rredhfm8", "macbook", "laptop"),
//        Prize("c83masfsr3r98hfm8", "iPhone", "phone"),
//        Prize("c83marfdse398hfm8", "iPhone", "phone"),
//        Prize("3rfsefgh45thgefsf", "macbook", "laptop"),
//        Prize("c83mar93r3ee8hfm8", "macbook", "laptop"),
//        Prize("c83marfdse398hfm8", "iPhone", "phone"),
//        Prize("3rfsefgh45thgefsf", "macbook", "laptop"),
//    )
//}