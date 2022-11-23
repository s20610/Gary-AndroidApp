package com.example.mobileclient.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.mobileclient.databinding.FragmentFacilitiesMapBinding
import com.example.mobileclient.model.CustomMarker
import com.example.mobileclient.viewmodels.FacilitiesViewModel
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Overlay
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay


class FacilitiesMap : Fragment() {
    private var _binding: FragmentFacilitiesMapBinding? = null
    private val binding get() = _binding!!
    private var mLocationOverlay: MyLocationNewOverlay? = null
    private val facilitiesViewModel: FacilitiesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFacilitiesMapBinding.inflate(inflater, container, false)
        val view = binding.root
        val map = binding.map
        var markers: ArrayList<CustomMarker> = ArrayList()
        binding.hospitalCheckbox.isChecked = true
        binding.policeCheckbox.isChecked = true
        binding.fireCheckbox.isChecked = true
        facilitiesViewModel.getFacilities()
        facilitiesViewModel.facilitiesResponse.observe(viewLifecycleOwner) { response ->
            Log.d("FacilitiesMap", "Facilities response: ${response.body()}")
            if (response.isSuccessful) {
                val facilities = response.body()!!
                markers = facilitiesViewModel.facilitiesToMarkers(
                    facilities,
                    map,
                    requireContext()
                ) as ArrayList<CustomMarker>
                Log.d("FacilitiesMap", "Markers: $markers")
                markers.forEach {
                    it.setOnMarkerClickListener { _, _ ->
                        val gmmIntentUri =
                            Uri.parse("google.navigation:q=${it.position.latitude},${it.position.longitude}")
                        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                        startActivity(mapIntent)
                        true
                    }
                }
                map.overlayManager.addAll(markers)
            }
        }
        setupMap(map)
        binding.hospitalCheckbox.setOnCheckedChangeListener { _, isChecked ->
            markers.find { it.type == "HOSPITAL" }?.setVisible(isChecked)
        }
        binding.policeCheckbox.setOnCheckedChangeListener { _, isChecked ->
            markers.find { it.type == "POLICE_STATION" }?.setVisible(isChecked)
        }
        binding.fireCheckbox.setOnCheckedChangeListener { _, isChecked ->
            markers.find { it.type == "FIRE_UNIT" }?.setVisible(isChecked)
        }

        return view
    }

    private fun setupMap(map: MapView) {
        mLocationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(context), binding.map)
        var mRotationGestureOverlay = RotationGestureOverlay(context, map)
        mRotationGestureOverlay.isEnabled = true
        map.controller.setCenter(GeoPoint(52.237049, 21.017532))
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.overlayManager.add(mLocationOverlay)
        map.overlayManager.add(mRotationGestureOverlay)
        mLocationOverlay!!.enableMyLocation()
        mLocationOverlay!!.enableFollowLocation()
        mLocationOverlay!!.isOptionsMenuEnabled = true
        map.setBuiltInZoomControls(true)
        map.setMultiTouchControls(true)
        map.controller.setZoom(15)
        map.invalidate()
    }
}