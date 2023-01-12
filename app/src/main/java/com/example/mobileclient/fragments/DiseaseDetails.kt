package com.example.mobileclient.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.mobileclient.R
import com.example.mobileclient.databinding.FragmentDiseaseDetailsBinding
import com.example.mobileclient.model.Disease
import com.example.mobileclient.viewmodels.UserViewModel

class DiseaseDetails : Fragment() {
    private var disease: Disease? = null
    private var _binding: FragmentDiseaseDetailsBinding? = null
    private val userViewModel: UserViewModel by activityViewModels()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDiseaseDetailsBinding.inflate(inflater, container, false)
        val view = binding.root

        disease = userViewModel.getChosenDisease()
        val userEmail: String =requireActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE).getString("email", "")!!
        if (disease != null) {
            disease?.userEmail = userEmail
            binding.diseaseName.setText(disease?.diseaseName)
            binding.adittionalInfoInput.setText(disease?.description)
            if (disease?.shareWithBand == true) {
                binding.shareSwitch.isChecked = true
            }
        } else {
            Toast.makeText(context, "No disease selected", Toast.LENGTH_SHORT).show()
        }
        binding.button.setOnClickListener {
            val updatedDisease = Disease(
                disease!!.userEmail,
                binding.diseaseName.text.toString(),
                binding.adittionalInfoInput.text.toString(),
                binding.shareSwitch.isChecked
            )
            userViewModel.putUserDisease(disease?.diseaseId!!, updatedDisease)
            userViewModel.updateCallResponseBody.observe(viewLifecycleOwner) { response ->
                if (response.isSuccessful) {
                    Navigation.findNavController(view)
                        .navigate(R.id.action_diseaseDetails_to_medicalInfoMain)
                }else{
                    Toast.makeText(context, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.button2.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_diseaseDetails_to_medicalInfoMain)
        }

        binding.deleteButton.setOnClickListener {
            userViewModel.deleteUserDisese(disease!!.diseaseId)
            userViewModel.deleteCallResponseBody.observe(viewLifecycleOwner) {
                if (it.isSuccessful) {
                    Toast.makeText(context, "Disease deleted", Toast.LENGTH_SHORT).show()
                    Navigation.findNavController(view)
                        .navigate(R.id.action_allergyDetails_to_medicalInfoMain)
                }
            }
        }

        return view
    }
}