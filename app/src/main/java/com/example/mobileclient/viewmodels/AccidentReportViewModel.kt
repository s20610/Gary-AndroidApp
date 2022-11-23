package com.example.mobileclient.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileclient.api.Repository
import com.example.mobileclient.model.AccidentReport
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response

class AccidentReportViewModel : ViewModel() {
    val getAccidentReportResponse: MutableLiveData<Response<AccidentReport>> = MutableLiveData()
    val postCallResponseBody: MutableLiveData<Response<ResponseBody>> = MutableLiveData()
    val getAccidentReportsResponse: MutableLiveData<Response<List<AccidentReport>>> =
        MutableLiveData()
    var userAccidentReportList: List<AccidentReport>? = getAccidentReportsResponse.value?.body()
    var pickedAccidentReport: AccidentReport? = null

    fun getAccidentReport(id: Int) {
        viewModelScope.launch {
            try {
                val response = Repository.getAccidentReport(id)
                getAccidentReportResponse.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getAccidentReports(userEmail: String) {
        viewModelScope.launch {
            try {
                val response = Repository.getAccidentReports(userEmail)
                getAccidentReportsResponse.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun postAccidentReport(accidentReport: AccidentReport) {
        viewModelScope.launch {
            try {
                val response = Repository.postAccidentReport(accidentReport)
                postCallResponseBody.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}