package me.firdaus1453.footballmatchschedulekt.mvp.team.detailplayer

import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.firdaus1453.footballmatchschedulekt.Utils.CoroutineContextProvider
import me.firdaus1453.footballmatchschedulekt.model.detailplayermodel.DetailPlayerResponse
import me.firdaus1453.footballmatchschedulekt.network.ApiRepository
import me.firdaus1453.footballmatchschedulekt.network.TheSportDBApi

class DetailPlayerPresenter(
    private val view: DetailPlayerView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {


    fun getDetailPlayer(id: String?) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository.doRequest(TheSportDBApi.getDetailPlayer(id)).await(), DetailPlayerResponse::class.java
            )

            view.hideLoading()
            if (!data.players.isNullOrEmpty()) {
                view.showDetailPlayer(data.players)
            }
        }
    }


}