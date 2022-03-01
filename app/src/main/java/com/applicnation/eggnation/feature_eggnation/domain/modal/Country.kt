package com.applicnation.eggnation.feature_eggnation.domain.modal

data class Countries(
    val countries: ArrayList<Country>
)

data class Country(
    val countryName: String,
    val countryShortCode: String,
    val regions: ArrayList<Region>
)

data class Region(
    val name: String,
    val shortCode: String
)