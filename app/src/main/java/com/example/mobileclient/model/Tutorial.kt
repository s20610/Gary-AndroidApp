package com.example.mobileclient.model

import com.google.gson.annotations.SerializedName

data class Tutorial(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("tutorialKind")
    val tutorialKind: String,
    @SerializedName("average")
    val average: Float
)
