package me.firdaus1453.footballmatchschedulekt.mvp.team.detailteam

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.firdaus1453.footballmatchschedulekt.Utils.CoroutineContextProvider
import me.firdaus1453.footballmatchschedulekt.helper.database
import me.firdaus1453.footballmatchschedulekt.model.favorite.FavoriteTeam
import me.firdaus1453.footballmatchschedulekt.model.teammodel.TeamResponse
import me.firdaus1453.footballmatchschedulekt.model.teammodel.TeamsItem
import me.firdaus1453.footballmatchschedulekt.network.ApiRepository
import me.firdaus1453.footballmatchschedulekt.network.TheSportDBApi
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.toast

class DetailTeamPresenter(
    private val view: DetailTeamView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    fun getTeamDetail(idTeam: String?) {
        view.showLoading()

        GlobalScope.launch(context.main) {
            val data =
                gson.fromJson(
                    apiRepository
                        .doRequest(TheSportDBApi.getDetailTeam(idTeam)).await(),
                    TeamResponse::class.java
                )

            if (!data.teams.isNullOrEmpty()) {
                view.showDetailTeam(data.teams)
            }
            view.hideLoading()
        }
    }

    fun addToFavorite(context: Context, data: MutableList<TeamsItem>) {
        try {
            context.database.use {
                insert(
                    FavoriteTeam.TABLE_FAVORITE_TEAM,
                    FavoriteTeam.TEAM_ID to data[0].idTeam,
                    FavoriteTeam.TEAM_NAME to data[0].strTeam,
                    FavoriteTeam.TEAM_BADGE to data[0].strTeamBadge
                )
            }
        } catch (e: SQLiteConstraintException) {
            context.toast("Error: ${e.message}")
        }
    }

    fun removeFavorit(context: Context, teams: MutableList<TeamsItem>) {
        try {
            context.database.use {
                delete(
                    FavoriteTeam.TABLE_FAVORITE_TEAM, FavoriteTeam.TEAM_ID + " = {id}",
                    "id" to teams[0].idTeam.toString()
                )
            }
        } catch (e: SQLiteConstraintException) {
            context.toast("Error: ${e.message}")
        }
    }

    fun isFavorite(context: Context, data: List<TeamsItem>): Boolean {
        var bFavorite = false

        context.database.use {

            val result = select(FavoriteTeam.TABLE_FAVORITE_TEAM)
                .whereArgs(
                    FavoriteTeam.TEAM_ID + " = {id}",
                    "id" to data[0].idTeam.toString()
                )
            val favorite = result.parseList(classParser<FavoriteTeam>())
            bFavorite = !favorite.isEmpty()
            Log.i("debug favo", bFavorite.toString())
        }
        return bFavorite
    }

}