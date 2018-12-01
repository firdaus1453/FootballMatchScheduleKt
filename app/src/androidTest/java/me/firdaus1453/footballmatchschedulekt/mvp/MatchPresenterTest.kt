package me.firdaus1453.footballmatchschedulekt.mvp

import com.google.gson.Gson
import me.firdaus1453.footballclubmodul6.network.ApiRepository
import me.firdaus1453.footballclubmodul6.network.TheSportDBApi
import me.firdaus1453.footballmatchschedulekt.model.nextmatchmodel.EventsItem
import me.firdaus1453.footballmatchschedulekt.model.nextmatchmodel.NextMatchResponse
import me.firdaus1453.footballmatchschedulekt.mvp.match.MatchPresenter
import me.firdaus1453.footballmatchschedulekt.mvp.match.MatchView
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
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
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MatchPresenter(view, apiRepository, gson)
    }

    @Test
    fun getMatchPast() {
        val data: MutableList<EventsItem> = mutableListOf()
        val response = NextMatchResponse(data)
        val id = "4328"

        Mockito.`when`(
            gson.fromJson(
                apiRepository.doRequest(TheSportDBApi.getPastMatch(id)),
                NextMatchResponse::class.java
            )
        ).thenReturn(response)

        presenter.getPastMatchList(id)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showPastMatchList(data)
        Mockito.verify(view).hideLoading()
    }
}