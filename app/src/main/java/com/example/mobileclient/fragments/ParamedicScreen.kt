package com.example.mobileclient.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.mobileclient.R
import com.example.mobileclient.databinding.FragmentParamedicScreenBinding
import com.example.mobileclient.viewmodels.ParamedicViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.osmdroid.bonuspack.routing.OSRMRoadManager
import org.osmdroid.bonuspack.routing.Road
import org.osmdroid.bonuspack.routing.RoadManager
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class ParamedicScreen : Fragment() {
    private var _binding: FragmentParamedicScreenBinding? = null
    private var mLocationOverlay: MyLocationNewOverlay? = null
    private val paramedicViewModel: ParamedicViewModel by activityViewModels()
    private lateinit var map: MapView
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentParamedicScreenBinding.inflate(inflater, container, false)
        val view = binding.root
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val formatted = current.format(formatter)
        binding.dayField.text = formatted
        binding.checkinButton.setOnClickListener {
            when (binding.checkinButton.text) {
                getString(R.string.ParamedicScreen_CheckIn) -> {
                    paramedicViewModel.startEmployeeShift()
                    paramedicViewModel.employeeShiftResponse.observe(viewLifecycleOwner) { response ->
                        if (response.isSuccessful) {
                            binding.checkinButton.text =
                                getString(R.string.ParamedicScreen_FinishShift)
                            binding.checkinButton.setBackgroundColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.red
                                )
                            )
                        }
                    }
                }
                else -> {
                    paramedicViewModel.endEmployeeShift()
                    //Add message box to confirm end of shift
                    paramedicViewModel.employeeShiftResponse.observe(viewLifecycleOwner) { response ->
                        if (response.isSuccessful) {
                            binding.checkinButton.text = getString(R.string.ParamedicScreen_CheckIn)
                            binding.checkinButton.setBackgroundColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.green_dark
                                )
                            )
                        }
                    }
                }
            }
        }

        binding.bottomNavigation.setOnItemSelectedListener {
            it.isChecked = true
            if (it.toString() == "Equipment") {
                Navigation.findNavController(view)
                    .navigate(R.id.action_paramedicScreen_to_equipment)
            } else if (it.toString() == "Victim") {
                Navigation.findNavController(view).navigate(R.id.addVictimInfo)
            } else if (it.toString() == "Support") {
                Navigation.findNavController(view)
                    .navigate(R.id.action_paramedicScreen_to_paramedicCallForSupport2)
            }
            true
        }


        map = binding.map
        setupMap()
        return view
    }

    private fun setupMap() {
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.controller.setZoom(15)
        map.setMultiTouchControls(true)
        map.setBuiltInZoomControls(true)
        val (marker: Marker, marker2: Marker, palacKultury: GeoPoint) = markerSetup()
        val roadManager: RoadManager = OSRMRoadManager(context, "Garry")
        val gpsProvider = GpsMyLocationProvider(context)
        gpsProvider.locationUpdateMinTime = 6000
        val waypoints: ArrayList<GeoPoint> = ArrayList()
        val color: Int = ContextCompat.getColor(requireContext(), R.color.green_light)
        mLocationOverlay = MyLocationNewOverlay(gpsProvider, map)
        mLocationOverlay!!.enableMyLocation()
        mLocationOverlay!!.enableFollowLocation()
        mLocationOverlay!!.runOnFirstFix {
            marker2.position = mLocationOverlay!!.myLocation
            marker2.title = "Medic location"
            marker2.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ambulance_icon)
            waypoints.add(palacKultury)
            waypoints.add(marker2.position)
            Log.d("waypoints", waypoints.toString())
            var road: Road
            var roadOverlay = Polyline()
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    road = roadManager.getRoad(waypoints)
                    roadOverlay = RoadManager.buildRoadOverlay(road, color, 12f)
                    map.overlayManager.add(roadOverlay)
                    map.overlayManager.add(marker)
                    map.overlayManager.add(marker2)
                    map.invalidate()
                }
            }
            mLocationOverlay!!.myLocationProvider.startLocationProvider { location, _ ->
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        waypoints.remove(marker2.position)
                        Log.d("waypoints after remove", waypoints.toString())
                        marker2.position = GeoPoint(location.latitude, location.longitude)
                        waypoints.add(marker2.position)
                        Log.d("waypoints after add", waypoints.toString())
                        map.overlayManager.remove(roadOverlay)
                        road = roadManager.getRoad(waypoints)
                        roadOverlay = RoadManager.buildRoadOverlay(road, color, 12f)
                        Log.d("Road creation", "Road created for $waypoints")
                        map.overlayManager.add(roadOverlay)
                        map.invalidate()
                    }
                }
                map.controller.animateTo(marker2.position)
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        map.onDetach()
    }

    override fun onResume() {
        super.onResume()
        map.onResume()
    }

    override fun onPause() {
        super.onPause()
        map.onPause()
    }

    private fun markerSetup(): Triple<Marker, Marker, GeoPoint> {
        val marker = Marker(map)
        val marker2 = Marker(map)
        val palacKultury = GeoPoint(52.231888, 21.005967)
        marker.position = palacKultury
        marker.title = "Location of incident"
        marker.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_warning_24)
        return Triple(marker, marker2, palacKultury)
    }
}