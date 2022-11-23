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
    private var ambulanceTypesResponse: MutableLiveData<List<String>> = MutableLiveData()
    private var ambulanceStatesResponse: MutableLiveData<List<String>> = MutableLiveData()
    private var ambulanceClassesResponse: MutableLiveData<List<String>> = MutableLiveData()
    private var emergencyTypesResponse: MutableLiveData<List<String>> = MutableLiveData()
    private var facilityTypesResponse: MutableLiveData<List<String>> = MutableLiveData()

    val allergyTypes: LiveData<List<String>> = allergyTypesResponse
    val bloodTypes: LiveData<List<String>> = bloodTypesResponse
    val rhTypes: LiveData<List<String>> = rhTypesResponse
    val ambulanceTypes: LiveData<List<String>> = ambulanceTypesResponse
    val ambulanceStates: LiveData<List<String>> = ambulanceStatesResponse
    val ambulanceClasses: LiveData<List<String>> = ambulanceClassesResponse
    val emergencyTypes: LiveData<List<String>> = emergencyTypesResponse
    val facilityTypes: LiveData<List<String>> = facilityTypesResponse

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

    fun getAmbulanceTypes() {
        viewModelScope.launch {
            try {
                val response = repository.getAmbulanceTypes()
                ambulanceTypesResponse.value = response.body()
            } catch (e: ConnectException) {
                Log.d("Connection exception", e.stackTraceToString())
            }
        }
    }

    fun getAmbulanceStates() {
        viewModelScope.launch {
            try {
                val response = repository.getAmbulanceStates()
                ambulanceStatesResponse.value = response.body()
            } catch (e: ConnectException) {
                Log.d("Connection exception", e.stackTraceToString())
            }
        }
    }

    fun getAmbulanceClasses(){
        viewModelScope.launch {
            try {
                val response = repository.getAmbulanceClasses()
                ambulanceClassesResponse.value = response.body()
            } catch (e: ConnectException) {
                Log.d("Connection exception", e.stackTraceToString())
            }
        }
    }

    fun getEmergencyTypes() {
        viewModelScope.launch {
            try {
                val response = repository.getEmergencyTypes()
                emergencyTypesResponse.value = response.body()
            } catch (e: ConnectException) {
                Log.d("Connection exception", e.stackTraceToString())
            }
        }
    }

    fun getFacilityTypes() {
        viewModelScope.launch {
            try {
                val response = repository.getFacilityTypes()
                emergencyTypesResponse.value = response.body()
            } catch (e: ConnectException) {
                Log.d("Connection exception", e.stackTraceToString())
            }
        }
    }
}