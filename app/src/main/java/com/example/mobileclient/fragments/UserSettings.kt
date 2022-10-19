package com.example.mobileclient.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mobileclient.databinding.FragmentUserSettingsBinding


class UserSettings : Fragment() {
    private var _binding: FragmentUserSettingsBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        _binding = FragmentUserSettingsBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.createIncidentSwitch.isChecked = sharedPref.getBoolean("createIncidentON", false)
        binding.createIncidentSwitch.setOnClickListener {
            if(binding.createIncidentSwitch.isChecked){
                editor.putBoolean("createIncidentON", true)
            }
            else{
                editor.putBoolean("createIncidentON", false)
            }
            editor.apply()
        }
//        editor.putString("language","en")
        return view
    }
}