package com.esmaeel.challenge.testUtils

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView
import org.hamcrest.Matcher


fun <T : Activity> goto(to: Class<T>): ActivityScenario<T> {
    return ActivityScenario.launch(to)
}

fun <T : Activity> goto(to: Class<T>, bundle: Bundle? = null): ActivityScenario<T> {
    val context = InstrumentationRegistry.getInstrumentation().targetContext
    return ActivityScenario.launch<T>(
        Intent(context, to).also {
            bundle?.let(it::putExtras)
        }
    )
}


fun KTextView.getText(): String {
    var text = String()
    this.view.interaction.perform(object : ViewAction {
        override fun getConstraints(): Matcher<View> {
            return ViewMatchers.isAssignableFrom(TextView::class.java)
        }

        override fun getDescription(): String {
            return "Text of the view"
        }

        override fun perform(uiController: UiController, view: View) {
            text = (view as TextView).text.toString()
        }
    })
    return text
}

fun KButton.getText(): String {
    var text = String()
    this.view.interaction.perform(object : ViewAction {
        override fun getConstraints(): Matcher<View> {
            return ViewMatchers.isAssignableFrom(TextView::class.java)
        }

        override fun getDescription(): String {
            return "Text of the view"
        }

        override fun perform(uiController: UiController, view: View) {
            text = (view as Button).text.toString()
        }
    })
    return text
}