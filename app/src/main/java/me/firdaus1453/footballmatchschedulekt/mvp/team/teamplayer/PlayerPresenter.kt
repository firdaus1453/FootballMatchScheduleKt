package me.firdaus1453.footballmatchschedulekt.mvp.team.teamplayer

import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.firdaus1453.footballmatchschedulekt.Utils.CoroutineContextProvider
import me.firdaus1453.footballmatchschedulekt.model.playermodel.PlayerResponse
import me.firdaus1453.footballmatchschedulekt.network.ApiRepository
import me.firdaus1453.footballmatchschedulekt.network.TheSportDBApi

class PlayerPresenter(
    private val view: PlayerView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    fun getAllPlayer(id: String?) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository.doRequest(TheSportDBApi.getAllPlayer(id)).await(), PlayerResponse::class.java
            )

            if (!data.player.isNullOrEmpty()) {
                view.showAllPlayer(data.player)
            }
            view.hideLoading()
        }
    }

}