package me.firdaus1453.footballmatchschedulekt.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import me.firdaus1453.footballmatchschedulekt.mvp.match.favorite.FavoriteMatchFragment
import me.firdaus1453.footballmatchschedulekt.mvp.team.favorite.FavoriteTeamFragment

class FavoritePagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm){
    override fun getItem(p0: Int): Fragment {
        return when (p0){
            0 -> FavoriteMatchFragment()
            else -> FavoriteTeamFragment()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Match"
            else -> "Team"
        }
    }
}