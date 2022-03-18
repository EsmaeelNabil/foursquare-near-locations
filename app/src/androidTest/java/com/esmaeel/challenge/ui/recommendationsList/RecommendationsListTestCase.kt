package com.esmaeel.challenge.ui.recommendationsList

import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Test

/**
 *  so this is the entry point of our UI-TEST which defines what test cases we have on this feature
 *  every test case might have different scenarios and that is why we need a scenario to run.
 */
class RecommendationsListTestCase : TestCase() {
    @Test
    fun emptyListScenario() = run {
        step("request list for a place that does not have recommendations in the api") {
            scenario(RecommendationsEmptyListScenario())
        }
    }

    @Test
    fun listWithDataScenario() = run {
        step("request list for a place that have recommendations") {
            scenario(RecommendationsDataListScenario())
        }
    }
}