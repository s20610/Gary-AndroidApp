package com.example.mobileclient.viewmodels

import android.media.Rating
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileclient.api.Repository
import com.example.mobileclient.model.Review
import com.example.mobileclient.model.Tutorial
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response
import java.net.ConnectException

class TutorialsViewModel : ViewModel() {
    private var repository: Repository = Repository
    var getTutorialsResponse: MutableLiveData<Response<List<Tutorial>>> = MutableLiveData()
    var addTutorialRatingResponse: MutableLiveData<Response<ResponseBody>> = MutableLiveData()
    var pickedTutorial: Tutorial? = null

    fun getTutorials() {
        viewModelScope.launch {
            try {
                val response = repository.getTutorials()
                getTutorialsResponse.value = response
            } catch (e: ConnectException) {
                Log.d("Connection exception", e.stackTraceToString())
            } catch (e: Exception) {
                Log.d("Exception", e.stackTraceToString())
            }
        }
    }

    fun addTutorialRating(tutorialId: Int, email: String, rating: Review) {
        viewModelScope.launch {
            try {
                val response = repository.addTutorialRating(tutorialId, email, rating)
                addTutorialRatingResponse.value = response
            } catch (e: ConnectException) {
                Log.d("Connection exception", e.stackTraceToString())
            } catch (e: Exception) {
                Log.d("Exception", e.stackTraceToString())
            }
        }
    }

}