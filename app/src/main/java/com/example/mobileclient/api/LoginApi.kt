package com.example.mobileclient.api

import com.example.mobileclient.model.Credentials
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    @POST("login")
    suspend fun getLoginResponse(
        @Body credentials: Credentials
    ): Response<String>
}