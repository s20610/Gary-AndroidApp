package com.example.mobileclient.fragments

import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.mobileclient.databinding.FragmentAmbulanceBreakBinding
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_KEYBOARD
import com.google.android.material.timepicker.TimeFormat

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AmbulanceBreak.newInstance] factory method to
 * create an instance of this fragment.
 */
class AmbulanceBreak : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentAmbulanceBreakBinding? = null
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
        _binding = FragmentAmbulanceBreakBinding.inflate(inflater, container, false)
        val view = binding.root
        val breakTypes = arrayOf("Refuel", "Food break", "5 min break", "Breakdown", "Others")
        val arrayAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, breakTypes)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)
        val calendar = Calendar.getInstance()
        val timePicker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(calendar.get(Calendar.HOUR_OF_DAY))
            .setMinute(calendar.get(Calendar.MINUTE))
            .setInputMode(INPUT_MODE_KEYBOARD)
            .build()
        timePicker.addOnCancelListener {
            timePicker.dismiss()
        }
        val timePicker1 = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(calendar.get(Calendar.HOUR_OF_DAY))
            .setMinute(calendar.get(Calendar.MINUTE))
            .setInputMode(INPUT_MODE_KEYBOARD)
            .build()
        timePicker1.addOnCancelListener {
            timePicker1.dismiss()
        }
        binding.startDateInput
            .setOnClickListener {
                binding.autoCompleteTextView.setAdapter(arrayAdapter)
                timePicker.show(parentFragmentManager, "start_time_picker")
                timePicker.addOnPositiveButtonClickListener {
                    val startTimeInputValue =
                        timePicker.hour.toString() + ":" + timePicker.minute.toString()
                    binding.startDateInput.setText(startTimeInputValue)
                }
            }
        binding.endDateInput.setOnClickListener {
            timePicker1.show(parentFragmentManager, "end_time_picker")
            timePicker1.addOnPositiveButtonClickListener {
                val endTimeInputValue =
                    timePicker1.hour.toString() + ":" + timePicker1.minute.toString()
                binding.endDateInput.setText(endTimeInputValue)
            }
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
         * @return A new instance of fragment AmbulanceBreak.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AmbulanceBreak().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}