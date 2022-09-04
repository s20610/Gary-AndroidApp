package com.example.mobileclient.model

import org.osmdroid.util.GeoPoint

data class Emergency (
    val description: String,
    val breathing: Boolean,
    val conscious: Boolean,
    val bloodType: String,
    val medicalBandId: String,
    val location: GeoPoint
        )