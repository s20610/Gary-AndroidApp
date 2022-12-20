package com.example.mobileclient.model

import com.google.gson.annotations.SerializedName

data class Schedule(
    @SerializedName("MONDAY") var MONDAY: MONDAY? = MONDAY(),
    @SerializedName("TUESDAY") var TUESDAY: TUESDAY? = TUESDAY(),
    @SerializedName("WEDNESDAY") var WEDNESDAY: WEDNESDAY? = WEDNESDAY(),
    @SerializedName("THURSDAY") var THURSDAY: THURSDAY? = THURSDAY(),
    @SerializedName("FRIDAY") var FRIDAY: FRIDAY? = FRIDAY()
)

data class WholeSchedule(
    @SerializedName("schedule" ) var schedule : Schedule? = Schedule()
)