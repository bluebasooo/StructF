package ru.mirea.structf

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import org.hamcrest.Matchers.allOf
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.mirea.structf.ui.MainActivity


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4ClassRunner::class)
class ExampleInstrumentedTest {

    @get: Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test fun test_isActivityInView() = onView(withId(R.id.activity_main)).check(matches(isDisplayed()))

    @Test fun test_MainFragmentInView() = onView(withId(R.id.main_fragment_view)).check(matches(isDisplayed()))

    @Test
    fun test_RecentDocsInView() {
        onView(withId(R.id.ic1)).check(
            matches(withChild(withId(R.id.recent_text)))
        )
    }

    @Test fun test_isScaleOnView() = onView(withId(R.id.vertical_progressbar)).check(matches(isDisplayed()))

    @Test fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("ru.mirea.structf", appContext.packageName)
    }

}