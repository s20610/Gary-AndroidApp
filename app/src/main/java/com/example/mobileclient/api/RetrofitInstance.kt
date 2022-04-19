package com.example.mobileclient.api

import com.example.mobileclient.util.Constants.Companion.BASE_URL
import com.example.mobileclient.util.Constants.Companion.gson
import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val api: LoginApi by lazy {
        retrofit.create(LoginApi::class.java)
    }
}