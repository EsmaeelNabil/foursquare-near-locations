package com.esmaeel.challenge.ui.recommendationsList

import com.esmaeel.challenge.common.base.BaseViewModel
import com.esmaeel.challenge.common.base.ViewState
import com.esmaeel.challenge.data.remote.models.GetRecommendationsInputData
import com.esmaeel.challenge.di.ContextProvider
import com.esmaeel.challenge.domain.usecases.GetRecommendationsUseCase
import com.esmaeel.challenge.utils.PermissionsUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecommendationsViewModel @Inject constructor(
    private val getRecommendationsUseCase: GetRecommendationsUseCase,
    private val permissionsUtils: PermissionsUtil,
    contextProvider: ContextProvider
) : BaseViewModel(contextProvider) {

    fun getRecommendationsList(lat: Double, lng: Double) {
        if (permissionsUtils.hasLocationsPermissions())
            getRecommendationsData(lat, lng)
        else setState(RecommendationsListViewState.OnShouldRequestPermission)
    }

    private fun getRecommendationsData(lat: Double, lng: Double) = launchBlock {
        getRecommendationsUseCase.invoke(GetRecommendationsInputData(lat, lng)).collect {
            setState(
                if (it.results.isEmpty())
                    ViewState.Empty
                else
                    RecommendationsListViewState.OnRecommendationsListResponse(it.results)
            )
        }
    }

}