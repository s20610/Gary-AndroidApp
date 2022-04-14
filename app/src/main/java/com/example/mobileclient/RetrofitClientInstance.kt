package com.example.mobileclient

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClientInstance {
    private var retrofit: Retrofit? = null
    private var BASE_URL: String? = "http://localhost:8081"

    fun getRetrofitInstance(): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder().baseUrl(BASE_URL!!)
                .addConverterFactory(GsonConverterFactory.create()).build()
        }
        return retrofit
    }
}