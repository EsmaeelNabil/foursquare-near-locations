package com.esmaeel.challenge.data.repositories

import app.cash.turbine.test
import com.esmaeel.challenge.common.base.AppException
import com.esmaeel.challenge.data.remote.RemoteDataSource
import com.esmaeel.challenge.data.remote.models.RecommendationsResponse
import com.esmaeel.challenge.di.ResourcesHandler
import com.esmaeel.challenge.utils.TestContextProvider
import com.esmaeel.challenge.utils.UnhandledTestException
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException
import kotlin.test.assertEquals
import kotlin.test.assertIs

@RunWith(MockitoJUnitRunner::class)
class RepositoryTest {

    val UNKNOWN_ERROR = "UNKNOWN_ERROR"
    val NETWORK_ERROR = "NETWORK_ERROR"
    val NETWORK_ERROR_TIMEOUT = "NETWORK_ERROR_TIMEOUT"

    private val contextProviderTest = TestContextProvider()
    lateinit var repository: RecommendationsRepository

    @MockK
    lateinit var remoteDataSource: RemoteDataSource

    @MockK
    lateinit var resourcesHandler: ResourcesHandler

    @MockK
    lateinit var fakeResponse: RecommendationsResponse

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        repository = RecommendationsRepository(
            remoteDataSource = remoteDataSource,
            contextProvider = contextProviderTest,
            resourcesHandler = resourcesHandler
        )

        coEvery { resourcesHandler.UNKNOWN_ERROR } returns UNKNOWN_ERROR
        coEvery { resourcesHandler.NETWORK_ERROR } returns NETWORK_ERROR
        coEvery { resourcesHandler.NETWORK_ERROR_TIMEOUT } returns NETWORK_ERROR_TIMEOUT
    }

    @Test
    fun `get list with success response`() = runBlocking {
        coEvery { remoteDataSource.getRecommendations(0.0, 0.0) } returns fakeResponse
        repository.getRecommendations(0.0, 0.0).test {
            assertEquals(awaitItem(), fakeResponse)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `get list that throws a TimeoutCancellationException and returns a NETWORK_ERROR_TIMEOUT message`() =
        runBlocking {

            coEvery { remoteDataSource.getRecommendations(0.0, 0.0) } coAnswers {
                // throws a TimeoutCancellationException that we are handling in the base repository
                withTimeout(0) {
                    fakeResponse
                }
            }

            repository.getRecommendations(0.0, 0.0).test {
                val error = awaitError()
                assertIs<AppException>(error)
                assertEquals(error.message, NETWORK_ERROR_TIMEOUT)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `get list that throws IOException and returns a NETWORK_ERROR message`() =
        runBlocking {
            coEvery { remoteDataSource.getRecommendations(0.0, 0.0) } throws IOException()

            repository.getRecommendations(0.0, 0.0).test {
                val error = awaitError()
                assertIs<AppException>(error)
                assertEquals(error.message, NETWORK_ERROR)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `get list that throws an UnhandledTestException and returns an AppException with the UnhandledTestException's message`() =
        runBlocking {
            val expectedMessage = "expectedMessage"
            coEvery { remoteDataSource.getRecommendations(0.0, 0.0) } throws UnhandledTestException(
                expectedMessage
            )

            repository.getRecommendations(0.0, 0.0).test {
                val error = awaitError()
                assertIs<AppException>(error)
                assertEquals(error.message, expectedMessage)
                cancelAndIgnoreRemainingEvents()
            }
        }

}