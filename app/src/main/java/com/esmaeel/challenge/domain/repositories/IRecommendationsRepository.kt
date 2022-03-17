package com.esmaeel.challenge.domain.repositories

import com.esmaeel.challenge.data.remote.models.RecommendationsResponse
import kotlinx.coroutines.flow.Flow

interface IRecommendationsRepository {
    fun getRecommendations(lat: Double, lng: Double): Flow<RecommendationsResponse>
}