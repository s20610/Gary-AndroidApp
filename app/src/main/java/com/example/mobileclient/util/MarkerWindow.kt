package com.example.mobileclient.util

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import com.example.mobileclient.R
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.infowindow.InfoWindow

class MarkerWindow(mapView: MapView, private val marker: Marker) : InfoWindow(R.layout.marker_info_window, mapView) {
    override fun onOpen(item: Any?) {
        closeAllInfoWindowsOn(mapView)
        val infoTitle = mView.findViewById<TextView>(R.id.marker_title_text)
        infoTitle.text = marker.title
        val openNavButton = mView.findViewById<Button>(R.id.open_nav_button)
        openNavButton.setOnClickListener {
            val gmmIntentUri =
                Uri.parse("google.navigation:q=${marker.position.latitude},${marker.position.longitude}")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            startActivity(mapView.context, mapIntent, null)
        }
        mView.setOnClickListener {
            close()
        }
    }

    override fun onClose() {
        Log.d("MarkerWindow", "onClose")
    }

}