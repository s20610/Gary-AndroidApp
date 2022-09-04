package com.example.mobileclient.fragments

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.os.Looper.loop
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.mobileclient.R
import com.example.mobileclient.databinding.FragmentParamedicScreenBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.internal.wait
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


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [ParamedicScreen.newInstance] factory method to
 * create an instance of this fragment.
 */
class ParamedicScreen : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentParamedicScreenBinding? = null
    private var mLocationOverlay: MyLocationNewOverlay? = null
    private lateinit var map: MapView
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentParamedicScreenBinding.inflate(inflater, container, false)
        val view = binding.root
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val formatted = current.format(formatter)
        binding.dayField.text = formatted
        binding.checkinButton.setOnClickListener {
            when (binding.checkinButton.text) {
                getString(R.string.ParamedicScreen_CheckIn) -> {
                    binding.checkinButton.text = getString(R.string.ParamedicScreen_FinishShift)
                }
                else -> {
                    binding.checkinButton.text = getString(R.string.ParamedicScreen_CheckIn)
                }
            }
        }
        //TODO("Change bottom buttons in phone view")
        binding.bottomNavigation?.setOnItemSelectedListener {
            it.isChecked = true
            if (it.toString() == "Equipment") {
                Navigation.findNavController(view)
                    .navigate(R.id.equipment)
            } else if (it.toString() == "Victim") {
                Navigation.findNavController(view).navigate(R.id.addVictimInfo)
            } else if (it.toString() == "Support") {
                Navigation.findNavController(view)
                    .navigate(R.id.paramedicCallForSupport2)
            }
            true
        }

        map = binding.map
        setupMap(map)
        return view
    }
    private fun setupMap(mapView: MapView) {
        map.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE)
        map.controller.setZoom(15.0)
        val (marker: Marker, marker2: Marker, palacKultury: GeoPoint) = markerSetup()
        val roadManager: RoadManager = OSRMRoadManager(context, "Garry")
        val gpsProvider: GpsMyLocationProvider = GpsMyLocationProvider(context)
        gpsProvider.locationUpdateMinTime = 6000
        val waypoints: ArrayList<GeoPoint> = ArrayList()
        val color: Int = ContextCompat.getColor(requireContext(), R.color.green_light)
        mLocationOverlay = MyLocationNewOverlay(gpsProvider, map)
        mLocationOverlay!!.enableMyLocation()
        mLocationOverlay!!.enableFollowLocation()
        mLocationOverlay!!.runOnFirstFix {
            marker2.position = mLocationOverlay!!.myLocation
            marker2.title = "Medic location"
            waypoints.add(palacKultury)
            waypoints.add(marker2.position)
            var road: Road = Road()
            var roadOverlay: Polyline = Polyline()
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
                        marker2.position = GeoPoint(location.latitude, location.longitude)
                        waypoints.add(marker2.position)
                        map.overlayManager.remove(roadOverlay)
                        road = roadManager.getRoad(waypoints)
                        roadOverlay = RoadManager.buildRoadOverlay(road, color, 12f)
                        Log.d("Road creation", "Road created for $waypoints")
                        map.overlayManager.add(roadOverlay)
                        map.invalidate()
                        map.controller.animateTo(marker2.position)
                    }
                }
            }
        }
    }

    private fun markerSetup(): Triple<Marker, Marker, GeoPoint> {
        val marker: Marker = Marker(map)
        val marker2: Marker = Marker(map)
        val palacKultury: GeoPoint = GeoPoint(52.231888, 21.005967)
        marker.position = palacKultury
        marker.title = "Location of incident"
        marker.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_warning_24)
        return Triple(marker, marker2, palacKultury)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ParamedicScreen.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ParamedicScreen().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}