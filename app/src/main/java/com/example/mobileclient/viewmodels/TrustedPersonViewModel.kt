package com.example.mobileclient.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileclient.api.Repository
import com.example.mobileclient.model.TrustedPerson
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response

class TrustedPersonViewModel : ViewModel() {
    val getTrustedPersonResponse: MutableLiveData<TrustedPerson> = MutableLiveData()
    val postCallResponseBody: MutableLiveData<Response<ResponseBody>> = MutableLiveData()
    val deleteCallResponseBody: MutableLiveData<Response<ResponseBody>> = MutableLiveData()
    val updateCallResponseBody: MutableLiveData<Response<ResponseBody>> = MutableLiveData()

    fun getTrustedPerson(userEmail: String) {
        viewModelScope.launch {
            try {
                val response = Repository.getTrustedPerson(userEmail)
                if(response.isSuccessful){
                    getTrustedPersonResponse.value = response.body()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun postTrustedPerson(trustedPerson: TrustedPerson) {
        viewModelScope.launch {
            try {
                val response = Repository.postTrustedPerson(trustedPerson)
                postCallResponseBody.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun putTrustedPerson(trustedPerson: TrustedPerson) {
        viewModelScope.launch {
            try {
                val response = Repository.putTrustedPerson(trustedPerson)
                updateCallResponseBody.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteTrustedPerson(userEmail: String) {
        viewModelScope.launch {
            try {
                val response = Repository.deleteTrustedPerson(userEmail)
                deleteCallResponseBody.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}