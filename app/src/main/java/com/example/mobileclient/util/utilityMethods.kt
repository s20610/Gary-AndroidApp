package com.example.mobileclient.util

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

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