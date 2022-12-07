package com.example.mobileclient.activities

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.mobileclient.R
import com.example.mobileclient.util.Constants.Companion.USER_INFO_PREFS
import org.osmdroid.config.Configuration

class LandingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createNotificationChannel()
        Configuration.getInstance().userAgentValue = "Garry"
        Log.d("Notification channel", "Created notification channel")
        val sharedPreferences = getSharedPreferences(USER_INFO_PREFS, Context.MODE_PRIVATE)
        if(sharedPreferences.contains("token") && sharedPreferences.contains("role")) {
            val role = sharedPreferences.getString("role", "")
            if (role == "user") {
                val intent = Intent(this, UserActivity::class.java)
                startActivity(intent)
                finish()
            } else if (role == "paramedic") {
                val intent = Intent(this, ParamedicActivity::class.java)
                startActivity(intent)
                finish()
            }
        } else {
            setContentView(R.layout.activity_landing)
        }
        Log.d("Landing", "Content view set to landing")
    }

    private fun createNotificationChannel() {
        Configuration.getInstance().userAgentValue = "Garry"
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notifications"
            val descriptionText = "Channel for app notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("Notifications", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onPause() {
        super.onPause()
        val sharedPref = this.getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("watchedOnboarding", true)
        editor.apply()
    }

    override fun onResume() {
        super.onResume()
        val sharedPref = this.getPreferences(Context.MODE_PRIVATE)
        val watchedOnboarding = sharedPref.getBoolean("watchedOnboarding", false)
        Log.d("watchedOnboarding", watchedOnboarding.toString())
        if (!watchedOnboarding) {
            val onboardingActivity = Intent(applicationContext, OnboardingActivity::class.java)
            startActivity(onboardingActivity)
        }
    }
}