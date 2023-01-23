package com.example.mobileclient.model

import com.example.mobileclient.util.setIncidentTypeFromApi

data class AccidentReport(
    var date: String,
    val email: String,
    val bandCode: String,
    var emergencyType: String,
    val victimCount: Int,
    val longitude: Double,
    val latitude: Double,
    val concious: Boolean,
    val breathing: Boolean,
    val description: String,
){
    private fun formatDate(){
        date = date.substring(0,9)
    }

    override fun toString(): String {
        formatDate()
        return "$emergencyType $date"
    }
}