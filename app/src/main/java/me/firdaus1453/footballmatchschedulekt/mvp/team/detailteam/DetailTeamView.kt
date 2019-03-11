package me.firdaus1453.footballmatchschedulekt.mvp.team.detailteam

import me.firdaus1453.footballmatchschedulekt.model.teammodel.TeamsItem

interface DetailTeamView {
    fun showLoading()
    fun hideLoading()
    fun showDetailTeam(dataTeam: List<TeamsItem>)
}