package com.example.mobileclient.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileclient.api.Repository
import com.example.mobileclient.model.*
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response

class ParamedicViewModel : ViewModel() {
    var employeeShiftResponse: MutableLiveData<Response<ResponseBody>> = MutableLiveData()
    var ambulanceEquipmentResponse: MutableLiveData<Response<List<AmbulanceEquipment>>> =
        MutableLiveData()
    var updateAmbulanceInfoResponse: MutableLiveData<Response<ResponseBody>> = MutableLiveData()
    var currentAmbulanceResponse: MutableLiveData<Response<Ambulance>> = MutableLiveData()
    var scheduleResponse: MutableLiveData<Response<WholeSchedule>> = MutableLiveData()
    var isOnBreak: Boolean = false
    var callForBackupResponse: MutableLiveData<Response<ResponseBody>> = MutableLiveData()
var sentBackupResponse: MutableLiveData<Response<Backup>> = MutableLiveData()

    fun startEmployeeShift(token: String) {
        viewModelScope.launch {
            try {
                val response = Repository.startEmployeeShift("Bearer $token")
                employeeShiftResponse.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun endEmployeeShift(token: String) {
        viewModelScope.launch {
            try {
                val response = Repository.endEmployeeShift("Bearer $token")
                employeeShiftResponse.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getSchedule(token: String) {
        viewModelScope.launch {
            try {
                val response = Repository.getEmployeeShifts("Bearer $token")
                scheduleResponse.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getAmbulanceEquipment(licensePlate: String, token: String) {
        viewModelScope.launch {
            try {
                val response = Repository.getAmbulanceEquipment(licensePlate,token)
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
                val response = Repository.getAssignedAmbulance("Bearer $token")
                currentAmbulanceResponse.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun callForBackup(backup: Backup){
        viewModelScope.launch {
            try {
                val response = Repository.callForBackup(backup)
                callForBackupResponse.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getSentBackup(id: Int){
        viewModelScope.launch {
            try {
                val response = Repository.getSentBackup(id)
                sentBackupResponse.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}