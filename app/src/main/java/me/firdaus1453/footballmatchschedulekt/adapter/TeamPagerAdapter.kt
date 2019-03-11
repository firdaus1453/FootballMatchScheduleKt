package me.firdaus1453.footballmatchschedulekt.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import me.firdaus1453.footballmatchschedulekt.mvp.team.teamoverview.TeamOverviewFragment
import me.firdaus1453.footballmatchschedulekt.mvp.team.teamplayer.PlayerFragment

class TeamPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm){
    override fun getItem(p0: Int): Fragment {
        return when (p0){
            0 -> TeamOverviewFragment()
            else -> PlayerFragment()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Info"
            else -> "Player"
        }
    }
}