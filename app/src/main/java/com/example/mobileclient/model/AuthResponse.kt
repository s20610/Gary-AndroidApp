package com.example.mobileclient.model

data class AuthResponse(
    val token: String,
    val email: String,
    val roles: List<String>,
)