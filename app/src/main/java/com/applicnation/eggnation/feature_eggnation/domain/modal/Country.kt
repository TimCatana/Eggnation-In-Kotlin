package com.applicnation.eggnation.feature_eggnation.domain.modal

data class Countries(
    val countries: ArrayList<Country>
)

data class Country(
    val countryName: String = "Afghanistan",
    val countryShortCode: String = "AF",
    val regions: ArrayList<Region> = arrayListOf(
        Region("Badakhshan", "BDS"),
        Region("Badghis", "BDG"),
        Region("Baghlan", "BGL"),
        Region("Balkh", "BAL"),
        Region("Bamyan", "BAM"),
        Region("Daykundi", "DAY"),
        Region("Farah", "FRA"),
        Region("Faryab", "FYB"),
        Region("Ghazni", "GHA"),
        Region("Ghor", "GHO"),
        Region("Helmand", "HEL"),
        Region("Herat", "HER"),
        Region("Jowzjan", "JOW"),
        Region("Kabul", "KAB"),
        Region("Kandahar", "KAN"),
        Region("Kapisa", "KAP"),
        Region("Khost", "KHO"),
        Region("Kunar", "KNR"),
        Region("Kunduz", "KDZ"),
        Region("Laghman", "LAG"),
        Region("Logar", "LOW"),
        Region("Maidan Wardak", "WAR"),
        Region("Nangarhar", "NAN"),
        Region("Nimruz", "NIM"),
        Region("Nuristan", "NUR"),
        Region("Paktia", "PIA"),
        Region("Paktika", "PKA"),
        Region("Panjshir", "PAN"),
        Region("Parwan", "PAR"),
        Region("Samangan", "SAM"),
        Region("Sar-e Pol", "SAR"),
        Region("Takhar", "TAK"),
        Region("Urozgan", "ORU"),
        Region("Zabul", "ZAB")
    )
)


data class Region(
    val name: String = "Badakhshan",
    val shortCode: String = "BDS"
)


