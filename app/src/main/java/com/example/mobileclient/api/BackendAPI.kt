package com.example.mobileclient.api

import com.example.mobileclient.model.*

import com.example.mobileclient.model.Credentials
import com.example.mobileclient.model.MedicalInfo
import com.example.mobileclient.model.NewUser
import com.example.mobileclient.model.Tutorial
import com.example.mobileclient.model.User
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface BackendAPI {
    @POST("login")
    suspend fun getLoginResponse(
        @Body credentials: Credentials
    ): Response<String>

    @POST("login")
    suspend fun getLogin2Response(@Body credentials: Credentials): Call<String>

    @POST("register/normal")
    suspend fun registerNewUser(
        @Body newUser: NewUser
    ): Response<ResponseBody>

    @GET("medicalInfo/{id}")
    suspend fun getUserMedicalInfo(
        @Path("id") userId: Int
    ) : Response<MedicalInfo>

    @POST("medicalInfo/{id}")
    suspend fun postUserMedicalInfo(
        @Path("id") userId: Int,
        @Body newMedicalInfo: MedicalInfo
    ) : Response<ResponseBody>

    @PUT("medicalInfo/blood/{id}")
    suspend fun putUserMedicalInfoBlood(
        @Path("id") userId: Int,
        @Body blood: BloodType
    ) : Response <ResponseBody>

    @GET("user/{id}")
    suspend fun getUserInfo(
        @Path("id") userId: Int
    ): Response<User>

    @GET("user/{id}")
    suspend fun getUser2Info(@Path("id") userId: Int): Call<User>

    @GET("tutorial")
    suspend fun getTutorials(): Response<List<Tutorial>>
}