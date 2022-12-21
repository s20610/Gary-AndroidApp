package com.example.mobileclient.fragments

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
import com.example.mobileclient.R
import com.example.mobileclient.databinding.FragmentAllergyFormBinding
import com.example.mobileclient.model.Allergy
import com.example.mobileclient.util.setAllergyTypeToApi
import com.example.mobileclient.viewmodels.UserViewModel

class AllergyForm : Fragment() {
    private var _binding: FragmentAllergyFormBinding? = null
    private val userViewModel: UserViewModel by activityViewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val allergyTypes: Array<String> =
            requireContext().resources.getStringArray(R.array.allergyTypes)
        // Inflate the layout for this fragment
        _binding = FragmentAllergyFormBinding.inflate(inflater, container, false)
        val view = binding.root
        val arrayAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            allergyTypes,
        )
        binding.autoCompleteTextView.setAdapter(arrayAdapter)

        binding.button2.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_allergyForm_to_medicalInfoMain)
        }
        binding.button.setOnClickListener {
            val userEmail: String =
                requireActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE)
                    .getString("email", "")!!
            val type =
                setAllergyTypeToApi(binding.autoCompleteTextView.text.toString(), allergyTypes)
            val allergy = Allergy(
                userEmail,
                type,
                binding.allergyName.text.toString(),
                binding.additionalInfoInput.text.toString(),
            )
            Log.d("Allergy", allergy.toString())
            userViewModel.postUserAllergy(allergy)
            userViewModel.postCallResponseBody.observe(viewLifecycleOwner) { response ->
                if (response.code() == 200) {
                    Navigation.findNavController(view)
                        .navigate(R.id.action_allergyForm_to_medicalInfoMain)
                    Toast.makeText(
                        context, "OK", Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        context, "error ${response.code()}", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        return view
    }
}