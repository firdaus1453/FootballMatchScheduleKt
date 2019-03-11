package me.firdaus1453.footballmatchschedulekt.main


import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.swipeLeft
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import android.view.ViewGroup
import me.firdaus1453.footballmatchschedulekt.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class TabSwipeBottomNav {

    val time: Long = 5000

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun tabSwipeBottomNav() {
        Thread.sleep(time)

        val tabView = onView(
            allOf(
                withContentDescription("Last"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tabs_match),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        tabView.perform(click())

        Thread.sleep(time)

        val tabView2 = onView(
            allOf(
                withContentDescription("Next"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tabs_match),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        tabView2.perform(click())

        Thread.sleep(time)

        val viewPager = onView(
            allOf(
                withId(R.id.viewpager_match),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.container),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        viewPager.perform(swipeLeft())

        Thread.sleep(time)

        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.navigation_next), withContentDescription("Teams"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.navigation),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())
        Thread.sleep(time)

        val bottomNavigationItemView2 = onView(
            allOf(
                withId(R.id.navigation_favorite), withContentDescription("Favorites Match"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.navigation),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView2.perform(click())
        Thread.sleep(time)

        val tabView3 = onView(
            allOf(
                withContentDescription("Team"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tabs_favorite),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        tabView3.perform(click())
        Thread.sleep(time)

        val viewPager2 = onView(
            allOf(
                withId(R.id.viewpager_favorite),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.container),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        viewPager2.perform(swipeLeft())
        Thread.sleep(time)

        val bottomNavigationItemView3 = onView(
            allOf(
                withId(R.id.navigation_past), withContentDescription("Matchs"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.navigation),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView3.perform(click())
        Thread.sleep(time)

    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
