package com.example.mobileclient.api

import com.example.mobileclient.util.Constants.Companion.LOCAL
import com.example.mobileclient.util.Constants.Companion.PJATK
import com.example.mobileclient.util.Constants.Companion.gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder().baseUrl(PJATK).addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val api: BackendAPI by lazy {
        retrofit.create(BackendAPI::class.java)
    }
}