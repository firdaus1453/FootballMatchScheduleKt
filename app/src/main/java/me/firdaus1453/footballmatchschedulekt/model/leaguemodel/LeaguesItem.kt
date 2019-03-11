package me.firdaus1453.footballmatchschedulekt.model.leaguemodel

data class LeaguesItem(val idLeague: String?, val strLeague: String?) {

    override fun toString(): String {
        return strLeague.toString()
    }
}
