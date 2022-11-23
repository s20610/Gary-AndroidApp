package com.example.mobileclient.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.mobileclient.R
import com.example.mobileclient.activities.ScanBandCodeActivity
import com.example.mobileclient.databinding.FragmentIncidentBinding
import com.example.mobileclient.model.AccidentReport
import com.example.mobileclient.viewmodels.AccidentReportViewModel
import com.example.mobileclient.viewmodels.TypesViewModel
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import org.osmdroid.util.GeoPoint


class Incident : Fragment() {
    private var _binding: FragmentIncidentBinding? = null

    private val accidentReportViewModel: AccidentReportViewModel by activityViewModels()
    private val typesViewModel: TypesViewModel by activityViewModels()


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        _binding = FragmentIncidentBinding.inflate(inflater, container, false)

        typesViewModel.getEmergencyTypes()
        typesViewModel.emergencyTypes.observe(viewLifecycleOwner) {
            val arrayAdapter =
                ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, it)
            binding.autoCompleteTextView2.setAdapter(arrayAdapter)
        }

        val view = binding.root
        var pickedLocation = GeoPoint(0.0, 0.0)
        binding.button2.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_incident_to_loggedInScreen)
        }
        binding.button.setOnClickListener {
            if (validateForm()) {
                val userEmail: String =
                    requireActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE)
                        .getString("email", "")!!
                val victimCount: Int = binding.victimsEdit?.text.toString().toInt()
                val barcode: String = binding.barcodeInputText.text.toString()
                val accidentType: String = binding.autoCompleteTextView2.text.toString()
                val isConscious = binding.consciousYes?.isChecked == true
                val isBreathing = binding.breathingYes?.isChecked == true
                val accidentReport = AccidentReport(
                    date = "",
                    userEmail,
                    barcode,
                    accidentType,
                    victimCount,
                    pickedLocation.longitude,
                    pickedLocation.latitude,
                    isConscious,
                    isBreathing,
                    description = ""
                )
                accidentReportViewModel.postAccidentReport(accidentReport)
                accidentReportViewModel.postCallResponseBody.observe(viewLifecycleOwner) {
                    if (it.isSuccessful) {
                        Toast.makeText(
                            requireContext(),
                            R.string.addedAccidentReport,
                            Toast.LENGTH_SHORT
                        ).show()
                        Navigation.findNavController(view)
                            .navigate(R.id.action_incident_to_loggedInScreen)
                    } else {
                        Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        val incidentLocationPicker = IncidentLocationPicker.newInstance()
        binding.openMapButton!!.setOnClickListener {
            incidentLocationPicker.show(childFragmentManager, "incident_location_picker")
        }
        childFragmentManager.setFragmentResultListener("incidentLocation", this) { _, bundle ->
            pickedLocation = bundle.getSerializable("bundleKey") as GeoPoint
            binding.locationInputText.setText(pickedLocation.toString())
        }
        val barcodeLauncher = registerForActivityResult(
            ScanContract()
        ) { result: ScanIntentResult ->
            if (result.contents == null) {
                Toast.makeText(
                    context,
                    resources.getString(R.string.barcode_scanning_cancelled),
                    Toast.LENGTH_LONG
                )
                    .show()
            } else {
                Toast.makeText(
                    context,
                    "${resources.getString(R.string.barcode_scanning_successful)} ${result.contents}",
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

    private fun validateForm(): Boolean {
        var valid = true

        val victimCount = binding.victimsEdit?.text.toString()
        if (victimCount.isEmpty()) {
            binding.victimsEdit?.error = "Required."
            valid = false
        } else {
            binding.victimsEdit?.error = null
        }

        val location = binding.locationInputText.text.toString()
        if (location.isEmpty()) {
            binding.locationInputText.error = "Required."
            valid = false
        } else {
            binding.locationInputText.error = null
        }

        val accidentType = binding.autoCompleteTextView2.text.toString()
        if (accidentType.isEmpty()) {
            binding.autoCompleteTextView2.error = "Required."
            valid = false
        } else {
            binding.autoCompleteTextView2.error = null
        }

        return valid
    }

}