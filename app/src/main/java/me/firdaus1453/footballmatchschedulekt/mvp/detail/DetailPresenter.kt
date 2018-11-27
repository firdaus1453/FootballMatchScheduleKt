package me.firdaus1453.footballmatchschedulekt.mvp.detail

import com.google.gson.Gson
import me.firdaus1453.footballclubmodul6.network.ApiRepository
import me.firdaus1453.footballclubmodul6.network.TheSportDBApi
import me.firdaus1453.footballmatchschedulekt.model.nextmatchmodel.NextMatchResponse
import me.firdaus1453.footballmatchschedulekt.model.teammodel.TeamResponse
import me.firdaus1453.footballmatchschedulekt.mvp.match.MatchView
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class DetailPresenter(
    private val view: DetailView,
    private val apiRepository: ApiRepository,
    private val gson: Gson
) {
    fun getDetailMatch(idEvent: String?){
        view.showLoading()
        doAsync {
            val data = gson.fromJson(
                apiRepository.doRequest(TheSportDBApi.getDetailMatch(idEvent)), NextMatchResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showMatchDetail(data.events)
            }
        }
    }

    fun getTeamDetail(idHomeTeam: String?, idAwayTeam: String?) {
        view.showLoading()

        doAsync {
            val dataHomeTeam =
                gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.getDetailTeam(idHomeTeam.toString())),
                    TeamResponse::class.java
                )


            val dataAwayTeam =
                gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.getDetailTeam(idAwayTeam.toString())),
                    TeamResponse::class.java
                )


            view.hideLoading()
            view.showDetailTeam(dataHomeTeam.teams, dataAwayTeam.teams)
        }
    }

}