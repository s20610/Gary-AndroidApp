package com.example.mobileclient.model

data class NewUser(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val birthDate: String,
    val phoneNumber: String,
)
