package com.example.mobileclient.api

import com.example.mobileclient.model.*
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response

object Repository {
    suspend fun getLoginResponse(credentials: Credentials): Response<String> {
        return RetrofitInstance.api.getLoginResponse(credentials)
    }

    suspend fun registerNewUser(newUser: NewUser): Response<ResponseBody> {
        return RetrofitInstance.api.registerNewUser(newUser)
    }

    suspend fun getUserInfo(id: Int): Response<User> {
        return RetrofitInstance.api.getUser(id)
    }

    suspend fun putUser(id: Int, newUser: User): Response<ResponseBody> {
        return RetrofitInstance.api.putUser(id, newUser)
    }

    suspend fun putUserInfo(id: Int, newUser: User): Response<ResponseBody> {
        return RetrofitInstance.api.putUserInfo(id, newUser)
    }

    suspend fun postUserInfo(id: Int, newUser: User): Response<ResponseBody> {
        return RetrofitInstance.api.postUserInfo(id, newUser)
    }

    suspend fun createNewEmergency(newEmergencyInfo: Emergency): Response<ResponseBody> {
        return RetrofitInstance.api.createNewEmergency(newEmergencyInfo)
    }

    suspend fun getVictim(id: Int): Response<User> {
        return RetrofitInstance.api.getVictim(id)
    }

    suspend fun putVictim(id: Int, newVictim: User): Response<ResponseBody> {
        return RetrofitInstance.api.putVictim(id, newVictim)
    }

    suspend fun postVictim(newVictim: User): Response<ResponseBody> {
        return RetrofitInstance.api.postVictim(newVictim)
    }

    suspend fun getTutorials(): Response<List<Tutorial>> {
        return RetrofitInstance.api.getTutorials()
    }

    suspend fun getTutorial(id: Int): Response<Tutorial> {
        return RetrofitInstance.api.getTutorial(id)
    }

    suspend fun getStaff(id: Int): Response<User> {
        return RetrofitInstance.api.getStaff(id)
    }

    suspend fun putStaff(id: Int, newStaff: User): Response<ResponseBody> {
        return RetrofitInstance.api.putStaff(id, newStaff)
    }

    suspend fun getUserMedicalInfo(id: Int): Response<MedicalInfo> {
        return RetrofitInstance.api.getUserMedicalInfo(id)
    }

    suspend fun putUserMedicalInfoBlood(id: Int, blood: RequestBody): Response<ResponseBody> {
        return RetrofitInstance.api.putUserMedicalInfoBlood(id, blood)
    }

    suspend fun postUserMedicalInfo(id: Int, medicalInfo: MedicalInfo): Response<ResponseBody> {
        return RetrofitInstance.api.putUserMedicalInfo(id, medicalInfo)
    }

    suspend fun postUserMedicalInfoChronic(id: Int, chronic: String): Response<ResponseBody> {
        return RetrofitInstance.api.putUserMedicalInfoChronic(id, chronic)
    }

    suspend fun postUserMedicalInfoAllergies(
        id: Int,
        allergies: List<Allergy>
    ): Response<ResponseBody> {
        return RetrofitInstance.api.putUserMedicalInfoAllergies(id, allergies)
    }


}