package com.example.mobileclient.model

data class Allergy (var userEmail: String, val allergyType: String, val allergyName : String, val other: String): java.io.Serializable{
    var allergyId: Int = 0
}