package com.example.mobileclient.api

import com.example.mobileclient.model.Credentials
import com.example.mobileclient.model.NewUser
import com.example.mobileclient.model.Tutorial
import com.example.mobileclient.model.User
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface BackendAPI {
    @POST("login")
    suspend fun getLoginResponse(
        @Body credentials: Credentials
    ): Response<String>

    @POST("login")
    suspend fun getLogin2Response(@Body credentials: Credentials):  Call<String>

    @POST("register/normal")
    suspend fun registerNewUser(
        @Body newUser: NewUser
    ): Response<ResponseBody>

    @GET("user/{id}")
    suspend fun getUserInfo(
        @Path("id") userId: Int
    ): Response<User>

    @GET("user/{id}")
    suspend fun getUser2Info(@Path("id") userId: Int): Call<User>

    @GET("tutorial")
    suspend fun getTutorials(): Response<List<Tutorial>>
}