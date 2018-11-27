package me.firdaus1453.footballmatchschedulekt.model.nextmatchmodel

import com.google.gson.annotations.SerializedName

data class NextMatchResponse(

	@field:SerializedName("events")
	val events: List<EventsItem>
)