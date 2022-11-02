package com.example.mobileclient.fragments

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
import com.example.mobileclient.databinding.FragmentLoggedInScreenBinding
import com.example.mobileclient.model.Allergy
import com.example.mobileclient.model.Disease
import com.example.mobileclient.viewmodels.UserViewModel

private const val disease = "disease"

class DiseaseDetails : Fragment() {
    private var disease: Disease? = null
    private var _binding: FragmentDiseaseDetailsBinding? = null
    private val userViewModel: UserViewModel by activityViewModels()

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            disease = it.getSerializable(com.example.mobileclient.fragments.disease) as Disease
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDiseaseDetailsBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.diseaseName.setText(disease?.diseaseName)
        binding.adittionalInfoInput.setText(disease?.description)
        if (disease?.shareWithBand == true) {
            binding.shareSwitch.isChecked = true
        }

        binding.button.setOnClickListener {
            val updatedDisease = Disease(
                disease!!.userEmail,
                binding.diseaseName.text.toString(),
                binding.adittionalInfoInput.text.toString(),
                binding.shareSwitch.isChecked
            )
            userViewModel.putUserDisease(disease?.diseaseId!!, updatedDisease)
        }

        binding.button2.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_allergyDetails_to_medicalInfoMain)
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

    companion object {
        @JvmStatic
        fun newInstance(disease: Disease) =
            DiseaseDetails().apply {
                arguments = Bundle().apply {
                    putSerializable(com.example.mobileclient.fragments.disease, disease)
                }
            }
    }
}