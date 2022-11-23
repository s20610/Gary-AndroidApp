package com.example.mobileclient.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.mobileclient.R
import com.example.mobileclient.databinding.FragmentIncidentsBrowseBinding
import com.example.mobileclient.databinding.FragmentShowIncidentBinding
import com.example.mobileclient.viewmodels.AccidentReportViewModel

class ShowIncident : Fragment() {
    private var _binding: FragmentShowIncidentBinding? = null
    private val binding get() = _binding!!
    private val accidentReportViewModel: AccidentReportViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShowIncidentBinding.inflate(inflater, container, false)
        val view = binding.root
        val pickedAccidentReport = accidentReportViewModel.pickedAccidentReport
        binding.autoCompleteTextView2.setText(pickedAccidentReport?.emergencyType)
        binding.autoCompleteTextView2.isEnabled = false
        binding.victimsEdit.setText(pickedAccidentReport?.victimCount.toString())
        binding.victimsEdit.isEnabled = false
        val locationString =
            pickedAccidentReport?.latitude.toString() + ", " + pickedAccidentReport?.longitude.toString()
        binding.locationInputText.setText(locationString)
        binding.locationInputText.isEnabled = false
        if (pickedAccidentReport?.conscious == true) {
            binding.consciousYes.isChecked = true
        } else {
            binding.consciousNo.isChecked = true
        }
        if (pickedAccidentReport?.breathing == true) {
            binding.breathingYes.isChecked = true
        } else {
            binding.breathingNo.isChecked = true
        }
        binding.consciousGroup.isEnabled = false
        binding.breathingGroup.isEnabled = false
        binding.barcodeInputText.setText(pickedAccidentReport?.bandCode)
        binding.barcodeInputText.isEnabled = false


        return view
    }
}