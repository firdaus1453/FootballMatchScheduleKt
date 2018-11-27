package me.firdaus1453.footballclubmodul6.network

import java.net.URL

class ApiRepository {

    fun doRequest(url: String): String {
        return URL(url).readText()
    }
}
