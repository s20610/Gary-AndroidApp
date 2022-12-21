package com.example.mobileclient.model

import com.google.gson.annotations.SerializedName

data class MONDAY(

    @SerializedName("start") var start: String?, @SerializedName("end") var end: String?

)

data class TUESDAY(

    @SerializedName("start") var start: String?, @SerializedName("end") var end: String?

)

data class WEDNESDAY(

    @SerializedName("start") var start: String?, @SerializedName("end") var end: String?

)

data class THURSDAY(

    @SerializedName("start") var start: String?, @SerializedName("end") var end: String?

)

data class FRIDAY(

    @SerializedName("start") var start: String?, @SerializedName("end") var end: String?

)