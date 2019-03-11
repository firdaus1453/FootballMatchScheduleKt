package me.firdaus1453.footballmatchschedulekt.mvp

import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.firdaus1453.footballmatchschedulekt.model.nextmatchmodel.EventsItem
import me.firdaus1453.footballmatchschedulekt.model.nextmatchmodel.NextMatchResponse
import me.firdaus1453.footballmatchschedulekt.model.teammodel.TeamResponse
import me.firdaus1453.footballmatchschedulekt.model.teammodel.TeamsItem
import me.firdaus1453.footballmatchschedulekt.mvp.detail.DetailPresenter
import me.firdaus1453.footballmatchschedulekt.mvp.detail.DetailView
import me.firdaus1453.footballmatchschedulekt.network.ApiRepository
import me.firdaus1453.footballmatchschedulekt.network.TheSportDBApi
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations


class DetailPresenterTest {
    @Mock
    lateinit var view: DetailView

    @Mock
    lateinit var apiRepository: ApiRepository

    @Mock
    lateinit var gson: Gson

    private lateinit var presenter: DetailPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = DetailPresenter(view, apiRepository, gson)
    }

    @Test
    fun getDetailMatch() {
        val data: MutableList<EventsItem> = mutableListOf()
        val response = NextMatchResponse(data)
        val id = "1235"

        GlobalScope.launch {
            `when`(
                gson.fromJson(
                    apiRepository.doRequest(TheSportDBApi.getDetailMatch(id)).await(), NextMatchResponse::class.java
                )
            ).thenReturn(response)


            presenter.getDetailMatch(id)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showMatchDetail(data)
            Mockito.verify(view).hideLoading()
        }
    }

    @Test
    fun getTeamDetail() {
        val data: MutableList<TeamsItem> = mutableListOf()
        val response = TeamResponse(data)
        val id = "1235"

        GlobalScope.launch {
            `when`(
                gson.fromJson(
                    apiRepository.doRequest(TheSportDBApi.getDetailTeam(id)).await(), TeamResponse::class.java
                )
            ).thenReturn(response)


            presenter.getTeamDetail(id, id)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showDetailTeam(data, data)
            Mockito.verify(view).hideLoading()
        }
    }


}