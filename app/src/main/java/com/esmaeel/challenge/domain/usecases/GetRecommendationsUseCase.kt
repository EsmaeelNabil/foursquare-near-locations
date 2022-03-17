package com.esmaeel.challenge.domain.usecases

import com.esmaeel.challenge.data.remote.models.GetRecommendationsInputData
import com.esmaeel.challenge.data.remote.models.RecommendationsResponse
import com.esmaeel.challenge.domain.repositories.IRecommendationsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetRecommendationsUseCase @Inject constructor(private val repository: IRecommendationsRepository) :
    UseCase<Flow<RecommendationsResponse>, GetRecommendationsInputData> {
    override suspend fun invoke(input: GetRecommendationsInputData): Flow<RecommendationsResponse> {
        return repository.getRecommendations(input.lat, input.lng)
    }
}