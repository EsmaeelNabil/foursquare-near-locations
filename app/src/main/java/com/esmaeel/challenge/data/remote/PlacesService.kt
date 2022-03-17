package com.esmaeel.challenge.data.remote

import com.esmaeel.challenge.common.base.INJECT_API_KEY
import com.esmaeel.challenge.data.remote.models.RecommendationsResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface PlacesService {
    companion object {
        const val NEARBY_PLACES_API = "places/nearby"
        const val AUTHORIZATION = "Authorization"
        const val DATE_KEY = "v"
        const val LAT_LONG = "ll"
        const val DATE_PATTERN = "yyyyMMdd"
    }

    /**
     * Get venue recommendations.
     * See [the docs](https://developer.foursquare.com/reference/places-nearby)
     */
    @INJECT_API_KEY
    @GET(NEARBY_PLACES_API)
    suspend fun getVenueRecommendations(@QueryMap query: Map<String, String>): RecommendationsResponse

}
