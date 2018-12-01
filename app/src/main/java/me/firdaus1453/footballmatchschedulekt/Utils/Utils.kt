package me.firdaus1453.footballclubmodul6

import android.view.View
import java.text.ParseException
import java.text.SimpleDateFormat

fun View.visible(){
    visibility = View.VISIBLE
}

fun View.invisible(){
    visibility = View.INVISIBLE
}

object DateTime {

    private fun formatDate(date: String, format: String): String {
        var result = ""
        val old = SimpleDateFormat("yyyy-MM-dd")

        try {
            val oldDate = old.parse(date)
            val newFormat = SimpleDateFormat(format)

            result = newFormat.format(oldDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return result
    }

    fun getShortDate(date: String?): String {
        return formatDate(date.toString(), "dd MMMM yyyy")
    }

    fun getLongDate(date: String?): String {
        return formatDate(date.toString(), "EEE, dd MMM yyyy")
    }
}