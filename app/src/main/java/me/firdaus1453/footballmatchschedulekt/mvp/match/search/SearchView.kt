package me.firdaus1453.footballmatchschedulekt.mvp.match.search

import me.firdaus1453.footballmatchschedulekt.model.searchmodel.EventItem

interface SearchMatchView {
    fun showLoading()
    fun hideLoading()
    fun showSearchMatch(data: List<EventItem>)
}