package com.example.mobileclient.api

import com.example.mobileclient.model.*
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

    @GET("auth/user/info")
    suspend fun getUserInfo(
        @Header("Authorization") token: String
    ): Response<UserInfoResponse>

    @PUT("auth/password/change")
    suspend fun changePassword(
        @Header("Authorization") token: String,
        @Body passwordChange: passwordChange
    ): Response<ResponseBody>

    @POST("auth/password/reset")
    suspend fun resetPassword(
        @Body email: Email
    ): Response<ResponseBody>

    @PUT("auth/password/reset")
    suspend fun confirmResetPassword(
        @Query("token") token: String,
        @Body newPassword: newPassword
    ): Response<ResponseBody>

    //Tutorial api calls
    @GET("tutorial")
    suspend fun getTutorials(): Response<List<Tutorial>>

    @GET("tutorial/{id}")
    suspend fun getTutorial(@Path("id") tutorialId: Int): Response<Tutorial>

    @POST("tutorial/{tutorialId}/{email}")
    suspend fun addTutorialRating(
        @Path("tutorialId") tutorialId: Int,
        @Path("email") email: String,
        @Body rating: Review
    ): Response<ResponseBody>

    //Medical info api calls

    @GET("medical_info/user/bandcode/{bandCode}")
    suspend fun getMedicalInfoByBandCode(
        @Path("bandCode") bandCode: String
    ): Response<MedicalInfo>

    @GET("medical_info/user/{email}")
    suspend fun getUserMedicalInfo(
        @Path("email") userEmail: String
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

    //enum types

    @GET("enum/allergy_type")
    suspend fun getAllergyTypes(): Response<List<String>>

    @GET("enum/rh_type")
    suspend fun getRhTypes(): Response<List<String>>

    @GET("enum/blood_type")
    suspend fun getBloodTypes(): Response<List<String>>

    @GET("enum/ambulance_types")
    suspend fun getAmbulanceTypes(): Response<List<String>>

    @GET("enum/ambulance_states")
    suspend fun getAmbulanceStates(): Response<List<String>>

    @GET("enum/ambulance_classes")
    suspend fun getAmbulanceClasses(): Response<List<String>>

    @GET("enum/emergency_type")
    suspend fun getEmergencyTypes(): Response<List<String>>

    @GET("enum/facility_type")
    suspend fun getFacilityTypes(): Response<List<String>>

    // Trusted Person

    @GET("trusted/{email}")
    suspend fun getTrustedPerson(
        @Path("email") userEmail: String,
    ): Response<TrustedPerson>

    @POST("trusted")
    suspend fun postTrustedPerson(
        @Body trustedPerson: TrustedPerson
    ): Response<ResponseBody>

    @PUT("trusted")
    suspend fun putTrustedPerson(
        @Body trustedPerson: TrustedPerson
    ): Response<ResponseBody>

    @DELETE("trusted/{email}")
    suspend fun deleteTrustedPerson(
        @Path("email") userEmail: String,
    ): Response<ResponseBody>

    //Accident Report
    @GET("accident_report/{id}")
    suspend fun getAccidentReport(
        @Path("id") id: Int,
    ): Response<AccidentReport>

    @GET("accident_report/user/{email}")
    suspend fun getUserAccidentReports(
        @Path("email") userEmail: String,
    ): Response<List<AccidentReport>>

    @POST("accident_report")
    suspend fun postAccidentReport(
        @Body accidentReport: AccidentReport
    ): Response<ResponseBody>

    //Employee shifts
    @GET("employee/shift/start")
    suspend fun startShift(@Header("Authorization") token: String): Response<ResponseBody>

    @GET("employee/shift/end")
    suspend fun endShift(@Header("Authorization") token: String): Response<ResponseBody>

    @GET("employee/schedule")
    suspend fun getSchedule(@Header("Authorization") token: String): Response<WholeSchedule>

    //Get current assigned ambulance
    @GET("employee/medic/assigned-to")
    suspend fun getAssignedAmbulance(@Header("Authorization") token: String): Response<Ambulance>


    //Facilities
    @GET("facility")
    suspend fun getFacilities(): Response<List<Facility>>

    //Ambulance
    @GET("ambulance/{licensePlate}/equipment")
    suspend fun getAmbulanceEquipment(
        @Path("licensePlate") licensePlate: String, @Header("Authorization") token: String
    ): Response<List<AmbulanceEquipment>>

    @POST("ambulance/{licensePlate}/state/{state}")
    suspend fun changeAmbulanceState(
        @Path("licensePlate") licensePlate: String,
        @Path("state") state: String
    ): Response<ResponseBody>

    @POST("ambulance/{licensePlate}/location")
    suspend fun updateAmbulanceLocation(
        @Path("licensePlate") licensePlate: String,
        @Body location: Location
    ): Response<ResponseBody>

    @POST("ambulance/{licensePlate}/items/add/{itemId}")
    suspend fun addAmbulanceItem(
        @Path("licensePlate") licensePlate: String,
        @Path("itemId") itemId: Int,
        @Query("count") type: Int
    ): Response<ResponseBody>

    @DELETE("ambulance/{licensePlate}/items/remove/{itemId}")
    suspend fun removeAmbulanceItem(
        @Path("licensePlate") licensePlate: String,
        @Path("itemId") itemId: Int, @Query("count") type: Int
    ): Response<ResponseBody>

    @GET("ambulance/{licensePlate}/incident")
    suspend fun getAmbulanceIncident(
        @Path("licensePlate") licensePlate: String
    ): Response<Incident>

    @POST("backup")
    suspend fun callForBackup(@Header("Authorization") token: String,@Body backup: Backup): Response<ResponseBody>

    @GET("backup/{id}")
    suspend fun getSentBackup(@Query("token") token: String,@Path("id") id: Int): Response<Backup>

}
