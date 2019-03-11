package me.firdaus1453.footballmatchschedulekt.mvp.team

import me.firdaus1453.footballmatchschedulekt.model.leaguemodel.LeagueResponse
import me.firdaus1453.footballmatchschedulekt.model.teammodel.TeamsItem

interface TeamsView {
    fun showLoading()
    fun hideLoading()
    fun showAllTeam(data: List<TeamsItem>)
    fun showLeagueList(data: LeagueResponse?)
    fun showSearchTeam(data: List<TeamsItem>)
}