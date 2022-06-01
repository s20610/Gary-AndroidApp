package com.example.mobileclient.fragments

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.mobileclient.R
import com.example.mobileclient.ScanBandCodeActivity
import com.example.mobileclient.databinding.FragmentIncidentBinding
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions





// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Incident.newInstance] factory method to
 * create an instance of this fragment.
 */
class Incident : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentIncidentBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        _binding = FragmentIncidentBinding.inflate(inflater, container, false)

        val incidents = arrayOf("Nieprzytomna osoba", "Zadławienie", "Rana kłuta", "Wypadek samochodowy", "Inne")
        val arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, incidents)
        binding.autoCompleteTextView2.setAdapter(arrayAdapter)

        val view = binding.root

        binding.button2.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_incident_to_loggedInScreen)
        }
        binding.button.setOnClickListener {
            val builder = NotificationCompat.Builder(requireContext(), "Notifications")
                .setSmallIcon(R.drawable.ic_baseline_medical_services_24)
                .setContentTitle("New incident reported")
                .setContentText("First responders are on their way")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT).setAutoCancel(true)
            val notificationId = 1
            with(NotificationManagerCompat.from(requireContext())) {
                // notificationId is a unique int for each notification that you must define
                notify(notificationId, builder.build())
            }
        }
        val incidentLocationPicker = IncidentLocationPicker.newInstance()
        binding.locationInput.setOnClickListener {
           incidentLocationPicker.show(childFragmentManager,"incident_location_picker")
        }
        childFragmentManager.setFragmentResultListener("incidentLocation",this) { _, bundle ->
            val result = bundle.getString("bundleKey")
            binding.locationInputText.setText(result)
        }


        val barcodeLauncher = registerForActivityResult(
            ScanContract()
        ) { result: ScanIntentResult ->
            if (result.contents == null) {
                Toast.makeText(context, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(
                    context,
                    "Scanned band code: " + result.contents,
                    Toast.LENGTH_LONG
                ).show()
                binding.barcodeInputText.setText(result.contents)
            }
        }
        val options = ScanOptions()
        options.setPrompt(resources.getString(R.string.scan_prompt))
        options.setCameraId(0)
        options.setBeepEnabled(true)
        options.captureActivity = ScanBandCodeActivity::class.java
        options.setOrientationLocked(false)
        options.setBarcodeImageEnabled(true)
        binding.scanButton.setOnClickListener {
            barcodeLauncher.launch(options)
        }
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Incident.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Incident().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}