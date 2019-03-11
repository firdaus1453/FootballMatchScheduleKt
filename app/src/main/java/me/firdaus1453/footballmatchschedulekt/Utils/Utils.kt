package me.firdaus1453.footballmatchschedulekt.Utils

import android.view.View
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun String.dateTimeToFormat(format: String = "yyyy-MM-dd HH:mm:ss"): Long {

    val formatter = SimpleDateFormat(format, Locale.ENGLISH)
    val date = formatter.parse(this)

    return date.time
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

    fun getLongDate(date: String?): String {
        return formatDate(date.toString(), "EEE, dd MMM yyyy")
    }

    fun timeFormat(time: String?): String {
        if (time.isNullOrBlank()) {
            return time.orEmpty()
        } else {
            val hourWib = time!!.substring(0, 2).toInt() + 7
            val hourr: Int
            val hourrr: String
            if (hourWib > 23) {
                hourr = hourWib - 24
                hourrr = "0" + hourr.toString()
            } else {
                hourr = hourWib
                hourrr = hourr.toString()
            }
            val minuteWib = time.substring(3, 5)
            return "$hourrr:$minuteWib WIB"
        }
    }


}