package com.example.mobileclient.model

data class UserInfoResponse(
    val id: Int,
    val name: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val birthDate: String,
    val bandcode: String,
)

data class NewUser(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val birthDate: String,
    val phoneNumber: String,
)

data class Credentials(
    val email: String,
    val password: String
)

data class passwordChange(
    val oldPassword: String,
    val newPassword: String
)

data class Email(
    val email: String
)

data class newPassword(
    val newPassword: String
)

data class AuthResponse(
    val token: String,
    val email: String,
    val roles: List<String>,
)