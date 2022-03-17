package com.esmaeel.challenge.ui.recommendationsList

import com.esmaeel.challenge.common.base.ViewState
import com.esmaeel.challenge.data.remote.models.RecommendationResult

sealed class RecommendationsListViewState : ViewState() {
    data class OnRecommendationsListResponse(val data: List<RecommendationResult>) :
        Loaded<List<RecommendationResult>>(data)

    object OnShouldRequestPermission : RecommendationsListViewState()
}