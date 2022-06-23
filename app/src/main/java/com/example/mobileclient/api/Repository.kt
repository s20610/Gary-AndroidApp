package com.example.mobileclient.api

import com.example.mobileclient.model.*
import com.example.mobileclient.model.Credentials
import com.example.mobileclient.model.MedicalInfo
import com.example.mobileclient.model.NewUser
import com.example.mobileclient.model.Tutorial
import com.example.mobileclient.model.User
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response

class Repository {
    suspend fun getLoginResponse(credentials: Credentials): Response<String> {
        return RetrofitInstance.api.getLoginResponse(credentials)
    }

    suspend fun registerNewUser(newUser: NewUser): Response<ResponseBody> {
        return RetrofitInstance.api.registerNewUser(newUser)
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

    suspend fun getUserInfo(id: Int): Response<User> {
        return RetrofitInstance.api.getUserInfo(id)
    }

    suspend fun getTutorials(): Response<List<Tutorial>> {
        return RetrofitInstance.api.getTutorials()
    }
}