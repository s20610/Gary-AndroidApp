package com.example.mobileclient.api

import com.example.mobileclient.model.Credentials
import retrofit2.Response

class Repository {
    suspend fun getLoginResponse(credentials: Credentials): Response<String> {
        return RetrofitInstance.api.getLoginResponse(credentials)
    }
}