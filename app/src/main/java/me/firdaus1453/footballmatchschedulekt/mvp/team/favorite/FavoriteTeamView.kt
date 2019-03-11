package me.firdaus1453.footballmatchschedulekt.mvp.team.favorite

import me.firdaus1453.footballmatchschedulekt.model.favorite.FavoriteTeam

interface FavoriteTeamView {
    fun showLoading()
    fun hideLoading()
    fun showFavoriteList(data: List<FavoriteTeam>)
}