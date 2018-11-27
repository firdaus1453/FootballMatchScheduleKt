package me.firdaus1453.footballmatchschedulekt.mvp.match

import me.firdaus1453.footballmatchschedulekt.model.nextmatchmodel.EventsItem

interface MatchView {
    fun showLoading()
    fun hideLoading()
    fun showNextMatchList(data: List<EventsItem>)
    fun showPastMatchList(data: List<EventsItem>)
}