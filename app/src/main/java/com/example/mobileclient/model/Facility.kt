package com.example.mobileclient.model

import android.location.Address

data class Facility(
    val id: Int,
    val name: String,
    val location: Location,
    val address: String,
    val type: String
)