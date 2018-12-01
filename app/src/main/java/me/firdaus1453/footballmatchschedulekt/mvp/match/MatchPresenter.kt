package me.firdaus1453.footballmatchschedulekt.mvp.match

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import me.firdaus1453.footballclubmodul6.network.ApiRepository
import me.firdaus1453.footballclubmodul6.network.TheSportDBApi
import me.firdaus1453.footballmatchschedulekt.helper.database
import me.firdaus1453.footballmatchschedulekt.model.favorite.FavoriteModel
import me.firdaus1453.footballmatchschedulekt.model.nextmatchmodel.NextMatchResponse
import me.firdaus1453.footballmatchschedulekt.mvp.match.MatchFragment.Companion.swipeRefresh
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MatchPresenter(
    private val view: MatchView,
    private val apiRepository: ApiRepository,
    private val gson: Gson
) {
    fun getNextMatchList(id: String?){
        view.showLoading()
        doAsync {
            val data = gson.fromJson(
                apiRepository.doRequest(TheSportDBApi.getNextMatch(id)), NextMatchResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showNextMatchList(data.events)
            }
        }
    }

    fun getPastMatchList(id: String?){
        view.showLoading()
        doAsync {
            val data = gson.fromJson(
                apiRepository.doRequest(TheSportDBApi.getPastMatch(id)), NextMatchResponse::class.java
            )

            uiThread {
                Log.i("debug1", data.toString())
                view.hideLoading()
                view.showPastMatchList(data.events)
            }
        }
    }

    fun showFavorite(context: Context){
        view.showLoading()

        context?.database?.use {
            swipeRefresh.isRefreshing = false
            val data = select(FavoriteModel.TABLE_FAVORITE).parseList(classParser<FavoriteModel>())
            Log.i("debug1", data.toString())
            view.showFavoriteList(data)
        }
        view.hideLoading()
    }

}