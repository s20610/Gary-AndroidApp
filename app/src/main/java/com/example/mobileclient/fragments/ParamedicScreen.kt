package com.example.mobileclient.fragments

import android.content.Context
import android.content.SharedPreferences
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.mobileclient.R
import com.example.mobileclient.databinding.ActivityParamedicBinding
import com.example.mobileclient.databinding.FragmentParamedicScreenBinding
import com.example.mobileclient.model.Schedule
import com.example.mobileclient.util.Constants.Companion.USER_INFO_PREFS
import com.example.mobileclient.util.Constants.Companion.USER_TOKEN_TO_PREFS
import com.example.mobileclient.util.findNextShift
import com.example.mobileclient.viewmodels.ParamedicViewModel
import kotlinx.coroutines.*
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
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class ParamedicScreen : Fragment() {
    private var _binding: FragmentParamedicScreenBinding? = null
    private var mLocationOverlay: MyLocationNewOverlay? = null
    private val paramedicViewModel: ParamedicViewModel by activityViewModels()
    private lateinit var map: MapView
    private val binding get() = _binding!!
    private var ambulance: String = "AAA000"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentParamedicScreenBinding.inflate(inflater, container, false)
        val view = binding.root
        map = binding.map
        binding.shiftButton.visibility = View.GONE
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val formatted = current.format(formatter)
        binding.dayField.text = formatted
        //get token from shared preferences
        val token = requireActivity().getSharedPreferences(USER_INFO_PREFS, Context.MODE_PRIVATE)
            .getString(USER_TOKEN_TO_PREFS, "")
        paramedicViewModel.getCurrentAmbulance(token ?: "")

        paramedicViewModel.currentAmbulanceResponse.observe(viewLifecycleOwner) {
            if (it.isSuccessful) {
                val licensePlate = it.body()?.licensePlate
                if (licensePlate != null) {
                    ambulance = licensePlate
                    setupMap()
                    paramedicViewModel.getAmbulanceEquipment(ambulance, token?: "")
                    paramedicViewModel.getAssignedIncident(ambulance)
                }
            }
        }
        paramedicViewModel.getSchedule(token ?: "")
        paramedicViewModel.scheduleResponse.observe(viewLifecycleOwner) { response ->
            if (response.isSuccessful) {
                Log.d("Schedule", response.body()!!.schedule.toString())
                if (response.body()?.schedule != null) {
                    val scheduleForToday = findNextShift(response.body()!!.schedule!!)
                    val textToDisplay =
                        "Start - ${scheduleForToday[0]} End - ${scheduleForToday[1]}"
                    binding.nearestShiftField.text = textToDisplay
                }
            }
        }


        binding.checkinButton.setOnClickListener {
            when (binding.checkinButton.text) {
                getString(R.string.ParamedicScreen_CheckIn) -> {
                    paramedicViewModel.startEmployeeShift(token ?: "")
                    paramedicViewModel.employeeShiftResponse.observe(viewLifecycleOwner) { response ->
                        if (response.isSuccessful) {
                            binding.checkinButton.text =
                                getString(R.string.ParamedicScreen_FinishShift)
                            binding.checkinButton.setBackgroundColor(
                                ContextCompat.getColor(
                                    requireContext(), R.color.red
                                )
                            )
                        }
                    }
                    binding.checkinButton.text = getString(R.string.ParamedicScreen_FinishShift)
                    binding.checkinButton.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(), R.color.red
                        )
                    )
                    binding.shiftButton.visibility = View.VISIBLE
                    binding.cardView.visibility = View.GONE
                }
                else -> {
                    paramedicViewModel.endEmployeeShift(token ?: "")
                    //Add message box to confirm end of shift
                    paramedicViewModel.employeeShiftResponse.observe(viewLifecycleOwner) { response ->
                        if (response.isSuccessful) {
                            binding.checkinButton.text = getString(R.string.ParamedicScreen_CheckIn)
                            binding.checkinButton.setBackgroundColor(
                                ContextCompat.getColor(
                                    requireContext(), R.color.green_dark
                                )
                            )
                        }
                    }
                    binding.checkinButton.text = getString(R.string.ParamedicScreen_CheckIn)
                    binding.checkinButton.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(), R.color.green_dark
                        )
                    )
                    binding.shiftButton.visibility = View.VISIBLE
                    binding.cardView.visibility = View.GONE
                }
            }
        }

        binding.shiftButton.setOnClickListener {
            binding.cardView.visibility = View.VISIBLE
            it.visibility = View.GONE
        }
        val navController: NavController =
            Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
        binding.bottomNavigation.setOnItemSelectedListener {
            it.isChecked = true
            when (it.toString()) {
                resources.getString(R.string.menu_equipment) -> {
                    it.isChecked = true
                    navController.navigate(R.id.checkEquipment)
                }
                resources.getString(R.string.menu_victim) -> {
                    it.isChecked = true
                    navController.navigate(R.id.addVictimInfo)
                }
                resources.getString(R.string.menu_support) -> {
                    it.isChecked = true
                    navController.navigate(R.id.paramedicCallForSupport2)
                }
                else -> {
                    it.isChecked = true
                    navController.navigate(R.id.paramedicScreen)
                }
            }
            true
        }
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
        gpsProvider.locationUpdateMinTime = 15000
        val waypoints: ArrayList<GeoPoint> = ArrayList()
        val color: Int = ContextCompat.getColor(requireContext(), R.color.green_light)
        mLocationOverlay = MyLocationNewOverlay(gpsProvider, map)
        mLocationOverlay!!.enableMyLocation()
        mLocationOverlay!!.enableFollowLocation()
        map.overlayManager.add(mLocationOverlay)
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
                        delay(5000)
                        val currentLocation = com.example.mobileclient.model.Location(
                            location.latitude, location.longitude
                        )
                        paramedicViewModel.updateAmbulanceLocation(ambulance, currentLocation)
                        Log.d(
                            "Location update",
                            paramedicViewModel.updateAmbulanceInfoResponse.value.toString()
                        )
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