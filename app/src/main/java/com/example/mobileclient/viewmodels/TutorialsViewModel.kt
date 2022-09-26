package com.example.mobileclient.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileclient.api.Repository
import com.example.mobileclient.model.Tutorial
import kotlinx.coroutines.launch
import retrofit2.Response
import java.net.ConnectException

class TutorialsViewModel: ViewModel() {
    private var repository: Repository = Repository
    var getTutorialsResponse: MutableLiveData<Response<List<Tutorial>>> = MutableLiveData()
    var getTutorialResponse: MutableLiveData<Response<Tutorial>> = MutableLiveData()

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

    fun getTutorial(id:Int){
        viewModelScope.launch {
            try{
                val response = repository.getTutorial(id)
                getTutorialResponse.value = response
            } catch (e: ConnectException) {
                Log.d("Connection exception", e.stackTraceToString())
            }catch (e: Exception){
                Log.d("Exception", e.stackTraceToString())
            }
        }
    }

}