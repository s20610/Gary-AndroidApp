package com.example.mobileclient.util

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.mobileclient.model.Gender
import com.example.mobileclient.model.Schedule
import com.example.mobileclient.model.VictimStatus
import java.time.LocalDate

fun checkPermission(permission: String, requestCode: Int, context: Context, activity: Activity) {
    if (ContextCompat.checkSelfPermission(
            context, permission
        ) == PackageManager.PERMISSION_DENIED
    ) {
        ActivityCompat.requestPermissions(activity, arrayOf(permission), requestCode)
    } else {
        Log.d("Permission", "$permission already granted")
    }
}

fun hasPermissions(context: Context, vararg permissions: String): Boolean = permissions.all {
    ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
}

fun setAllergyTypeFromApi(allergyType: String, allergyTypesArray: Array<String>): String {
    return when (allergyType) {
        "SKIN_CONTACT" -> allergyTypesArray[0]
        "INGESTION" -> allergyTypesArray[1]
        "INJECTION" -> allergyTypesArray[2]
        "INHALATION" -> allergyTypesArray[3]
        else -> {
            ""
        }
    }
}

fun setAllergyTypeToApi(allergyType: String, allergyTypesArray: Array<String>): String {
    return when (allergyType) {
        allergyTypesArray[0] -> "SKIN_CONTACT"
        allergyTypesArray[1] -> "INGESTION"
        allergyTypesArray[2] -> "INJECTION"
        allergyTypesArray[3] -> "INHALATION"
        else -> {
            ""
        }
    }
}

fun setIncidentTypeFromApi(incidentType: String, incidentTypesArray: Array<String>): String {
    return when (incidentType) {
        "CAR_ACCIDENT" -> incidentTypesArray[0]
        "FLOOD" -> incidentTypesArray[1]
        "FIRE" -> incidentTypesArray[2]
        "UNKNOWN" -> incidentTypesArray[3]
        "HEART_ATTACK" -> incidentTypesArray[4]
        "SUICIDE" -> incidentTypesArray[5]
        "COVID" -> incidentTypesArray[6]
        else -> {
            ""
        }
    }
}
fun setIncidentTypeToApi(incidentType: String, incidentTypesArray: Array<String>): String {
    return when (incidentType) {
        incidentTypesArray[0] -> "CAR_ACCIDENT"
        incidentTypesArray[1] -> "FLOOD"
        incidentTypesArray[2] -> "FIRE"
        incidentTypesArray[3] -> "UNKNOWN"
        incidentTypesArray[4] -> "HEART_ATTACK"
        incidentTypesArray[5] -> "SUICIDE"
        incidentTypesArray[6] -> "COVID"
        else -> {
            ""
        }
    }
}

fun setGenderTypeFromApi(genderType: String, genderTypesArray: Array<String>): String {
    return when (genderType) {
        Gender.MALE.toString() -> genderTypesArray[0]
        Gender.FEMALE.toString() -> genderTypesArray[1]
        Gender.OTHER.toString() -> genderTypesArray[2]
        else -> {
            ""
        }
    }
}

fun setGenderTypeToApi(genderType: String, genderTypesArray: Array<String>): Gender {
    return when (genderType) {
        genderTypesArray[0] -> Gender.MALE
        genderTypesArray[1] -> Gender.FEMALE
        genderTypesArray[2] -> Gender.OTHER
        else -> {
            Gender.OTHER
        }
    }
}

fun setVictimStatusTypeFromApi(victimStatusType: String, victimStatusTypesArray: Array<String>): String {
    return when (victimStatusType) {
        VictimStatus.STABLE.toString() -> victimStatusTypesArray[0]
        VictimStatus.UNSTABLE.toString() -> victimStatusTypesArray[1]
        VictimStatus.DECEASED.toString() -> victimStatusTypesArray[2]
        else -> {
            ""
        }
    }
}

fun setVictimStatusTypeToApi(victimStatusType: String, victimStatusTypesArray: Array<String>): VictimStatus {
    return when (victimStatusType) {
        victimStatusTypesArray[0] -> VictimStatus.STABLE
        victimStatusTypesArray[1] -> VictimStatus.UNSTABLE
        victimStatusTypesArray[2] -> VictimStatus.DECEASED
        else -> {
            VictimStatus.STABLE
        }
    }
}

fun findNextShift(schedule: Schedule): List<String> {
    val today = LocalDate.now().dayOfWeek.toString()
    val nearestShift: ArrayList<String> = ArrayList()
    Log.d("simple name", schedule.THURSDAY?.javaClass?.simpleName.toString())
    when {
        schedule.MONDAY?.javaClass?.simpleName.toString().equals(today,true) -> {
            nearestShift.add(schedule.MONDAY!!.start!!)
            nearestShift.add(schedule.MONDAY!!.end!!)
        }
        schedule.TUESDAY?.javaClass?.simpleName.toString().equals(today,true) -> {
            nearestShift.add(schedule.TUESDAY!!.start!!)
            nearestShift.add(schedule.TUESDAY!!.end!!)
        }
        schedule.WEDNESDAY?.javaClass?.simpleName.toString().equals(today,true) -> {
            nearestShift.add(schedule.WEDNESDAY!!.start!!)
            nearestShift.add(schedule.WEDNESDAY!!.end!!)
        }
        schedule.THURSDAY?.javaClass?.simpleName.toString().equals(today,true) -> {
            nearestShift.add(schedule.THURSDAY!!.start!!)
            nearestShift.add(schedule.THURSDAY!!.end!!)
        }
        schedule.FRIDAY?.javaClass?.simpleName.toString().equals(today,true) -> {
            nearestShift.add(schedule.FRIDAY!!.start!!)
            nearestShift.add(schedule.FRIDAY!!.end!!)
        }
        else -> {
            Log.d("Schedule", "No shift today or no matching day")
        }
    }
    Log.d("Schedule", nearestShift.toString())
    return nearestShift.toList()
}

// check internet connection
fun checkIfInternetAvailable(context: Context): Boolean{
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
    return if (networkCapabilities != null){
        if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
            networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) &&
            networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_SUSPENDED)
        ) {
            Log.d("Internet check", "Connected to Internet")
            true
        } else {
            Log.d("Internet check", "Not connected to Internet")
            false
        }
    }else{
        false
    }
}