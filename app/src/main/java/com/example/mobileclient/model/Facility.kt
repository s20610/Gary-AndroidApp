package com.example.mobileclient.model

data class Facility(
    val facilityId: Int,
    val name: String,
    val location: Location,
    val facilityType: String
)