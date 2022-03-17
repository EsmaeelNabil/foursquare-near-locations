package com.esmaeel.challenge.data.repositories

import com.esmaeel.challenge.common.base.BaseRepository
import com.esmaeel.challenge.data.remote.RemoteDataSource
import com.esmaeel.challenge.di.ContextProvider
import com.esmaeel.challenge.di.ResourcesHandler
import com.esmaeel.challenge.domain.repositories.IRecommendationsRepository

class RecommendationsRepository(
    private val remoteDataSource: RemoteDataSource,
    contextProvider: ContextProvider,
    resourcesHandler: ResourcesHandler
) : BaseRepository(contextProvider, resourcesHandler), IRecommendationsRepository {
    override fun getRecommendations(lat: Double, lng: Double) = networkHandler {
        remoteDataSource.getRecommendations(lat, lng)
    }
}