package com.example.mobileclient.model

data class Casualty(
    val firstName: String,
    var gender: Gender,
    val lastName: String,
    var status: VictimStatus,
    val victimInfoId: Int?
)

enum class VictimStatus {
    STABLE, UNSTABLE, DECEASED
}
enum class Gender{
    MALE, FEMALE, OTHER
}
