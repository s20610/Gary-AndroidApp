package com.example.mobileclient.api

import com.example.mobileclient.model.*
import okhttp3.ResponseBody
import retrofit2.Response

class Repository {
    suspend fun getLoginResponse(credentials: Credentials): Response<String> {
        return RetrofitInstance.api.getLoginResponse(credentials)
    }

    suspend fun registerNewUser(newUser: NewUser): Response<ResponseBody>{
        return RetrofitInstance.api.registerNewUser(newUser)
    }
    suspend fun getUserMedicalInfo(id: Int): Response<MedicalInfo>{
        return RetrofitInstance.api.getUserMedicalInfo(id)
    }
    suspend fun putUserMedicalInfoBlood(id: Int, blood : BloodType) : Response<ResponseBody>{
        return RetrofitInstance.api.putUserMedicalInfoBlood(id, blood)
    }

    suspend fun postUserMedicalInfo(id: Int, medicalInfo: MedicalInfo): Response<ResponseBody>{
        return RetrofitInstance.api.postUserMedicalInfo(id, medicalInfo)
    }


}