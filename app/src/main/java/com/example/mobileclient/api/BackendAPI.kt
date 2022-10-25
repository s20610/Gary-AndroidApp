package com.example.mobileclient.api

import com.example.mobileclient.model.*
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface BackendAPI {
    //Login api calls
    @POST("auth/login")
    suspend fun getLoginResponse(
        @Body credentials: Credentials
    ): Response<AuthResponse>

    @POST("auth/signup")
    suspend fun registerNewUser(
        @Body newUser: NewUser
    ): Response<ResponseBody>

    //Tutorial api calls
    @GET("tutorial")
    suspend fun getTutorials(): Response<List<Tutorial>>

    @GET("tutorial/{id}")
    suspend fun getTutorial(@Path("id") tutorialId: Int): Response<Tutorial>

    //Medical info api calls
    @GET("medical_info/user/{email}")
    suspend fun getUserMedicalInfo(@Path("email") userEmail: String
    ): Response<MedicalInfo>

    @GET("medical_info/blood/{id}")
    suspend fun getUserBlood(
        @Path("id") id: Int,
    ): Response<Blood>

    @PUT("medical_info/blood/{id}")
    suspend fun putUserBlood(
        @Path("id") id: Int,
        @Body blood: Blood
    ): Response<ResponseBody>

    @POST("medical_info/blood")
    suspend fun postUserBlood(
        @Body blood: Blood
    ): Response<ResponseBody>

    @GET("disease/{id}")
    suspend fun getDisease(
        @Path("id") id: Int,
    ): Response<Disease>

    @PUT("disease/{id}")
    suspend fun putUserDisease(
        @Path("id") id: Int,
        @Body disease: Disease
    ): Response<ResponseBody>

    @DELETE("disease/{id}")
    suspend fun deleteUserDisease(
        @Path("id") id: Int,
    ): Response<ResponseBody>

    @POST("disease")
    suspend fun postUserDisease(
        @Body disease: Disease
    ): Response<ResponseBody>

    @GET("allergy/{id}")
    suspend fun getAllergy(
        @Path("id") id: Int,
    ): Response<Allergy>

    @PUT("allergy/{id}")
    suspend fun putUserAllergy(
        @Path("id") id: Int,
        @Body allergy: Allergy
    ): Response<ResponseBody>

    @DELETE("allergy/{id}")
    suspend fun deleteUserAllergy(
        @Path("id") id: Int,
    ): Response<ResponseBody>

    @POST("allergy")
    suspend fun postUserMedicalInfoAllergies(
        @Body allergy: Allergy
    ): Response<ResponseBody>

    @GET("enum/allergy_type")
    suspend fun getAllergyTypes(): Response<List<String>>

    @GET("enum/rh_type")
    suspend fun getRhTypes(): Response<List<String>>

    @GET("enum/blood_type")
    suspend fun getBloodTypes(): Response<List<String>>
}