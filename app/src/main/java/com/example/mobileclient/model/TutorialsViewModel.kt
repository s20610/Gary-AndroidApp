package com.example.mobileclient.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileclient.api.Repository
import kotlinx.coroutines.launch
import retrofit2.Response
import java.net.ConnectException

class TutorialsViewModel: ViewModel() {
    private var repository: Repository = Repository
    var getTutorialsResponse: MutableLiveData<Response<List<Tutorial>>> = MutableLiveData()

    fun getTutorials(){
        viewModelScope.launch {
            try{
                val response = repository.getTutorials()
                getTutorialsResponse.value = response
            } catch (e: ConnectException) {
                Log.d("Connection exception", e.stackTraceToString())
            }catch (e: Exception){
                Log.d("Exception", e.stackTraceToString())
            }
        }
    }

}