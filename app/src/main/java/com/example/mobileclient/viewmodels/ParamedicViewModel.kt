package com.example.mobileclient.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileclient.api.Repository
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response

class ParamedicViewModel: ViewModel() {
    val employeeShiftResponse: MutableLiveData<Response<ResponseBody>> = MutableLiveData()

    fun startEmployeeShift() {
        viewModelScope.launch {
            try {
                val response = Repository.startEmployeeShift()
                employeeShiftResponse.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun endEmployeeShift() {
        viewModelScope.launch {
            try {
                val response = Repository.endEmployeeShift()
                employeeShiftResponse.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}