package com.example.mobileclient.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileclient.api.Repository
import com.example.mobileclient.model.*
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response
import kotlin.math.absoluteValue

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
    var assignedIncidentResponse: MutableLiveData<Response<Incident>> = MutableLiveData()
    var victimMedicalInfoResponse: MutableLiveData<Response<MedicalInfo>> = MutableLiveData()
    var postCasualtiesResponse: MutableLiveData<Response<ResponseBody>> = MutableLiveData()
    var casualtiesResponse: MutableLiveData<Response<List<Casualty>>> = MutableLiveData()
    var putCasualtyResponse: MutableLiveData<Response<ResponseBody>> = MutableLiveData()
    var pickedVictimInfo: Casualty? = null
    private var equipmentUpdateMap: HashMap<Int, Int> = HashMap()

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

    fun getMedicalInfoWithBandCode(bandCode: String) {
        viewModelScope.launch {
            try {
                val response = Repository.getMedicalInfoByBandCode(bandCode)
                victimMedicalInfoResponse.value = response
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
                val response = Repository.getAmbulanceEquipment(licensePlate, "Bearer $token")
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

    private fun addAmbulanceItem(licensePlate: String, itemId: Int, count: Int) {
        viewModelScope.launch {
            try {
                val response = Repository.addAmbulanceItem(licensePlate, itemId, count)
                updateAmbulanceInfoResponse.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun removeAmbulanceItem(licensePlate: String, itemId: Int, count: Int) {
        viewModelScope.launch {
            try {
                val response = Repository.removeAmbulanceItem(licensePlate, itemId, count)
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

    fun callForBackup(backup: Backup, token: String) {
        viewModelScope.launch {
            try {
                val response = Repository.callForBackup(backup, "Bearer $token")
                callForBackupResponse.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getSentBackup(id: Int, token: String) {
        viewModelScope.launch {
            try {
                val response = Repository.getSentBackup(id, "Bearer $token")
                sentBackupResponse.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getAssignedIncident(licensePlate: String) {
        viewModelScope.launch {
            try {
                val response = Repository.getAssignedIncident(licensePlate)
                assignedIncidentResponse.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun addEquipmentUpdate(itemId: Int) {
        equipmentUpdateMap[itemId] = equipmentUpdateMap[itemId]?.plus(1) ?: 1
    }

    fun removeEquipmentUpdate(itemId: Int) {
        equipmentUpdateMap[itemId] = equipmentUpdateMap[itemId]?.minus(1) ?: -1
    }

    private fun clearEquipmentUpdateMap() {
        equipmentUpdateMap.clear()
    }

    fun updateEquipment(licensePlate: String) {
        equipmentUpdateMap.forEach { (itemId, count) ->
            if (count > 0) {
                addAmbulanceItem(licensePlate, itemId, count)
            } else if (count < 0) {
                removeAmbulanceItem(licensePlate, itemId, count.absoluteValue)
            }
        }
        clearEquipmentUpdateMap()
    }

    fun postCasualties(id: Int, casualties: Casualty, token: String) {
        viewModelScope.launch {
            try {
                val response = Repository.postCasualties(id, casualties, "Bearer $token")
                postCasualtiesResponse.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getCasualties(id: Int, token: String) {
        viewModelScope.launch {
            try {
                val response = Repository.getCasualties(id, "Bearer $token")
                casualtiesResponse.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun putCasualty(id: Int,victimInfoId: Int, casualty: Casualty, token: String){
        viewModelScope.launch {
            try {
                val response = Repository.putCasualty(id,victimInfoId,casualty, "Bearer $token")
                putCasualtyResponse.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}