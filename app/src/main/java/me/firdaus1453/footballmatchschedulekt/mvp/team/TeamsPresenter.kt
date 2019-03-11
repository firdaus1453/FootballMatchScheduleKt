package me.firdaus1453.footballmatchschedulekt.mvp.team

import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.firdaus1453.footballmatchschedulekt.Utils.CoroutineContextProvider
import me.firdaus1453.footballmatchschedulekt.model.leaguemodel.LeagueResponse
import me.firdaus1453.footballmatchschedulekt.model.teammodel.TeamResponse
import me.firdaus1453.footballmatchschedulekt.network.ApiRepository
import me.firdaus1453.footballmatchschedulekt.network.TheSportDBApi

class TeamsPresenter(
    private val view: TeamsView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {


    fun getAllTeam(id: String?) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository.doRequest(TheSportDBApi.getTeamAll(id)).await(), TeamResponse::class.java
            )

            view.hideLoading()
            if (!data.teams.isNullOrEmpty()) {
                view.showAllTeam(data.teams)
            }
        }
    }

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

    fun getSearchTeam(id: String?) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository.doRequest(TheSportDBApi.getSearchTeam(id)).await(), TeamResponse::class.java
            )

            view.hideLoading()
            if (!data.teams.isNullOrEmpty()) {
                view.showSearchTeam(data.teams)
            }
        }
    }

}