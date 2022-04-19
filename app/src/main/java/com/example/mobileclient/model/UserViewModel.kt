package com.example.mobileclient.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileclient.api.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class UserViewModel() : ViewModel() {
    private var repository: Repository = Repository()
    var loginResponse: MutableLiveData<Response<String>> = MutableLiveData()


    fun getLoginResponse(credentials: Credentials) {
        viewModelScope.launch {
            val response = repository.getLoginResponse(credentials)
            loginResponse.value = response
        }
    }
}