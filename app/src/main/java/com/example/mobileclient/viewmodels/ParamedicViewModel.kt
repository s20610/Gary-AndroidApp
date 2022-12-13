package com.example.mobileclient.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileclient.api.Repository
import com.example.mobileclient.model.Ambulance
import com.example.mobileclient.model.AmbulanceEquipment
import com.example.mobileclient.model.Location
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response

class ParamedicViewModel : ViewModel() {
    var employeeShiftResponse: MutableLiveData<Response<ResponseBody>> = MutableLiveData()
    var ambulanceEquipmentResponse: MutableLiveData<Response<List<AmbulanceEquipment>>> =
        MutableLiveData()
    var updateAmbulanceInfoResponse: MutableLiveData<Response<ResponseBody>> = MutableLiveData()
    var currentAmbulanceResponse: MutableLiveData<Response<Ambulance>> = MutableLiveData()

    fun startEmployeeShift(token: String) {
        viewModelScope.launch {
            try {
                val response = Repository.startEmployeeShift(token)
                employeeShiftResponse.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun endEmployeeShift(token: String) {
        viewModelScope.launch {
            try {
                val response = Repository.endEmployeeShift(token)
                employeeShiftResponse.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getAmbulanceEquipment(licensePlate: String) {
        viewModelScope.launch {
            try {
                val response = Repository.getAmbulanceEquipment(licensePlate)
                ambulanceEquipmentResponse.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun changeAmbulanceState(licensePlate: String, state: String) {
        viewModelScope.launch {
            try {
                val response = Repository.changeAmbulanceState(licensePlate, state)
                updateAmbulanceInfoResponse.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateAmbulanceLocation(licensePlate: String, location: Location) {
        viewModelScope.launch {
            try {
                val response = Repository.updateAmbulanceLocation(licensePlate, location)
                updateAmbulanceInfoResponse.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun addAmbulanceItem(licensePlate: String, itemId: Int) {
        viewModelScope.launch {
            try {
                val response = Repository.addAmbulanceItem(licensePlate, itemId)
                updateAmbulanceInfoResponse.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun removeAmbulanceItem(licensePlate: String, itemId: Int) {
        viewModelScope.launch {
            try {
                val response = Repository.removeAmbulanceItem(licensePlate, itemId)
                updateAmbulanceInfoResponse.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getCurrentAmbulance(token: String) {
        viewModelScope.launch {
            try {
                val response = Repository.getAssignedAmbulance(token)
                currentAmbulanceResponse.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}