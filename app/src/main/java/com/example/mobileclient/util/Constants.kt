package com.example.mobileclient.util

import com.google.gson.Gson
import com.google.gson.GsonBuilder

class Constants {
    companion object{
        const val BASE_URL = "http://10.0.2.2:8080"
        val gson: Gson = GsonBuilder().setLenient().create()
    }
}