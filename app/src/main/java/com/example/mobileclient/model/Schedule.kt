package com.example.mobileclient.model

import com.google.gson.annotations.SerializedName

data class Schedule(
    @SerializedName("MONDAY") var MONDAY: MONDAY?,
    @SerializedName("TUESDAY") var TUESDAY: TUESDAY?,
    @SerializedName("WEDNESDAY") var WEDNESDAY: WEDNESDAY?,
    @SerializedName("THURSDAY") var THURSDAY: THURSDAY?,
    @SerializedName("FRIDAY") var FRIDAY: FRIDAY?
)

data class WholeSchedule(
    @SerializedName("schedule" ) var schedule : Schedule?
)