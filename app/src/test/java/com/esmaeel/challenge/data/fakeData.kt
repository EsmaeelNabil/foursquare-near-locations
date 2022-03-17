package com.esmaeel.challenge.data

import com.esmaeel.challenge.data.remote.models.*

val response = RecommendationsResponse(
    results = listOf(
        RecommendationResult(
            categories = listOf(), distance = 0, geocode = GeoCode(
                main = Main(
                    latitude = 0.0,
                    longitude = 0.0
                )
            ), location = Location(
                address = "",
                country = "",
                locality = "",
                neighbourhood = listOf(),
                postcode = "",
                region = ""
            ), name = "", timezone = ""
        )
    )
)
val emptyResponse = RecommendationsResponse(results = listOf())
