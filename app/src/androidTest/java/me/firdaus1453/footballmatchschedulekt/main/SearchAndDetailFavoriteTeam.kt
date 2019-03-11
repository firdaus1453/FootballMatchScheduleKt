package me.firdaus1453.footballmatchschedulekt.main


import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import android.view.ViewGroup
import me.firdaus1453.footballmatchschedulekt.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class SearchAndDetailFavoriteTeam {

    val time: Long = 5000

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun searchAndDetailFavoriteTeam() {
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

        val actionMenuItemView = onView(
            allOf(
                withId(R.id.search), withContentDescription("Search"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.action_bar),
                        1
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        actionMenuItemView.perform(click())

        Thread.sleep(time)

        val searchAutoComplete = onView(
            allOf(
                withId(R.id.search_src_text),
                childAtPosition(
                    allOf(
                        withId(R.id.search_plate),
                        childAtPosition(
                            withId(R.id.search_edit_frame),
                            1
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        searchAutoComplete.perform(replaceText("barca"), closeSoftKeyboard())

        Thread.sleep(time)

        val searchAutoComplete2 = onView(
            allOf(
                withId(R.id.search_src_text), withText("barca"),
                childAtPosition(
                    allOf(
                        withId(R.id.search_plate),
                        childAtPosition(
                            withId(R.id.search_edit_frame),
                            1
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        searchAutoComplete2.perform(pressImeActionButton())

        Thread.sleep(time)

        val tesCardView = onView(
            allOf(
                childAtPosition(
                    allOf(
                        withId(R.id.rv_match),
                        childAtPosition(
                            withClassName(`is`("org.jetbrains.anko._RelativeLayout")),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        tesCardView.perform(click())

        Thread.sleep(time)

        val tabView = onView(
            allOf(
                withContentDescription("Player"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tab_team),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        tabView.perform(click())

        Thread.sleep(time)

        val viewPager = onView(
            allOf(
                withId(R.id.viewpager_team),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.support.v7.widget.ContentFrameLayout")),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        viewPager.perform(swipeLeft())

        Thread.sleep(time)

        val tesCardView2 = onView(
            allOf(
                childAtPosition(
                    allOf(
                        withId(R.id.rv_match),
                        childAtPosition(
                            withClassName(`is`("org.jetbrains.anko._RelativeLayout")),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        tesCardView2.perform(click())

        Thread.sleep(time)

        pressBack()

        Thread.sleep(time)

        val actionMenuItemView2 = onView(
            allOf(
                withId(R.id.menu_favorites), withContentDescription("Favorite"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.action_bar),
                        2
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        actionMenuItemView2.perform(click())

        Thread.sleep(time)

        val actionMenuItemView3 = onView(
            allOf(
                withId(R.id.menu_favorites), withContentDescription("Favorite"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.action_bar),
                        2
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        actionMenuItemView3.perform(click())

        Thread.sleep(time)

        pressBack()

        Thread.sleep(time)

        pressBack()
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
