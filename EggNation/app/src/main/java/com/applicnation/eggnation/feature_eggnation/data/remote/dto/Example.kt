package com.applicnation.eggnation.feature_eggnation.data.remote.dto

/**
 * in this pagace, put data models that will be recieved from databases or apis
 */
data class ExampleDto(
    val lol: String,
    val lol2: String,
    val lol3: String
)

/**
 * usually fetching data from api or database gives you more data then you need
 * this function will return a model (that you created in domain/model) that will
 * use only the necessary data fetched from the database or api and stored in the above
 * data class
 */
//fun ExampleDto.toExample(): ExampleModel {
//    return ExampleModel(
//        lol1 = lol1,
//        lol3 = lol3
//    )
//}