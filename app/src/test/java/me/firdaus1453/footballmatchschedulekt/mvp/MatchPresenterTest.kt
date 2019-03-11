package me.firdaus1453.footballmatchschedulekt.mvp

import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.firdaus1453.footballmatchschedulekt.Utils.TestContextProvider
import me.firdaus1453.footballmatchschedulekt.model.nextmatchmodel.EventsItem
import me.firdaus1453.footballmatchschedulekt.model.nextmatchmodel.NextMatchResponse
import me.firdaus1453.footballmatchschedulekt.mvp.match.MatchPresenter
import me.firdaus1453.footballmatchschedulekt.mvp.match.MatchView
import me.firdaus1453.footballmatchschedulekt.network.ApiRepository
import me.firdaus1453.footballmatchschedulekt.network.TheSportDBApi
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class MatchPresenterTest {
    @Mock
    lateinit var view: MatchView

    @Mock
    lateinit var apiRepository: ApiRepository

    @Mock
    lateinit var gson: Gson

    lateinit var presenter: MatchPresenter

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        presenter = MatchPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getMatchPastTest() {
        val data: MutableList<EventsItem> = mutableListOf()
        val response = NextMatchResponse(data)
        val id = "4328"

        GlobalScope.launch {
            `when`(
                gson.fromJson(
                    apiRepository.doRequest(TheSportDBApi.getPastMatch(id)).await(),
                    NextMatchResponse::class.java
                )
            ).thenReturn(response)

            presenter.getPastMatchList(id)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showPastMatchList(data)
            Mockito.verify(view).hideLoading()
        }
    }

    @Test
    fun getMatchNextTest() {
        val data: MutableList<EventsItem> = mutableListOf()
        val response = NextMatchResponse(data)
        val id = "1234"

        GlobalScope.launch {
            `when`(
                gson.fromJson(
                    apiRepository.doRequest(TheSportDBApi.getNextMatch(id)).await(),
                    NextMatchResponse::class.java
                )
            ).thenReturn(response)

            presenter.getNextMatchList(id)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showNextMatchList(data)
            Mockito.verify(view).hideLoading()
        }
    }
}