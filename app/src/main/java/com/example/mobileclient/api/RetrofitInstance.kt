package com.example.mobileclient.api

import com.example.mobileclient.util.Constants.Companion.BASE_URL
import com.example.mobileclient.util.Constants.Companion.gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val api: BackendAPI by lazy {
        retrofit.create(BackendAPI::class.java)
    }
}