package com.example.mobileclient.model

import android.util.Log
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileclient.api.Repository
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import java.net.ConnectException

class UserViewModel : ViewModel() {
    private var repository: Repository = Repository
    var loginResponse: MutableLiveData<Response<String>> = MutableLiveData()
    var registerResponse: MutableLiveData<Response<ResponseBody>> = MutableLiveData()
    var getUserMedicalInfoResponse: MutableLiveData<Response<MedicalInfo>> = MutableLiveData()
    var putMedicalInfoBloodResponse: MutableLiveData<Response<ResponseBody>> = MutableLiveData()
    var postUserMedicalInfoResponse: MutableLiveData<Response<ResponseBody>> = MutableLiveData()
    var getUserInfoResponse: MutableLiveData<Response<User>> = MutableLiveData()


    fun getLoginResponse(credentials: Credentials) {
        viewModelScope.launch {
            try {
                val response = repository.getLoginResponse(credentials)
                loginResponse.value = response
            } catch (e: ConnectException) {
                Log.d("Connection exception", e.stackTraceToString())
            } catch (e: Exception) {
                Log.d("Login exception", e.stackTraceToString())
            }
        }
    }

    fun registerNewUser(newUser: NewUser) {
        viewModelScope.launch {
            try {
                val response = repository.registerNewUser(newUser)
                registerResponse.value = response
            } catch (e: ConnectException) {
                Log.d("Connection exception", e.stackTraceToString())
            } catch (e: Exception) {
                Log.d("registerNewUser exception", e.stackTraceToString())
            }
        }
    }

    fun getMedicalInfoResponse(id: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getUserMedicalInfo(id)
                getUserMedicalInfoResponse.value = response
            } catch (e: ConnectException) {
                Log.d("Connection exception", e.stackTraceToString())
            } catch (e: Exception) {
                Log.d("getMedicalInfo exception", e.stackTraceToString())
            }
        }
    }

    fun putUserMedicalInfoBlood(id: Int, blood: RequestBody) {
        viewModelScope.launch {
            try {
                val response = repository.putUserMedicalInfoBlood(id, blood)
                putMedicalInfoBloodResponse.value = response
                getUserInfo(id)
            } catch (e: Exception) {
                Log.d("Medical info exception", e.stackTraceToString())
            }
        }
    }

    fun postMedicalInfo(id: Int, medicalInfo: MedicalInfo) {
        viewModelScope.launch {
            try {
                val response = repository.postUserMedicalInfo(id, medicalInfo)
                postUserMedicalInfoResponse.value = response
            } catch (e: ConnectException) {
                Log.d("Connection exception", e.stackTraceToString())
            } catch (e: Exception) {
                Log.d("postMedicalInfoResponse exception", e.stackTraceToString())
            }
        }
    }

    fun getUserInfo(id: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getUserInfo(id)
                getUserInfoResponse.value = response
            } catch (e: ConnectException) {
                Log.d("Connection exception", e.stackTraceToString())
            } catch (e: Exception) {
                Log.d("getUserInfo exception", e.stackTraceToString())
            }
        }
    }
}