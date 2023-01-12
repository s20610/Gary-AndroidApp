package com.example.mobileclient.fragments

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.mobileclient.databinding.FragmentParamedicScreenBinding
import com.example.mobileclient.databinding.FragmentParamedicSettingsBinding
import com.example.mobileclient.databinding.FragmentUserSettingsBinding
import java.util.*


class ParamedicSettings : Fragment() {
    private var _binding: FragmentParamedicSettingsBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        _binding = FragmentParamedicSettingsBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.languageSelection.setAdapter(
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                listOf("en", "pl")
            )
        )
        val selectedLanguage: String = sharedPref.getString("language", "")!!
        binding.languageSelection.setOnItemClickListener { adapterView, view, i, l ->
            if (selectedLanguage != binding.languageSelection.text.toString()) {
                if (binding.languageSelection.text.toString() == "en") {
                    editor.putString("language", "en")
                    setAppLocale("en-rUS")
                    editor.apply()
                } else {
                    editor.putString("language", "pl")
                    setAppLocale("pl")
                    editor.apply()
                }
                requireActivity().finish()
                startActivity(requireActivity().intent)
                requireActivity().overridePendingTransition(0, 0)
            }
        }

        return view
    }

    private fun setAppLocale(language: String) {
        val res = resources
        val dm = res.displayMetrics
        val conf = res.configuration
        if (language == "en-rUS") {
            conf.setLocale(Locale(language))
        } else {
            conf.setLocale(Locale(language))
            conf.setLocale(Locale(language.lowercase(Locale.getDefault())))
        }
        res.updateConfiguration(conf, dm)
        onConfigurationChanged(conf)
    }


}