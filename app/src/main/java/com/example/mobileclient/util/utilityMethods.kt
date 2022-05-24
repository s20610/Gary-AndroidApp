package com.example.mobileclient.util

import java.net.InetAddress

fun isInternetAvailable(): Boolean {
    return try{
        val ipAddr: InetAddress = InetAddress.getByName("google.com")
        !ipAddr.equals("")
    }catch (e: Exception){
        false
    }
}