package com.example.mobileclient.api

import com.example.mobileclient.model.*
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface BackendAPI {
    @POST("login")
    suspend fun getLoginResponse(
        @Body credentials: Credentials
    ): Response<String>

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
}