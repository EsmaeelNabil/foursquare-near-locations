package com.esmaeel.challenge.ui.recommendationsList

import androidx.core.os.bundleOf
import com.esmaeel.challenge.testUtils.goto
import com.kaspersky.kaspresso.testcases.api.scenario.Scenario
import com.kaspersky.kaspresso.testcases.core.testcontext.TestContext

/**
 * this is the ui test it self
 * this time we are testing when the list is empty
 */
class RecommendationsEmptyListScenario() : Scenario() {
    val listScreen = RecommendationsListScreen()
    private val emptyListCoordinates = bundleOf(
        RecommendationsListActivity.LAT to 42.45698,
        RecommendationsListActivity.LONG to 4.641453139340504
    )


    override val steps: TestContext<Unit>.() -> Unit = {
        step("open screen and check for coordinates in intent extras") {
            val activity = goto(RecommendationsListActivity::class.java, emptyListCoordinates)
            flakySafely(2_000) {
                activity.onActivity {
                    with(it.intent) {
                        assert(hasExtra(RecommendationsListActivity.LAT))
                        assert(hasExtra(RecommendationsListActivity.LONG))
                    }
                }
            }
        }


        listScreen {
            step("assert that the recycler view is Gone therefore no data and empty view is shown ") {
                flakySafely(2_000) {
                    list {
                        doesNotExist()
                    }
                }
            }

        }
    }

}