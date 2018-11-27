package me.firdaus1453.footballmatchschedulekt.mvp.match

import com.google.gson.Gson
import me.firdaus1453.footballclubmodul6.network.ApiRepository
import me.firdaus1453.footballclubmodul6.network.TheSportDBApi
import me.firdaus1453.footballmatchschedulekt.model.nextmatchmodel.NextMatchResponse
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
                view.hideLoading()
                view.showPastMatchList(data.events)
            }
        }
    }

}