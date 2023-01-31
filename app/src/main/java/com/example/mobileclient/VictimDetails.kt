package com.example.mobileclient

import android.R
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
import com.example.mobileclient.databinding.FragmentVictimDetailsBinding
import com.example.mobileclient.model.Casualty
import com.example.mobileclient.util.Constants
import com.example.mobileclient.util.setGenderTypeFromApi
import com.example.mobileclient.util.setGenderTypeToApi
import com.example.mobileclient.util.setVictimStatusTypeFromApi
import com.example.mobileclient.util.setVictimStatusTypeToApi
import com.example.mobileclient.viewmodels.ParamedicViewModel

class VictimDetails : Fragment() {
    private var casualty: Casualty? = null
    private var _binding: FragmentVictimDetailsBinding? = null
    private val paramedicViewModel: ParamedicViewModel by activityViewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentVictimDetailsBinding.inflate(inflater, container, false)
        val view = binding.root
        val token = requireActivity().getSharedPreferences(
            Constants.USER_INFO_PREFS, Context.MODE_PRIVATE
        ).getString(Constants.USER_TOKEN_TO_PREFS, "")
        val genderTypes: Array<String> =
            requireContext().resources.getStringArray(com.example.mobileclient.R.array.genderTypes)
        val victimStatusTypes: Array<String> =
            requireContext().resources.getStringArray(com.example.mobileclient.R.array.victimStatusTypes)
        val arrayAdapter1 =
            ArrayAdapter(requireContext(), R.layout.simple_dropdown_item_1line, genderTypes)
        val arrayAdapter2 =
            ArrayAdapter(requireContext(), R.layout.simple_dropdown_item_1line, victimStatusTypes)
        binding.genderPickerText.setAdapter(arrayAdapter1)
        binding.autoCompleteTextView.setAdapter(arrayAdapter2)
        casualty = paramedicViewModel.pickedVictimInfo
        var incidentId = 0
        paramedicViewModel.assignedIncidentResponse.observe(viewLifecycleOwner) {response ->
            if (response.code() == 200) {
                incidentId = response.body()?.incidentId!!
            }
        }
        if (casualty != null) {
            binding.autoCompleteTextView.setText(
                setVictimStatusTypeFromApi(
                    casualty!!.status.toString(), victimStatusTypes
                ),false
            )
            binding.firstName.setText(casualty!!.firstName)
            binding.lastName.setText(casualty!!.lastName)
            binding.genderPickerText.setText(
                setGenderTypeFromApi(
                    casualty!!.gender.toString(), genderTypes
                ),false
            )
        }
        binding.updateButton.setOnClickListener {
            if (incidentId != 0){
                val casualtyNew = Casualty(
                    binding.firstName.text.toString(),
                    setGenderTypeToApi(binding.genderPickerText.text.toString(), genderTypes),
                    binding.lastName.text.toString(),
                    setVictimStatusTypeToApi(binding.autoCompleteTextView.text.toString(), victimStatusTypes),
                    casualty!!.victimInfoId!!
                )
                if (token != null) {
                    paramedicViewModel.putCasualty(incidentId, casualty!!.victimInfoId!!,casualtyNew,token)
                    paramedicViewModel.putCasualtyResponse.observe(viewLifecycleOwner) { response ->
                        if (response.code() == 200) {
                            paramedicViewModel.casualtiesResponse.value!!.body()!!.find { it.victimInfoId == casualty!!.victimInfoId!! }!!.gender = setGenderTypeToApi(binding.genderPickerText.text.toString(),genderTypes)
                            paramedicViewModel.casualtiesResponse.value!!.body()!!.find { it.victimInfoId == casualty!!.victimInfoId!! }!!.status = setVictimStatusTypeToApi(binding.autoCompleteTextView.text.toString(),victimStatusTypes)
                            Log.d("AddVictimInfo", "Casualty updated")
                            Toast.makeText(requireContext(), "OK", Toast.LENGTH_SHORT).show()
                            Navigation.findNavController(view).navigate(com.example.mobileclient.R.id.action_victimDetails_to_victimList)
                        }else{
                            Log.d("AddVictimInfo", "Casualty not added")
                            Toast.makeText(requireContext(), getString(com.example.mobileclient.R.string.error_adding_info), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
        binding.cancelButton.setOnClickListener {
            Navigation.findNavController(view).navigate(com.example.mobileclient.R.id.victimList)
        }
        return view
    }
}