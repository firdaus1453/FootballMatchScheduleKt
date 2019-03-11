package me.firdaus1453.footballmatchschedulekt.mvp.match

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.firdaus1453.footballmatchschedulekt.Utils.CoroutineContextProvider
import me.firdaus1453.footballmatchschedulekt.helper.database
import me.firdaus1453.footballmatchschedulekt.model.favorite.FavoriteModel
import me.firdaus1453.footballmatchschedulekt.model.leaguemodel.LeagueResponse
import me.firdaus1453.footballmatchschedulekt.model.nextmatchmodel.NextMatchResponse
import me.firdaus1453.footballmatchschedulekt.mvp.match.favorite.FavoriteMatchFragment.Companion.swipeRefresh
import me.firdaus1453.footballmatchschedulekt.network.ApiRepository
import me.firdaus1453.footballmatchschedulekt.network.TheSportDBApi
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class MatchPresenter(
    private val view: MatchView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    fun getLeague() {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository.doRequest(TheSportDBApi.getLeagueAll()).await(), LeagueResponse::class.java
            )

            view.hideLoading()
            view.showLeagueList(data)
        }
    }

    fun getNextMatchList(id: String?) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository.doRequest(TheSportDBApi.getNextMatch(id)).await(), NextMatchResponse::class.java
            )

            view.hideLoading()
            if (!data.events.isNullOrEmpty()) {
                view.showNextMatchList(data.events)
            }
        }
    }

    fun getPastMatchList(id: String?) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository.doRequest(TheSportDBApi.getPastMatch(id)).await(), NextMatchResponse::class.java
            )

            view.hideLoading()
            if (!data.events.isNullOrEmpty()) {
                view.showPastMatchList(data.events)
            }
        }
    }

    fun showFavorite(context: Context) {
        view.showLoading()

        context.database.use {
            swipeRefresh.isRefreshing = false
            val data = select(FavoriteModel.TABLE_FAVORITE).parseList(classParser<FavoriteModel>())
            Log.i("debug1", data.toString())
            view.showFavoriteList(data)
        }
        view.hideLoading()
    }
}