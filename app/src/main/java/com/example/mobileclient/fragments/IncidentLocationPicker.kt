package com.example.mobileclient.fragments

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.example.mobileclient.R
import com.example.mobileclient.databinding.FragmentIncidentLocationPickerBinding
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import java.util.*

class IncidentLocationPicker : DialogFragment() {
    private var _binding: FragmentIncidentLocationPickerBinding? = null
    private val binding get() = _binding!!
    private var mLocationOverlay: MyLocationNewOverlay? = null
    private lateinit var map: MapView
    private var pointOnMap = GeoPoint(10.0, 10.0)
    private lateinit var marker: Marker

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIncidentLocationPickerBinding.inflate(inflater, container, false)
        val view = binding.root
        map = binding.map
        var address: List<Address>
        val geocoder = Geocoder(requireContext(), Locale("pol"))
        val mReceive: MapEventsReceiver = object : MapEventsReceiver {
            override fun singleTapConfirmedHelper(p: GeoPoint?): Boolean {
                marker.position = p
                address =
                    geocoder.getFromLocation(marker.position.latitude, marker.position.longitude, 1)!!
                var resultAddress: String = ""
                for (i in 0..address[0].maxAddressLineIndex) {
                    resultAddress += address[0].getAddressLine(i)
                }
                binding.coordinates.text = resultAddress
                return false
            }

            override fun longPressHelper(p: GeoPoint?): Boolean {
                return false
            }
        }
        mLocationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(context), binding.map)
        mLocationOverlay!!.enableMyLocation()
        mLocationOverlay!!.enableFollowLocation()
        mLocationOverlay!!.isOptionsMenuEnabled = true
        map.controller.setCenter(GeoPoint(52.237049, 21.017532))
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.controller.setZoom(18.0)
        map.overlayManager.add(mLocationOverlay)
        map.overlayManager.add(MapEventsOverlay(mReceive))
        marker = Marker(map)
        marker.position = pointOnMap
        marker.isDraggable = true
        marker.title = "Location of incident"
        marker.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_warning_24)
        map.overlayManager.add(marker)
        binding.myLocationButton.setOnClickListener {
            map.controller.animateTo(mLocationOverlay!!.myLocation)
        }
        binding.dialogAccept.setOnClickListener {
            val result = marker.position.toString()
            setFragmentResult("incidentLocation", bundleOf("bundleKey" to result))
            dismiss()
        }
        return view
    }

    override fun onPause() {
        mLocationOverlay!!.disableMyLocation()
        super.onPause()
    }

    override fun onResume() {
        mLocationOverlay!!.enableMyLocation()
        super.onResume()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mLocationOverlay!!.runOnFirstFix {
            marker.position = GeoPoint(
                mLocationOverlay!!.myLocation.latitude + 0.00015,
                mLocationOverlay!!.myLocation.longitude + 0.00015
            )
            map.invalidate()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment IncidentLocationPicker.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            IncidentLocationPicker().apply {
                arguments = Bundle().apply {

                }
            }
    }
}