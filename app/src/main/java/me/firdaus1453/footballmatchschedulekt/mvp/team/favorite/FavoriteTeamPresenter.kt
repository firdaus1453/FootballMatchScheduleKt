package me.firdaus1453.footballmatchschedulekt.mvp.team.favorite

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import me.firdaus1453.footballmatchschedulekt.Utils.CoroutineContextProvider
import me.firdaus1453.footballmatchschedulekt.helper.database
import me.firdaus1453.footballmatchschedulekt.model.favorite.FavoriteTeam
import me.firdaus1453.footballmatchschedulekt.mvp.match.favorite.FavoriteMatchFragment.Companion.swipeRefresh
import me.firdaus1453.footballmatchschedulekt.network.ApiRepository
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class FavoriteTeamPresenter(
    private val view: FavoriteTeamView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    fun showFavorite(context: Context) {
        view.showLoading()

        context.database.use {
            swipeRefresh.isRefreshing = false
            val data = select(FavoriteTeam.TABLE_FAVORITE_TEAM).parseList(classParser<FavoriteTeam>())
            Log.i("debug1", data.toString())
            view.showFavoriteList(data)
        }
        view.hideLoading()
    }
}