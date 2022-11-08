package com.example.mobileclient.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mobileclient.R
import com.example.mobileclient.databinding.FragmentUserSettingsBinding
import java.util.*


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

        val languages = arrayOf("Polish", "English")

        val arrayAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, languages)
        binding.languageSelection.setAdapter(arrayAdapter)

        binding.languageSelection.setOnItemClickListener { parent:AdapterView<*>, view:View, position: Int, id: Long ->
            if (position == 0){
                setAppLocale("pl")
                Toast.makeText(context,"jezyk polski", Toast.LENGTH_LONG).show()
            }
            if (position == 1){
                setAppLocale("")
                Toast.makeText(context,"jezyk angielski", Toast.LENGTH_LONG).show()
            }
        }
        return view
    }

    private fun setAppLocale(language: String) {
        val res = resources
        val dm = res.displayMetrics
        val conf = res.configuration
        if (language == ""){
            conf.setLocale(Locale.getDefault())

        }else{
            conf.setLocale(Locale(language))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                conf.setLocale(Locale(language.toLowerCase()));
            } else {
                conf.locale = Locale(language.toLowerCase());
            }

        }
        res.updateConfiguration(conf,dm)
        onConfigurationChanged(conf)

    }


}