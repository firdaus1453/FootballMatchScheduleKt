package me.firdaus1453.footballmatchschedulekt.mvp.team.teamplayer

import me.firdaus1453.footballmatchschedulekt.model.playermodel.PlayerItem

interface PlayerView {
    fun showLoading()
    fun hideLoading()
    fun showAllPlayer(data: List<PlayerItem>) {
    }
}