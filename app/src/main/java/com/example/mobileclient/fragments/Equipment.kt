package com.example.mobileclient.fragments

import android.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.Navigation
import com.example.mobileclient.databinding.FragmentEquipmentBinding

class Equipment : Fragment() {
    private var _binding: FragmentEquipmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentEquipmentBinding.inflate(inflater, container, false)
        val view = binding.root

        val names = arrayOf(
            "Karetka1 specialistyczna WE1234",
            "Karetka2 covid WE54321",
            "Karetka3 specialistyczna WE1434"
        )

        val arrayAdapter =
            ArrayAdapter(requireContext(), R.layout.simple_list_item_1, names)
        binding.ambulanceList.adapter = arrayAdapter

        binding.ambulanceList.setOnItemClickListener { adapterView, view, i, l ->
            Navigation.findNavController(view)
                .navigate(com.example.mobileclient.R.id.action_equipment_to_checkEquipment)

        }

        return view
    }
}