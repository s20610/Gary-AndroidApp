package com.example.mobileclient.model

data class User(
    val id: String,
    val firstname: String,
    val lastname: String,
    val birthDate: String,
    val phone: String,
    val bandCode: String,
    val medicalInfo: MedicalInfo
)
