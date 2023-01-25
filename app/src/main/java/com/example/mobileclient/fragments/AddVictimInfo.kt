package com.example.mobileclient.fragments

import android.R.layout.simple_dropdown_item_1line as simple_dropdown_item_1line1
import com.example.mobileclient.R as R1
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.mobileclient.databinding.FragmentAddVictimInfoBinding
import com.example.mobileclient.model.Casualty
import com.example.mobileclient.model.Gender
import com.example.mobileclient.util.Constants
import com.example.mobileclient.util.setGenderTypeToApi
import com.example.mobileclient.util.setVictimStatusTypeToApi
import com.example.mobileclient.viewmodels.ParamedicViewModel

class AddVictimInfo : Fragment() {
    private var _binding: FragmentAddVictimInfoBinding? = null
    private val paramedicViewModel: ParamedicViewModel by activityViewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddVictimInfoBinding.inflate(inflater, container, false)
        var incidentId = 0
        val genderTypes: Array<String> =
            requireContext().resources.getStringArray(com.example.mobileclient.R.array.genderTypes)
        val victimStatusTypes: Array<String> =
            requireContext().resources.getStringArray(com.example.mobileclient.R.array.victimStatusTypes)
        val token = requireActivity().getSharedPreferences(Constants.USER_INFO_PREFS, Context.MODE_PRIVATE)
            .getString(Constants.USER_TOKEN_TO_PREFS, "")
        val arrayAdapter1 =
            ArrayAdapter(requireContext(), simple_dropdown_item_1line1, genderTypes)
        val arrayAdapter2 =
            ArrayAdapter(requireContext(), simple_dropdown_item_1line1, victimStatusTypes)

        binding.genderPickerText.setAdapter(arrayAdapter1)
        binding.autoCompleteTextView.setAdapter(arrayAdapter2)

        paramedicViewModel.assignedIncidentResponse.observe(viewLifecycleOwner) {response ->
            if (response.code() == 200) {
                incidentId = response.body()?.incidentId!!
            }
        }

        binding.button.setOnClickListener {
            if (incidentId != 0 && incidentId != null){
                val casualties: ArrayList<Casualty> = ArrayList()
                val casualty = Casualty(
                    binding.firstName?.text.toString(),
                    setGenderTypeToApi(binding.genderPickerText.text.toString(), genderTypes),
                    binding.lastName?.text.toString(),
                    setVictimStatusTypeToApi(binding.autoCompleteTextView.text.toString(), victimStatusTypes)
                )
                casualties.add(casualty)
                if (token != null) {
                    paramedicViewModel.postCasualties(incidentId,casualty,token)
                    paramedicViewModel.postCasualtiesResponse.observe(viewLifecycleOwner) { response ->
                        if (response.code() == 200) {
                            Log.d("AddVictimInfo", "Casualty added")
                            Toast.makeText(requireContext(), "OK", Toast.LENGTH_SHORT).show()
                        }else{
                            Log.d("AddVictimInfo", "Casualty not added")
                            Toast.makeText(requireContext(), getString(R1.string.error_adding_info), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        val view = binding.root

        binding.button2.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R1.id.paramedicScreen)
        }

        return view
    }
}