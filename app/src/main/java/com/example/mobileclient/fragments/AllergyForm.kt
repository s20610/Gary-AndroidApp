package com.example.mobileclient.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
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
import com.example.mobileclient.viewmodels.TypesViewModel
import com.example.mobileclient.viewmodels.UserViewModel

class AllergyForm : Fragment() {
    private var _binding: FragmentAllergyFormBinding? = null
    private val userViewModel: UserViewModel by activityViewModels()
    private val typesViewModel: TypesViewModel by activityViewModels()
    private val binding get() = _binding!!

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAllergyFormBinding.inflate(inflater, container, false)
        val view = binding.root
        typesViewModel.getAllergyTypes()
        typesViewModel.allergyTypes.observe(viewLifecycleOwner) {
            val arrayAdapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                it,
            )
            binding.autoCompleteTextView.setAdapter(arrayAdapter)
        }

        binding.button2.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_allergyForm_to_medicalInfoMain)
        }
        binding.button.setOnClickListener {
            val userEmail: String =
                requireActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE)
                    .getString("email", "")!!
            val allergy = Allergy(
                userEmail,
                binding.autoCompleteTextView.text.toString(),
                binding.allergyName.text.toString(),
                binding.additionalInfoInput.text.toString(),
            )
            userViewModel.postUserAllergy(allergy)
            userViewModel.postCallResponseBody.observe(viewLifecycleOwner) { response ->
                if (response.isSuccessful) {
                    Navigation.findNavController(view)
                        .navigate(R.id.action_allergyForm_to_medicalInfoMain)
                    Toast.makeText(
                        context,
                        "OK",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        context,
                        "Update error " + response.code(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
        return view
    }
}