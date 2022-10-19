package com.example.mobileclient.util

import com.google.gson.Gson
import com.google.gson.GsonBuilder

class Constants {
    companion object{
        const val LOCAL = "http://10.0.2.2:8080"
        const val PJATK = "http://172.21.40.111:8080"
        val gson: Gson = GsonBuilder().setLenient().create()
    }
}