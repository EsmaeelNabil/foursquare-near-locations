package com.esmaeel.challenge.data.remote

import com.esmaeel.challenge.utils.ktx.getFormattedApiDate
import javax.inject.Inject
import javax.inject.Singleton

/**
 * this is responsible for getting the recommendations from the network
 */
@Singleton
class RemoteDataSource @Inject constructor(private val remoteService: PlacesService) {
    suspend fun getRecommendations(latitude: Double, longitude: Double) =
        remoteService.getVenueRecommendations(
            hashMapOf(
                PlacesService.DATE_KEY to getFormattedApiDate(),
                PlacesService.LAT_LONG to "$latitude,$longitude"
            )
        )
}

