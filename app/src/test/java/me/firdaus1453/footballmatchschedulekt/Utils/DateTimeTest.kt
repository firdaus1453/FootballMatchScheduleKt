package me.firdaus1453.footballmatchschedulekt.Utils

import me.firdaus1453.footballmatchschedulekt.DateTime
import org.junit.Test

import org.junit.Assert.*

class DateTimeTest {

    @Test
    fun getLongDate() {
        assertEquals("Sat, 01 Dec 2018", DateTime.getLongDate("2018-12-01"))
    }
}