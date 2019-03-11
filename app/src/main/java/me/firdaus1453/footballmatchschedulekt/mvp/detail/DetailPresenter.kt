package me.firdaus1453.footballmatchschedulekt.mvp.detail

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.firdaus1453.footballmatchschedulekt.Utils.CoroutineContextProvider
import me.firdaus1453.footballmatchschedulekt.helper.database
import me.firdaus1453.footballmatchschedulekt.model.favorite.FavoriteModel
import me.firdaus1453.footballmatchschedulekt.model.nextmatchmodel.EventsItem
import me.firdaus1453.footballmatchschedulekt.model.nextmatchmodel.NextMatchResponse
import me.firdaus1453.footballmatchschedulekt.model.teammodel.TeamResponse
import me.firdaus1453.footballmatchschedulekt.network.ApiRepository
import me.firdaus1453.footballmatchschedulekt.network.TheSportDBApi
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.toast

class DetailPresenter(
    private val view: DetailView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    fun getDetailMatch(idEvent: String?) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository.doRequest(TheSportDBApi.getDetailMatch(idEvent)).await(), NextMatchResponse::class.java
            )
            if (!data.events.isNullOrEmpty()) {
                view.showMatchDetail(data.events)
            }
            view.hideLoading()
        }
    }

    fun getTeamDetail(idHomeTeam: String?, idAwayTeam: String?) {
        view.showLoading()

        GlobalScope.launch(context.main) {
            val dataHomeTeam =
                gson.fromJson(
                    apiRepository
                        .doRequest(TheSportDBApi.getDetailTeam(idHomeTeam)).await(),
                    TeamResponse::class.java
                )


            val dataAwayTeam =
                gson.fromJson(
                    apiRepository
                        .doRequest(TheSportDBApi.getDetailTeam(idAwayTeam)).await(),
                    TeamResponse::class.java
                )

            if (!dataAwayTeam.teams.isNullOrEmpty() && !dataHomeTeam.teams.isNullOrEmpty()) {
                view.showDetailTeam(dataHomeTeam.teams, dataAwayTeam.teams)
            }
            view.hideLoading()
        }
    }

    fun addToFavorite(context: Context, data: MutableList<EventsItem>) {
        try {
            context.database.use {
                insert(
                    FavoriteModel.TABLE_FAVORITE,
                    FavoriteModel.EVENT_ID to data[0].idEvent,
                    FavoriteModel.ID_HOME to data[0].idHomeTeam,
                    FavoriteModel.TEAM_HOME to data[0].strHomeTeam,
                    FavoriteModel.SCORE_HOME to data[0].intHomeScore,
                    FavoriteModel.ID_AWAY to data[0].idAwayTeam,
                    FavoriteModel.TEAM_AWAY to data[0].strAwayTeam,
                    FavoriteModel.SCORE_AWAY to data[0].intAwayScore,
                    FavoriteModel.EVENT_DATE to data[0].dateEvent,
                    FavoriteModel.EVENT_TIME to data[0].strTime
                )
            }
        } catch (e: SQLiteConstraintException) {
            context.toast("Error: ${e.message}")
        }
    }

    fun removeFavorit(context: Context, teams: MutableList<EventsItem>) {
        try {
            context.database.use {
                delete(
                    FavoriteModel.TABLE_FAVORITE, FavoriteModel.EVENT_ID + " = {id}",
                    "id" to teams[0].idEvent.toString()
                )
            }
        } catch (e: SQLiteConstraintException) {
            context.toast("Error: ${e.message}")
        }
    }

    fun isFavorite(context: Context, data: List<EventsItem>): Boolean {
        var bFavorite = false

        context.database.use {

            val result = select(FavoriteModel.TABLE_FAVORITE)
                .whereArgs(
                    FavoriteModel.EVENT_ID + " = {id}",
                    "id" to data[0].idEvent.toString()
                )
            val favorite = result.parseList(classParser<FavoriteModel>())
            bFavorite = !favorite.isEmpty()
            Log.i("debug favo", bFavorite.toString())
        }

        return bFavorite
    }

}