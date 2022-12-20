package com.example.mobileclient.model

import com.google.gson.annotations.SerializedName

data class ScheduleDay(
    val day: String
)


data class MONDAY(

    @SerializedName("start") var start: String? = null,
    @SerializedName("end") var end: String? = null

)

data class TUESDAY(

    @SerializedName("start") var start: String? = null,
    @SerializedName("end") var end: String? = null

)

data class WEDNESDAY(

    @SerializedName("start") var start: String? = null,
    @SerializedName("end") var end: String? = null

)

data class THURSDAY(

    @SerializedName("start") var start: String? = null,
    @SerializedName("end") var end: String? = null

)

data class FRIDAY(

    @SerializedName("start") var start: String? = null,
    @SerializedName("end") var end: String? = null

)