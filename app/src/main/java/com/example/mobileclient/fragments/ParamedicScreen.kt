package com.example.mobileclient.fragments

import android.annotation.SuppressLint
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.mobileclient.R
import com.example.mobileclient.databinding.FragmentParamedicScreenBinding
import com.example.mobileclient.model.UserViewModel
import org.osmdroid.bonuspack.routing.OSRMRoadManager
import org.osmdroid.bonuspack.routing.Road
import org.osmdroid.bonuspack.routing.RoadManager
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.PolyOverlayWithIW
import org.osmdroid.views.overlay.Polyline
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.IMyLocationConsumer
import org.osmdroid.views.overlay.mylocation.IMyLocationProvider
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
    private val sharedViewModel: UserViewModel by activityViewModels()
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

    @SuppressLint("ResourceAsColor")
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
        binding.checkinButton.setOnClickListener{
            if(binding.checkinButton.text == getString(R.string.ParamedicScreen_CheckIn)){
                binding.checkinButton.text = getString(R.string.ParamedicScreen_FinishShift)
            }else {
                binding.checkinButton.text = getString(R.string.ParamedicScreen_CheckIn)
            }
        }


        binding.button3.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_paramedicScreen_to_paramedicCallForSupport2)
        }

        binding.button4.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.addVictimInfo)
        }

        binding.button5.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_paramedicScreen_to_equipment)
        }
        map = binding.map
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.controller.setZoom(15.0)
        val marker: Marker = Marker(map)
        val palacKultury: GeoPoint = GeoPoint(52.231888,21.005967)
        val medicLocation: GeoPoint = GeoPoint(52.22133,21.005923)
        val marker2: Marker = Marker(map)
        marker2.position = medicLocation
        marker2.title = "Medic location"
        marker.position = palacKultury
        marker.title = "Location of incident"
        marker.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_warning_24)
        val roadManager: RoadManager = OSRMRoadManager(context, "garry")
        val gpsProvider: GpsMyLocationProvider = GpsMyLocationProvider(context)
        val waypoints: ArrayList<GeoPoint> = ArrayList()
        waypoints.add(palacKultury)
        waypoints.add(medicLocation)
        val road: Road = roadManager.getRoad(waypoints)
        val color: Int = ContextCompat.getColor(requireContext(),R.color.green_light)
        val roadOverlay: Polyline = RoadManager.buildRoadOverlay(road,color,12f)
//        roadOverlay.outlinePaint.strokeWidth = 10f
        gpsProvider.locationUpdateMinTime = 5000
//        gpsProvider.startLocationProvider { location, _ ->
//            if (waypoints.contains(medicLocation)) {
//                waypoints.remove(medicLocation)
//            }
//            if (location != null) {
//                medicLocation.latitude = location.latitude
//                medicLocation.longitude = location.longitude
//                waypoints.add(medicLocation)
//                road = roadManager.getRoad(waypoints)
//                roadOverlay = RoadManager.buildRoadOverlay(road)
//                Log.d("Road creation", "Road created for $waypoints")
//                map.invalidate()
//            }
//        }
        mLocationOverlay = MyLocationNewOverlay(gpsProvider, binding.map)
        mLocationOverlay!!.enableMyLocation()
        mLocationOverlay!!.enableFollowLocation()
        map.overlayManager.add(marker)
        map.overlayManager.add(marker2)
        map.overlayManager.add(mLocationOverlay)
        map.invalidate()
        map.overlays.add(roadOverlay)
        map.invalidate()





        return view
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