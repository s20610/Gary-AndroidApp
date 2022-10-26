package com.example.mobileclient.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.mobileclient.R
import com.example.mobileclient.databinding.FragmentDiseaseFormBinding
import com.example.mobileclient.model.Disease
import com.example.mobileclient.viewmodels.UserViewModel

class DiseaseForm : Fragment() {
    private var _binding: FragmentDiseaseFormBinding? = null
    private val userViewModel: UserViewModel by activityViewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDiseaseFormBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.button.setOnClickListener {
            val userEmail: String =
                requireActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE)
                    .getString("email", "")!!
            val disease = Disease(
                userEmail,
                binding.startDateInput.text.toString(),
                binding.adittionalInfoInput.text.toString(),
                binding.shareSwitch.isChecked
            )
            userViewModel.postUserDisease(disease)
            userViewModel.postCallResponseBody.observe(viewLifecycleOwner) { response ->
                if (response.isSuccessful) {
                    Toast.makeText(context, "Disease added successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(
                        context, "Disease adding error " + response.code(), Toast.LENGTH_LONG
                    ).show()
                }
            }


            Navigation.findNavController(view).navigate(R.id.action_diseaseForm_to_medicalInfoMain)
        }
        binding.button2.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_diseaseForm_to_medicalInfoMain)
        }

        return view
    }
}