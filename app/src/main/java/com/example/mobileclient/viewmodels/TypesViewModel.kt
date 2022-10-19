package com.example.mobileclient.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileclient.api.Repository
import kotlinx.coroutines.launch
import retrofit2.Response
import java.net.ConnectException

class TypesViewModel: ViewModel() {
    private var repository: Repository = Repository
    private var allergyTypesResponse: MutableLiveData<List<String>> = MutableLiveData()
    private var bloodTypesResponse: MutableLiveData<List<String>> = MutableLiveData()
    private var rhTypesResponse: MutableLiveData<List<String>> = MutableLiveData()
    val allergyTypes: LiveData<List<String>> = allergyTypesResponse
    val bloodTypes: LiveData<List<String>> = bloodTypesResponse
    val rhTypes: LiveData<List<String>> = rhTypesResponse

    fun getAllergyTypes() {
        viewModelScope.launch {
            try {
                val response = repository.getAllergyTypes()
                allergyTypesResponse.value = response.body()
            } catch (e: ConnectException) {
                Log.d("Connection exception", e.stackTraceToString())
            }
        }
    }

    fun getBloodTypes() {
        viewModelScope.launch {
            try {
                val response = repository.getBloodTypes()
                bloodTypesResponse.value = response.body()
            } catch (e: ConnectException) {
                Log.d("Connection exception", e.stackTraceToString())
            }
        }
    }

    fun getRhTypes() {
        viewModelScope.launch {
            try {
                val response = repository.getRhTypes()
                rhTypesResponse.value = response.body()
            } catch (e: ConnectException) {
                Log.d("Connection exception", e.stackTraceToString())
            }
        }
    }
}