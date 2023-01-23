package com.example.mobileclient.viewmodels

import android.content.Context
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileclient.R
import com.example.mobileclient.api.Repository
import com.example.mobileclient.model.CustomMarker
import com.example.mobileclient.model.Facility
import kotlinx.coroutines.launch
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import retrofit2.Response

class FacilitiesViewModel : ViewModel() {
    val facilitiesResponse: MutableLiveData<Response<List<Facility>>> = MutableLiveData()


    fun getFacilities() {
        viewModelScope.launch {
            try {
                val response = Repository.getFacilities()
                facilitiesResponse.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun facilitiesToMarkers(
        facilities: List<Facility>,
        map: MapView,
        context: Context
    ): List<CustomMarker> {
        val markers = mutableListOf<CustomMarker>()
        facilities.forEach { facility ->
            val marker = CustomMarker(map, facility.facilityType)
            marker.position = GeoPoint(facility.location.latitude, facility.location.longitude)
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            marker.title = facility.name
            marker.icon =
                ContextCompat.getDrawable(context, checkTypeOfFacility(facility.facilityType))
            markers.add(marker)
        }
        return markers
    }

    private fun checkTypeOfFacility(facilityType: String): Int {
        Log.d("FacilitiesMap", "Facility type: $facilityType")
        return when (facilityType) {
            "HOSPITAL" -> R.drawable.ic_baseline_local_hospital_40
            "POLICE_STATION" -> R.drawable.ic_baseline_local_police_40
            "FIRE_UNIT" -> R.drawable.ic_baseline_local_fire_department_40
            "GUARD_UNIT" -> R.drawable.ic_placeholder
            "AED" -> R.drawable.ic_heart_flash_24
            else -> R.drawable.ic_placeholder
        }
    }
}