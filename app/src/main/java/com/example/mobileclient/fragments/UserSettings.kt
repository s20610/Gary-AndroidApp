package com.example.mobileclient.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mobileclient.R
import com.example.mobileclient.databinding.FragmentIncidentBinding
import com.example.mobileclient.databinding.FragmentUserSettingsBinding


class UserSettings : Fragment() {
    private var _binding: FragmentUserSettingsBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val sharedPref = activity!!.getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        _binding = FragmentUserSettingsBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.switch1.isChecked = sharedPref.getBoolean("notificationsON", false)
        binding.switch1.setOnClickListener {
            if(binding.switch1.isChecked){
                editor.putBoolean("notificationsON", true)
            }
            else{
                editor.putBoolean("notificationsON", false)
            }
            editor.apply()
        }
        binding.switch2.isChecked = sharedPref.getBoolean("createIncidentON", false)
        binding.switch2.setOnClickListener {
            if(binding.switch2.isChecked){
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