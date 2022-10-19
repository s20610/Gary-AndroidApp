package com.example.mobileclient.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileclient.api.Repository
import com.example.mobileclient.model.*
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import java.net.ConnectException

class UserViewModel : ViewModel() {
    private var repository: Repository = Repository
    var loginResponse: MutableLiveData<Response<AuthResponse>> = MutableLiveData()
    var registerResponse: MutableLiveData<Response<ResponseBody>> = MutableLiveData()
    var getUserMedicalInfoResponse: MutableLiveData<Response<MedicalInfo>> = MutableLiveData()

    //    var putMedicalInfoBloodResponse: MutableLiveData<Response<ResponseBody>> = MutableLiveData()
//    var postUserMedicalInfoResponse: MutableLiveData<Response<ResponseBody>> = MutableLiveData()
    var getUserInfoResponse: MutableLiveData<Response<User>> = MutableLiveData()
    var updateCallResponseBody: MutableLiveData<Response<ResponseBody>> = MutableLiveData()
    var postCallResponseBody: MutableLiveData<Response<ResponseBody>> = MutableLiveData()
    var victimResponse: MutableLiveData<Response<User>> = MutableLiveData()

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

    fun putUser(id: Int, newUser: User) {
        viewModelScope.launch {
            try {
                val response = repository.putUser(id, newUser)
                updateCallResponseBody.value = response
            } catch (e: ConnectException) {
                Log.d("Connection exception", e.stackTraceToString())
            } catch (e: Exception) {
                Log.d("Put User exception", e.stackTraceToString())
            }
        }
    }

    fun getUserInfo(id: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getUserInfo(id)
                if (response.code() == 200) {
                    getUserInfoResponse.value = response
                }
            } catch (e: ConnectException) {
                Log.d("Connection exception", e.stackTraceToString())
            } catch (e: Exception) {
                Log.d("getUserInfo exception", e.stackTraceToString())
            }
        }
    }

    fun putUserInfo(id: Int, newUser: User) {
        viewModelScope.launch {
            try {
                val response = repository.putUserInfo(id, newUser)
                updateCallResponseBody.value = response
            } catch (e: ConnectException) {
                Log.d("Connection exception", e.stackTraceToString())
            } catch (e: Exception) {
                Log.d("getUserInfo exception", e.stackTraceToString())
            }
        }
    }

    fun postUserInfo(id: Int, newUser: User) {
        viewModelScope.launch {
            try {
                val response = repository.postUserInfo(id, newUser)
                postCallResponseBody.value = response
            } catch (e: ConnectException) {
                Log.d("Connection exception", e.stackTraceToString())
            } catch (e: Exception) {
                Log.d("getUserInfo exception", e.stackTraceToString())
            }
        }
    }

    fun getVictim(id: Int){
        viewModelScope.launch {
            try {
                val response = repository.getVictim(id)
                victimResponse.value = response
            } catch (e: ConnectException) {
                Log.d("Connection exception", e.stackTraceToString())
            } catch (e: Exception) {
                Log.d("getUserInfo exception", e.stackTraceToString())
            }
        }
    }

    fun putVictim(id: Int, newVictim: User){
        viewModelScope.launch {
            try {
                val response = repository.putVictim(id,newVictim)
                updateCallResponseBody.value = response
            } catch (e: ConnectException) {
                Log.d("Connection exception", e.stackTraceToString())
            } catch (e: Exception) {
                Log.d("getUserInfo exception", e.stackTraceToString())
            }
        }
    }

    fun postVictim(id: Int, newVictim: User){
        viewModelScope.launch {
            try {
                val response = repository.postVictim(newVictim)
                postCallResponseBody.value = response
            } catch (e: ConnectException) {
                Log.d("Connection exception", e.stackTraceToString())
            } catch (e: Exception) {
                Log.d("getUserInfo exception", e.stackTraceToString())
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
                updateCallResponseBody.value = response
                getUserInfo(id)
            } catch (e: Exception) {
                Log.d("Medical info exception", e.stackTraceToString())
            }
        }
    }

    fun postUserDisease(disease: Disease) {
        viewModelScope.launch {
            try {
                val response = repository.postUserDisease(disease)
                postCallResponseBody.value = response
            } catch (e: ConnectException) {
                Log.d("Connection exception", e.stackTraceToString())
            } catch (e: Exception) {
                Log.d("postMedicalInfoResponse exception", e.stackTraceToString())
            }
        }
    }

    fun postUserAllergy(allergy: Allergy) {
        viewModelScope.launch {
            try {
                val response = repository.postUserAllergy(allergy)
                postCallResponseBody.value = response
            } catch (e: ConnectException) {
                Log.d("Connection exception", e.stackTraceToString())
            } catch (e: Exception) {
                Log.d("postMedicalInfoResponse exception", e.stackTraceToString())
            }
        }
    }


}