package com.example.mobileclient.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileclient.api.Repository
import com.example.mobileclient.model.*
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response
import java.net.ConnectException

class UserViewModel : ViewModel() {
    private var repository: Repository = Repository
    var loginResponse: MutableLiveData<Response<AuthResponse>> = MutableLiveData()
    var registerResponse: MutableLiveData<Response<ResponseBody>> = MutableLiveData()
    var getUserMedicalInfoResponse: MutableLiveData<Response<MedicalInfo>> = MutableLiveData()
    var updateCallResponseBody: MutableLiveData<Response<ResponseBody>> = MutableLiveData()
    var postCallResponseBody: MutableLiveData<Response<ResponseBody>> = MutableLiveData()
    var deleteCallResponseBody: MutableLiveData<Response<ResponseBody>> = MutableLiveData()
    private var chosenAllergy: MutableLiveData<Allergy> = MutableLiveData()
    private var chosenDisease: MutableLiveData<Disease> = MutableLiveData()

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

    fun getUserMedicalInfo(userEmail: String) {
        viewModelScope.launch {
            try {
                val response = repository.getUserMedicalInfo(userEmail)
                getUserMedicalInfoResponse.value = response
            } catch (e: ConnectException) {
                Log.d("Connection exception", e.stackTraceToString())
            } catch (e: Exception) {
                Log.d("getMedicalInfo exception", e.stackTraceToString())
            }
        }
    }

    fun putUserMedicalInfoBlood(id: Int, blood: Blood) {
        viewModelScope.launch {
            try {
                val response = repository.putUserBlood(id, blood)
                updateCallResponseBody.value = response
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

    fun putUserAllergy(id: Int, allergy: Allergy) {
        viewModelScope.launch {
            try {
                val response = repository.putUserAllergy(id, allergy)
                updateCallResponseBody.value = response
            } catch (e: Exception) {
                Log.d("Medical info exception", e.stackTraceToString())
            }
        }
    }

    fun putUserDisease(id: Int, disease: Disease) {
        viewModelScope.launch {
            try {
                val response = repository.putUserDisease(id, disease)
                updateCallResponseBody.value = response
            } catch (e: Exception) {
                Log.d("Medical info exception", e.stackTraceToString())
            }
        }
    }

    fun deleteUserAllergy(id: Int) {
        viewModelScope.launch {
            try {
                val response = repository.deleteUserAllergy(id)
                deleteCallResponseBody.value = response
            } catch (e: Exception) {
                Log.d("Medical info exception", e.stackTraceToString())
            }
        }
    }

    fun deleteUserDisese(id: Int) {
        viewModelScope.launch {
            try {
                val response = repository.deleteUserDisease(id)
                deleteCallResponseBody.value = response
            } catch (e: Exception) {
                Log.d("Medical info exception", e.stackTraceToString())
            }
        }
    }

    fun getChosenAllergy(): Allergy? {
        return chosenAllergy.value
    }

    fun getChosenDisease(): Disease? {
        return chosenDisease.value
    }

    fun setChosenAllergy(allergy: Allergy) {
        chosenAllergy.value = allergy
    }

    fun setChosenDisease(disease: Disease) {
        chosenDisease.value = disease
    }


}