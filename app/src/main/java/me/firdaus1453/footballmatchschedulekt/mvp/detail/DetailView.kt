package me.firdaus1453.footballmatchschedulekt.mvp.detail

import me.firdaus1453.footballmatchschedulekt.model.nextmatchmodel.EventsItem
import me.firdaus1453.footballmatchschedulekt.model.teammodel.TeamsItem

interface DetailView {
    fun showLoading()
    fun hideLoading()
    fun showMatchDetail(data: List<EventsItem>)
    fun showDetailTeam(dataHomeTeam: List<TeamsItem>, dataAwayTeam: List<TeamsItem>)
}