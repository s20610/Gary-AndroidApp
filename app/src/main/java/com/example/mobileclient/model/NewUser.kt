package com.example.mobileclient.model

import java.time.LocalDate

data class NewUser(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val birthDate: String,
    val phoneNumber: String,
    val username: String
)
