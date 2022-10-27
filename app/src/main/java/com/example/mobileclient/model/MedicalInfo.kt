package com.example.mobileclient.model

import retrofit2.http.Field

data class MedicalInfo(
    @Field("medicalInfoId")
    val medicalInfoId: Int,
    var rhType: String,
    var bloodType: String,
    @Field("allergies")
    val allergies: List<Allergy>,
    @Field("diseases")
    val diseases: List<Disease>
    )
