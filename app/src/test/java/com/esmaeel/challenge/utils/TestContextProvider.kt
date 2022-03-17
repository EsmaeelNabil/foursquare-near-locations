package com.esmaeel.challenge.utils

import com.esmaeel.challenge.di.ContextProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlin.coroutines.CoroutineContext

class TestContextProvider : ContextProvider() {
    @OptIn(ExperimentalCoroutinesApi::class)
    val testDispatcher = UnconfinedTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    override val Main: CoroutineContext
        get() = testDispatcher

    @OptIn(ExperimentalCoroutinesApi::class)
    override val IO: CoroutineContext
        get() = testDispatcher
}


