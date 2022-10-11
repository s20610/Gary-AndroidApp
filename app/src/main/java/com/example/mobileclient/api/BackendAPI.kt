package com.example.mobileclient.api

import com.example.mobileclient.fragments.Incident
import com.example.mobileclient.model.*

import com.example.mobileclient.model.Credentials
import com.example.mobileclient.model.MedicalInfo
import com.example.mobileclient.model.NewUser
import com.example.mobileclient.model.Tutorial
import com.example.mobileclient.model.User
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface BackendAPI {
    //Login api calls
    @POST("login")
    suspend fun getLoginResponse(
        @Body credentials: Credentials
    ): Response<String>

    @POST("register/normal")
    suspend fun registerNewUser(
        @Body newUser: NewUser
    ): Response<ResponseBody>

    //User api calls
    @GET("user/{id}")
    suspend fun getUser(
        @Path("id") userId: Int
    ): Response<User>

    @PUT("user/{id}")
    suspend fun putUser(
        @Path("id") userId: Int,
        @Body newUser: User
    ): Response<ResponseBody>

    @PUT("user/info/{id}")
    suspend fun putUserInfo(
        @Path("id") userId: Int,
        @Body newUserInfo: User
    ): Response<ResponseBody>

    @POST("user/info/{id}")
    suspend fun postUserInfo(
        @Path("id") userId: Int,
        @Body newUserInfo: User
    ): Response<ResponseBody>

    //Emergency api calls
    @POST("emergency/new")
    suspend fun createNewEmergency(
        @Body newEmergencyInfo: Emergency
    ): Response<ResponseBody>

    @POST("incident/new")
    suspend fun createNewIncident(
        @Body newIncidentInfo: Incidentt
    ): Response<ResponseBody>

    //Victim api calls
    @GET("victim/{id}")
    suspend fun getVictim(
        @Path("id") victimId: Int,
    ): Response<User>

    @PUT("victim/{id}")
    suspend fun putVictim(
        @Path("id") victimId: Int,
        @Body newVictim: User
    ): Response<ResponseBody>

    @POST("victim")
    suspend fun postVictim(
        @Body newVictim: User
    ): Response<ResponseBody>

    //Tutorial api calls
    @GET("tutorial")
    suspend fun getTutorials(): Response<List<Tutorial>>

    @GET("tutorial/{id}")
    suspend fun getTutorial(@Path("id") tutorialId: Int): Response<Tutorial>

    //Staff api calls
    @GET("staff/{id}")
    suspend fun getStaff(
        @Path("id") staffId: Int,
    ): Response<User> //TODO(Create Staff data class)

    @PUT("staff/{id}")
    suspend fun putStaff(
        @Path("id") staffId: Int,
        @Body newStaff: User //TODO(Create Staff data class)
    ): Response<ResponseBody>

    //Review api calls
    @GET("review/{id}")
    suspend fun getReview(
        @Path("id") reviewId: Int,
    ): Response<Review>

    @PUT("review/{id}")
    suspend fun putReview(
        @Path("id") reviewId: Int,
        @Body newReview: Review
    ): Response<ResponseBody>

    @POST("review/{id}")
    suspend fun postReview(
        @Path("id") reviewId: Int,
        @Body newReview: Review
    ): Response<ResponseBody>

    //Medical info api calls
    @GET("medicalInfo/{id}")
    suspend fun getUserMedicalInfo(
        @Path("id") userId: Int
    ): Response<MedicalInfo>

    @PUT("medicalInfo/{id}")
    suspend fun putUserMedicalInfo(
        @Path("id") userId: Int,
        @Body newMedicalInfo: MedicalInfo
    ): Response<ResponseBody>

    @PUT("medicalInfo/blood/{id}")
    suspend fun putUserMedicalInfoBlood(
        @Path("id") userId: Int,
        @Body blood: RequestBody
    ): Response<ResponseBody>

    @PUT("medicalInfo/chronic/{id}")
    suspend fun putUserMedicalInfoChronic(
        @Path("id") userId: Int,
        @Body chronic: String
    ): Response<ResponseBody>

    @PUT("medicalInfo/allergies/{id}")
    suspend fun putUserMedicalInfoAllergies(
        @Path("id") userId: Int,
        @Body allergies: List<Allergy>
    ): Response<ResponseBody>

}