package me.firdaus1453.footballmatchschedulekt.network

import me.firdaus1453.footballmatchschedulekt.BuildConfig

object TheSportDBApi {

    fun getLeagueAll(): String {
        return "${BuildConfig.BASE_URL}/api/v1/json/${BuildConfig.TSDB_API_KEY}/search_all_leagues.php?s=Soccer"

    }

    fun getNextMatch(id: String?): String {
        return "${BuildConfig.BASE_URL}/api/v1/json/${BuildConfig.TSDB_API_KEY}/eventsnextleague.php?id=$id"

    }

    fun getPastMatch(id: String?): String {
        return "${BuildConfig.BASE_URL}/api/v1/json/${BuildConfig.TSDB_API_KEY}/eventspastleague.php?id=$id"
    }

    fun getDetailMatch(idEvent: String?): String {
        return "${BuildConfig.BASE_URL}/api/v1/json/${BuildConfig.TSDB_API_KEY}/lookupevent.php?id=$idEvent"

    }

    fun getDetailTeam(idTeam: String?): String {
        return "${BuildConfig.BASE_URL}/api/v1/json/${BuildConfig.TSDB_API_KEY}/lookupteam.php?id=$idTeam"

    }

    fun getSearchMatch(keyword: String?): String {
        return "${BuildConfig.BASE_URL}/api/v1/json/${BuildConfig.TSDB_API_KEY}/searchevents.php?e=$keyword"
    }

    fun getTeamAll(id: String?): String {
        return "${BuildConfig.BASE_URL}/api/v1/json/${BuildConfig.TSDB_API_KEY}/lookup_all_teams.php?id=$id"

    }


    fun getSearchTeam(keyword: String?): String {
        return "${BuildConfig.BASE_URL}/api/v1/json/${BuildConfig.TSDB_API_KEY}/searchteams.php?t=$keyword"
    }

    fun getAllPlayer(id: String?): String {
        return "${BuildConfig.BASE_URL}/api/v1/json/${BuildConfig.TSDB_API_KEY}/lookup_all_players.php?id=$id"


    }


    fun getDetailPlayer(id: String?): String {
        return "${BuildConfig.BASE_URL}/api/v1/json/${BuildConfig.TSDB_API_KEY}/lookupplayer.php?id=$id"

    }
}