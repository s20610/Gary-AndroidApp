package com.example.mobileclient.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.mobileclient.R
import com.example.mobileclient.databinding.FragmentIncidentBinding


class Incident : Fragment() {
    private var _binding: FragmentIncidentBinding? = null
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
            Navigation.findNavController(view).navigate(R.id.action_incident_to_incidentVictim)
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