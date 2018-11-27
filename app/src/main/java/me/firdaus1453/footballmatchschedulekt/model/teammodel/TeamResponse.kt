package me.firdaus1453.footballmatchschedulekt.model.teammodel

import com.google.gson.annotations.SerializedName

data class TeamResponse(

	@field:SerializedName("teams")
	val teams: List<TeamsItem>
)