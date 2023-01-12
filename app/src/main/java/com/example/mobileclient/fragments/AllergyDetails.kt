package com.example.mobileclient.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.mobileclient.R
import com.example.mobileclient.databinding.FragmentAllergyDetailsBinding
import com.example.mobileclient.model.Allergy
import com.example.mobileclient.util.setAllergyTypeFromApi
import com.example.mobileclient.util.setAllergyTypeToApi
import com.example.mobileclient.viewmodels.UserViewModel

class AllergyDetails : Fragment() {
    private var allergy: Allergy? = null
    private var _binding: FragmentAllergyDetailsBinding? = null
    private val userViewModel: UserViewModel by activityViewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val allergyTypes: Array<String> =
            requireContext().resources.getStringArray(R.array.allergyTypes)
        // Inflate the layout for this fragment
        _binding = FragmentAllergyDetailsBinding.inflate(inflater, container, false)
        val view = binding.root
        allergy = userViewModel.getChosenAllergy()
        val userEmail: String =
            requireActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE)
                .getString("email", "")!!
        if (allergy != null) {
            allergy?.userEmail = userEmail
            binding.autoCompleteTextView.setText(
                setAllergyTypeFromApi(
                    allergy!!.allergyType, allergyTypes
                )
            )
            binding.allergyName.setText(allergy?.allergyName)
            binding.additionalInfoInput.setText(allergy?.other)
        } else {
            Log.d("AllergyDetails", "Allergy is null")
        }

        binding.button.setOnClickListener {
            val allergyType: String =
                setAllergyTypeToApi(binding.autoCompleteTextView.text.toString(), allergyTypes)
            val updatedAllergy = Allergy(
                allergy!!.userEmail,
                allergyType,
                binding.allergyName.text.toString(),
                binding.additionalInfoInput.text.toString(),
            )
            userViewModel.putUserAllergy(allergy?.allergyId!!, updatedAllergy)
            userViewModel.updateCallResponseBody.observe(viewLifecycleOwner) { response ->
                if (response.isSuccessful) {
                    Navigation.findNavController(view)
                        .navigate(R.id.action_allergyDetails_to_medicalInfoMain)
                } else {
                    Toast.makeText(context, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.button2.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_allergyDetails_to_medicalInfoMain)
        }

        binding.deleteButton.setOnClickListener {
            userViewModel.deleteUserAllergy(allergy!!.allergyId)
            userViewModel.deleteCallResponseBody.observe(viewLifecycleOwner) {
                if (it.isSuccessful) {
                    Navigation.findNavController(view)
                        .navigate(R.id.action_allergyDetails_to_medicalInfoMain)
                }
            }
        }
        return view
    }
}