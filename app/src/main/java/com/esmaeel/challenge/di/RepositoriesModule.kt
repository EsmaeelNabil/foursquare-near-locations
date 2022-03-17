package com.esmaeel.challenge.di

import com.esmaeel.challenge.data.remote.RemoteDataSource
import com.esmaeel.challenge.data.repositories.RecommendationsRepository
import com.esmaeel.challenge.domain.repositories.IRecommendationsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoriesModule {


    @Provides
    @Singleton
    fun provideRecommendationsRepository(
        remoteDataSource: RemoteDataSource,
        contextProvider: ContextProvider,
        resourcesHandler: ResourcesHandler
    ): IRecommendationsRepository {
        return RecommendationsRepository(
            remoteDataSource = remoteDataSource,
            contextProvider = contextProvider,
            resourcesHandler = resourcesHandler
        )
    }

}
