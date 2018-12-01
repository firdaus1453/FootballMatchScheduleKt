package me.firdaus1453.footballmatchschedulekt.model.detailmodel

import com.google.gson.annotations.SerializedName

data class DetailMatchResponse(

	@field:SerializedName("events")
	val events: List<EventsItem>
)