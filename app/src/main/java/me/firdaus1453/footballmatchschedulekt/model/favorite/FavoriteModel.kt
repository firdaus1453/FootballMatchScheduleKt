package me.firdaus1453.footballmatchschedulekt.model.favorite

data class FavoriteModel(
    val id: Long?, val eventId: String?,
    val idHome: String?,val teamHome: String?, val scoreHome: String?,
    val idAway: String?,val teamAway: String?, val scoreAway: String?,    val eventDate: String?

    ) {
    companion object {
        const val TABLE_FAVORITE: String = "TABLE_FAVORITE"
        const val ID: String = "ID"
        const val EVENT_ID: String = "EVENT_ID"
        const val ID_HOME: String = "ID_HOME"
        const val TEAM_HOME: String = "TEAM_HOME"
        const val SCORE_HOME: String = "SCORE_HOME"
        const val ID_AWAY: String = "ID_AWAY"
        const val TEAM_AWAY: String = "TEAM_AWAY"
        const val SCORE_AWAY: String = "SCORE_AWAY"
        const val EVENT_DATE: String = "EVENT_DATE"
    }
}