package com.esmaeel.challenge.ui.recommendationsList

import app.cash.turbine.test
import com.esmaeel.challenge.common.base.ViewState
import com.esmaeel.challenge.data.emptyResponse
import com.esmaeel.challenge.data.remote.models.GetRecommendationsInputData
import com.esmaeel.challenge.data.response
import com.esmaeel.challenge.domain.usecases.GetRecommendationsUseCase
import com.esmaeel.challenge.utils.PermissionsUtil
import com.esmaeel.challenge.utils.TestContextProvider
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertEquals
import kotlin.test.assertIs

@RunWith(MockitoJUnitRunner::class)
class RecommendationsViewModelTest {

    private lateinit var viewModel: RecommendationsViewModel
    private val contextProvidersTest = TestContextProvider()

    @MockK
    lateinit var useCase: GetRecommendationsUseCase

    @MockK
    lateinit var permissionsUtil: PermissionsUtil

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        viewModel = RecommendationsViewModel(
            getRecommendationsUseCase = useCase,
            contextProvider = contextProvidersTest,
            permissionsUtils = permissionsUtil
        )
    }

    @Test
    fun `try to get data without permissions return permissions required state`() = runTest {
        every { permissionsUtil.hasLocationsPermissions() } returns false

        viewModel.getRecommendationsList(0.0, 0.0)

        viewModel.state.test {
            val state = awaitItem()
            assertIs<RecommendationsListViewState.OnShouldRequestPermission>(state)
            cancelAndIgnoreRemainingEvents()
        }

    }

    @Test
    fun `get data with permissions accepted returns empty list of data`() = runTest {
        every { permissionsUtil.hasLocationsPermissions() } returns true
        coEvery {
            useCase.invoke(
                GetRecommendationsInputData(
                    0.0,
                    0.0
                )
            )
        } returns flowOf(emptyResponse)

        viewModel.getRecommendationsList(0.0, 0.0)

        viewModel.state.test {
            val state = awaitItem()
            assertIs<ViewState.Empty>(state)
            cancelAndIgnoreRemainingEvents()
        }

    }

    @Test
    fun `get data with permissions accepted returns list of data`() = runTest {
        every { permissionsUtil.hasLocationsPermissions() } returns true
        coEvery {
            useCase.invoke(
                GetRecommendationsInputData(
                    0.0,
                    0.0
                )
            )
        } returns flowOf(response)

        viewModel.getRecommendationsList(0.0, 0.0)

        viewModel.state.test {
            val state = awaitItem()
            assertIs<RecommendationsListViewState.OnRecommendationsListResponse>(state)
            assertEquals(response.results, state.result)
            cancelAndIgnoreRemainingEvents()
        }

    }
}