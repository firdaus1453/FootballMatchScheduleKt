package me.firdaus1453.footballmatchschedulekt.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import me.firdaus1453.footballmatchschedulekt.mvp.match.PastMatchFragment
import me.firdaus1453.footballmatchschedulekt.mvp.match.next.NextMatchFragment

class MatchPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm){
    override fun getItem(p0: Int): Fragment {
        return when (p0){
            0 -> PastMatchFragment()
            else -> NextMatchFragment()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Last"
            else -> "Next"
        }
    }
}