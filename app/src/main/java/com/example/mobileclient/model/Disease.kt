package com.example.mobileclient.model

data class Disease(var userEmail: String, val diseaseName: String, val description: String, val shareWithBand: Boolean): java.io.Serializable{
    var diseaseId: Int = 0
}