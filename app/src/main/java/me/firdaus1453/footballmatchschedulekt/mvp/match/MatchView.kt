package me.firdaus1453.footballmatchschedulekt.mvp.match

import me.firdaus1453.footballmatchschedulekt.model.favorite.FavoriteModel
import me.firdaus1453.footballmatchschedulekt.model.leaguemodel.LeagueResponse
import me.firdaus1453.footballmatchschedulekt.model.nextmatchmodel.EventsItem

interface MatchView {
    fun showLoading()
    fun hideLoading()
    fun showLeagueList(data: LeagueResponse)
    fun showNextMatchList(data: List<EventsItem>)
    fun showPastMatchList(data: List<EventsItem>)
    fun showFavoriteList(data: List<FavoriteModel>)
}