package me.firdaus1453.footballmatchschedulekt.mvp.team.detailplayer

import me.firdaus1453.footballmatchschedulekt.model.detailplayermodel.PlayersItem

interface DetailPlayerView {
    fun showLoading()
    fun hideLoading()
    fun showDetailPlayer(data: List<PlayersItem>)
}