package com.example.mobileclient.util

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

fun checkPermission(permission: String, requestCode: Int, context: Context, activity: Activity) {
    if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED) {
        ActivityCompat.requestPermissions(activity, arrayOf(permission), requestCode)
    } else {
        Log.d("Permission", "$permission already granted")
    }
}