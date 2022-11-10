package com.example.mobileclient.model

data class AccidentReport(
    val email: String,
    val bandCode: String,
    val emergencyType: String,
    val victimCount: Int,
    val longitude: Double,
    val latitude: Double,
    val conscious: Boolean,
    val breathing: Boolean,
){
    override fun toString(): String {
        return emergencyType
    }
}