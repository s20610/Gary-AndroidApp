package com.example.mobileclient.model

data class Victim(
    val id: Int,
    val firstname: String,
    val lastname: String,
    val medicalInfo: MedicalInfo,
    val reportSurveys: Set<Emergency>
)
