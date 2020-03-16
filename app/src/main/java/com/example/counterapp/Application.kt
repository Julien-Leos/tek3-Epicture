package com.example.epicture

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class EpictureApplication : Application() {

    companion object {
        val clientId : String = "1021db3f5151cf5"
        val clientSecret = "ddd88290a689b5cbfdc1c0eb6e3ceec95530dd2f"
        lateinit var settings : MutableMap<String, String>

        var searchQuery : String = ""

        @SuppressLint("StaticFieldLeak")
        lateinit var context : Context
    }

    override fun onCreate() {
        super.onCreate()
        settings = mutableMapOf(
            "client_id" to clientId,
            "client_secret" to clientSecret,
            "url" to "https://api.imgur.com/oauth2/authorize?client_id=$clientId&response_type=token"
        )
    }

}