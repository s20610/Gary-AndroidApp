package com.example.mobileclient.model

data class MedicalInfo(
    val medicalInfoId: Int,
    var rhType: String,
    var bloodType: String,
    val allergies: List<Allergy>,
    val diseases: List<Disease>
    )
