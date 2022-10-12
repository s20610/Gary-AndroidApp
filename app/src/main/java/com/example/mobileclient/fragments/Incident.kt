package com.example.mobileclient.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.mobileclient.R
import com.example.mobileclient.databinding.FragmentIncidentBinding
import com.example.mobileclient.model.Emergency
import com.example.mobileclient.model.Incidentt
import com.example.mobileclient.viewmodels.EmergencyViewModel
import com.example.mobileclient.viewmodels.IncidentViewModel
import com.example.mobileclient.viewmodels.UserViewModel
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions


class Incident : Fragment() {
    private var _binding: FragmentIncidentBinding? = null

    private val incidentViewModel: IncidentViewModel by activityViewModels()


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        _binding = FragmentIncidentBinding.inflate(inflater, container, false)

        val incidents = arrayOf(
            "Nieprzytomna osoba",
            "Zadławienie",
            "Rana kłuta",
            "Wypadek samochodowy",
            "Inne"
        )
        val arrayAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, incidents)
        binding.autoCompleteTextView2.setAdapter(arrayAdapter)

        val view = binding.root

        binding.button2.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_incident_to_loggedInScreen)
        }
        binding.button.setOnClickListener {

            val incident = Incidentt(
                binding.autoCompleteTextView2.text.toString(),
                binding.victimsEdit.toString(),
                binding.locationInputText.text.toString()
            )

            incidentViewModel.createNewIncident(incident)
            incidentViewModel.postCallResponseBody.observe(viewLifecycleOwner){response ->
                if (response.isSuccessful) {
                    Toast.makeText(context, "Update successful", Toast.LENGTH_SHORT).show()
                    Navigation.findNavController(view).navigate(R.id.action_incident_to_incidentVictim)
                } else {
                    Toast.makeText(context, "Update error " + response.code(), Toast.LENGTH_LONG
                    ).show()
                }
            }




        }
        val incidentLocationPicker = IncidentLocationPicker.newInstance()
        binding.openMapButton!!.setOnClickListener {
            incidentLocationPicker.show(childFragmentManager, "incident_location_picker")
        }
        childFragmentManager.setFragmentResultListener("incidentLocation", this) { _, bundle ->
            val result = bundle.getString("bundleKey")
            binding.locationInputText.setText(result)
        }



        return view
    }
}