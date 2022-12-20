package com.example.mobileclient.util

import com.example.mobileclient.model.Tutorial
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class Constants {
    companion object {
        const val LOCAL = "http://10.0.2.2:8080"
        const val PJATK = "http://172.21.40.111:8080"
        val gson: Gson = GsonBuilder().setLenient().create()
        const val USER_INFO_PREFS = "userInfo"
        const val USER_EMAIL_TO_PREFS = "email"
        const val USER_TOKEN_TO_PREFS = "token"
        const val USER_ROLE_TO_PREFS = "role"
        val tutorialsEmpty: List<Tutorial> = mutableListOf(
            Tutorial(
                "1",
                "Tutorial 1",
                "https://miro.medium.com/max/480/1*QiE4-0MPslYPvx2Fit1NIQ.jpeg",
                "COURSE",
                0.2f,
                ""
            ),
            Tutorial(
                "2",
                "Tutorial 2",
                "https://miro.medium.com/max/480/1*QiE4-0MPslYPvx2Fit1NIQ.jpeg",
                "FILE_EMERGENCE",
                0.5f,
                ""
            ),
            Tutorial(
                "3",
                "Tutorial 3",
                "https://miro.medium.com/max/480/1*QiE4-0MPslYPvx2Fit1NIQ.jpeg",
                "GUIDE",
                0.2f,
                ""
            ),
            Tutorial(
                "4",
                "Tutorial 4",
                "https://miro.medium.com/max/480/1*QiE4-0MPslYPvx2Fit1NIQ.jpeg",
                "FILE_EMERGENCE",
                0.7f,
                ""
            ),
            Tutorial(
                "5",
                "Tutorial 5",
                "https://miro.medium.com/max/480/1*QiE4-0MPslYPvx2Fit1NIQ.jpeg",
                "COURSE",
                0.25f,
                ""
            ),
        )
    }
}