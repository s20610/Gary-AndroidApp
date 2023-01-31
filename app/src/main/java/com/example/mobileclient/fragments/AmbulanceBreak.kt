package com.example.mobileclient.fragments

import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.mobileclient.R
import com.example.mobileclient.databinding.FragmentAmbulanceBreakBinding
import com.example.mobileclient.viewmodels.ParamedicViewModel
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_KEYBOARD
import com.google.android.material.timepicker.TimeFormat

class AmbulanceBreak : Fragment() {
    private var _binding: FragmentAmbulanceBreakBinding? = null
    private val binding get() = _binding!!
    private val paramedicViewModel: ParamedicViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAmbulanceBreakBinding.inflate(inflater, container, false)
        val view = binding.root
        val topAppBar = requireActivity().findViewById<MaterialToolbar>(R.id.topAppBar)
        if (!paramedicViewModel.isOnBreak) {
            binding.breakButton.text = getString(R.string.startBreak)
            binding.breakButton.setBackgroundColor(resources.getColor(R.color.green_light, null))
            topAppBar.setBackgroundColor(resources.getColor(R.color.green_dark, null))
            topAppBar.title = ""
        } else {
            binding.breakButton.text = getString(R.string.end)
            binding.breakButton.setBackgroundColor(resources.getColor(R.color.red, null))
            topAppBar.setBackgroundColor(resources.getColor(R.color.mustard, null))
            topAppBar.title = getString(R.string.currentlyOnBreak)
        }
        binding.breakButton.setOnClickListener {
            if (!paramedicViewModel.isOnBreak) {
                paramedicViewModel.changeAmbulanceState(
                    paramedicViewModel.currentAmbulanceResponse.value!!.body()!!.licensePlate,
                    "CREW_BRAKE"
                )
                paramedicViewModel.updateAmbulanceInfoResponse.observe(viewLifecycleOwner) { response ->
                    if (response.isSuccessful) {
                        paramedicViewModel.isOnBreak = true
                        binding.breakButton.text = getString(R.string.end)
                        binding.breakButton.setBackgroundColor(
                            resources.getColor(
                                R.color.red,
                                null
                            )
                        )
                        topAppBar.setBackgroundColor(resources.getColor(R.color.mustard, null))
                        topAppBar.title = getString(R.string.currentlyOnBreak)
                    }
                }
            } else {
                paramedicViewModel.changeAmbulanceState(
                    paramedicViewModel.currentAmbulanceResponse.value!!.body()!!.licensePlate,
                    "AVAILABLE"
                )
                paramedicViewModel.updateAmbulanceInfoResponse.observe(viewLifecycleOwner) { response ->
                    if (response.isSuccessful) {
                        paramedicViewModel.isOnBreak = false
                        binding.breakButton.text = getString(R.string.startBreak)
                        binding.breakButton.setBackgroundColor(
                            resources.getColor(
                                R.color.green_light,
                                null
                            )
                        )
                        topAppBar.setBackgroundColor(resources.getColor(R.color.green_dark, null))
                        topAppBar.title = ""
                    }
                }
            }
        }
        binding.cancelButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_ambulanceBreak_to_paramedicScreen)
        }
        return view
    }
}