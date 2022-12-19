package com.example.mobileclient.model

import com.google.gson.annotations.SerializedName

data class Tutorial(
    @SerializedName("tutorialId")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("tutorialType")
    val tutorialType: String,
    @SerializedName("averageRating")
    val averageRating: Float,
    @SerializedName("tutorialHTML")
    val tutorialHTML: String
)
