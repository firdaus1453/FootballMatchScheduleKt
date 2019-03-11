package me.firdaus1453.footballmatchschedulekt.mvp.match.search

import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.firdaus1453.footballmatchschedulekt.Utils.CoroutineContextProvider
import me.firdaus1453.footballmatchschedulekt.model.searchmodel.SearchResponse
import me.firdaus1453.footballmatchschedulekt.network.ApiRepository
import me.firdaus1453.footballmatchschedulekt.network.TheSportDBApi

class SearchPresenter(
    private val view: SearchMatchView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {


    fun getSearchMatch(keyword: String?) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository.doRequest(TheSportDBApi.getSearchMatch(keyword?.trim())).await(), SearchResponse::class.java
            )

            view.hideLoading()
            if (data.event != null && !data.event.isNullOrEmpty()) {
                Log.i("cek", "masuk if true data.event")
                view.showSearchMatch(data.event)
            }else{
                Log.i("cek", "masuk else data.event")
            }
        }
    }

}