package com.esmaeel.challenge.ui.recommendationsList

import android.view.View
import com.esmaeel.challenge.R
import com.esmaeel.challenge.data.remote.models.RecommendationResult
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.image.KImageView
import io.github.kakaocup.kakao.recycler.KRecyclerItem
import io.github.kakaocup.kakao.recycler.KRecyclerView
import io.github.kakaocup.kakao.text.KTextView
import org.hamcrest.Matcher


/**
 * consider this as a representation for our screen and addons/helper/extension functions
 * that helps us with testing it
 * find more about how to use this approach here : https://github.com/KakaoCup/Kakao
 */
class RecommendationsListScreen() : KScreen<RecommendationsListScreen>() {
    override val layoutId: Int = R.layout.recommendations_list_activity
    override val viewClass: Class<*> = RecommendationsListActivity::class.java

    val list = KRecyclerView(
        builder = { withId(R.id.recycler) },
        itemTypeBuilder = { itemType(::Item) },
    )

    class Item(parent: Matcher<View>) : KRecyclerItem<RecommendationResult>(parent) {
        val name = KTextView(parent) { withId(R.id.nameTv) }
        val logo = KImageView(parent) { withId(R.id.image) }
    }

}


