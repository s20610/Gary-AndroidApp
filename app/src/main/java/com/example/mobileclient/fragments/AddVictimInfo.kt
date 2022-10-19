package com.example.mobileclient.fragments

import android.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.Navigation
import com.example.mobileclient.databinding.FragmentAddVictimInfoBinding

class AddVictimInfo : Fragment() {
    private var _binding: FragmentAddVictimInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddVictimInfoBinding.inflate(inflater, container, false)

        val incidents = arrayOf("Zadławienie", "Rana kłuta", "Wypadek samochodowy", "Inne")
        val victimState = arrayOf("Przytomna", "Nieprzytomna", "Nieoddychająca", "Inne")
        val arrayAdapter1 =
            ArrayAdapter(requireContext(), R.layout.simple_dropdown_item_1line, incidents)
        val arrayAdapter2 =
            ArrayAdapter(requireContext(), R.layout.simple_dropdown_item_1line, victimState)

        binding.autoCompleteTextView.setAdapter(arrayAdapter1)
        binding.autoCompleteTextView3.setAdapter(arrayAdapter2)

        val view = binding.root

        binding.button2.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(com.example.mobileclient.R.id.paramedicScreen)
        }

        return view
    }
}