package me.firdaus1453.footballmatchschedulekt.main

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import me.firdaus1453.footballmatchschedulekt.Fragment.FavoriteMatchFragment
import me.firdaus1453.footballmatchschedulekt.R
import me.firdaus1453.footballmatchschedulekt.mvp.match.MatchFragment

class MainActivity : AppCompatActivity() {
    lateinit var toolbar: ActionBar

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_past -> {
               movePastFragment()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_next -> {
                moveNextFragment()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_favorite -> {
                moveFavoriteFragment()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = supportActionBar!!
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        movePastFragment()

    }

    private fun movePastFragment() {
        toolbar.title = "Past Match"
        val bundle = Bundle()
        bundle.putInt(MatchFragment.KEY_MENU, 2)
        val fragment = MatchFragment()
        fragment.arguments = bundle
        addFragment(fragment)
    }

    private fun moveNextFragment() {
        toolbar.title = "Next Match"
        val bundle = Bundle()
        bundle.putInt(MatchFragment.KEY_MENU, 1)
        val fragment = MatchFragment()
        fragment.arguments = bundle
        addFragment(fragment)
    }

    private fun moveFavoriteFragment() {
        toolbar.title = "Favorite Match"
        val fragment = FavoriteMatchFragment()
        addFragment(fragment)
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.design_bottom_sheet_slide_in,
                R.anim.design_bottom_sheet_slide_out
            )
            .replace(R.id.container, fragment, fragment.javaClass.getSimpleName())
            .commit()
    }
}
