package com.example.mobileclient.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.LENGTH_SHORT
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.mobileclient.R
import com.example.mobileclient.adapter.AllergyAdapter
import com.example.mobileclient.adapter.ChronicDiseasesAdapter
import com.example.mobileclient.databinding.FragmentMedicalInfoMainBinding
import com.example.mobileclient.model.Allergy
import com.example.mobileclient.model.MedicalInfo
import com.example.mobileclient.model.User
import com.example.mobileclient.viewmodels.UserViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MedicalInfoMain : Fragment() {
    private var _binding: FragmentMedicalInfoMainBinding? = null
    private val userViewModel: UserViewModel by activityViewModels()

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMedicalInfoMainBinding.inflate(inflater, container, false)
        val view = binding.root
        //Data before api request is handled
        var medicalInfo: MedicalInfo? = MedicalInfo(0, "A_PLUS", "Brak", "Brak")
        val allergiesEmpty: List<Allergy> = mutableListOf(
            Allergy(medicalInfo!!.allergies, "Jedzenie", "",""),
        )
        val chronicDiseasesEmpty: List<String> = mutableListOf(
            medicalInfo.chronicDiseases
        )
        binding.allergyView.adapter = AllergyAdapter(allergiesEmpty)
        binding.allergyView.setHasFixedSize(true)
        binding.diseaseView.adapter = ChronicDiseasesAdapter(chronicDiseasesEmpty)
        binding.diseaseView.setHasFixedSize(true)
        //
        userViewModel.getMedicalInfoResponse(2)
        userViewModel.getUserMedicalInfoResponse.observe(viewLifecycleOwner) { response ->
            if (response.isSuccessful) {
                medicalInfo = response.body()
                when (medicalInfo!!.bloodType) {
                    "A_PLUS" -> binding.imageView.setImageResource(R.drawable.blood_type_a_plus)
                    "A_MINUS" -> binding.imageView.setImageResource(R.drawable.blood_type_a_minus)
                    "AB_PLUS" -> binding.imageView.setImageResource(R.drawable.blood_type_ab__plus)
                    "AB_MINUS" -> binding.imageView.setImageResource(R.drawable.blood_type_ab_minus)
                    "B_PLUS" -> binding.imageView.setImageResource(R.drawable.blood_type_b_plus)
                    "B_MINUS" -> binding.imageView.setImageResource(R.drawable.blood_type_b_minus)
                    "O_PLUS" -> binding.imageView.setImageResource(R.drawable.blood_type_0_plus)
                    "O_MINUS" -> binding.imageView.setImageResource(R.drawable.blood_type_0_minus)
                    "UNKNOWN" -> binding.imageView.setImageResource(R.drawable.blood_type_add)
                    "" -> binding.imageView.setImageResource(R.drawable.blood_type_add)
                }
                val allergiesFromApi: List<Allergy> = mutableListOf(
                    Allergy("","","",""),
                )
                val chronicDiseasesFromApi: List<String> = mutableListOf(
                    medicalInfo!!.chronicDiseases
                )
                binding.allergyView.adapter = AllergyAdapter(allergiesFromApi)
                binding.diseaseView.adapter = ChronicDiseasesAdapter(chronicDiseasesFromApi)
            } else {
                Toast.makeText(context, "Server error" + response.code(), LENGTH_LONG).show()
                Log.d("getUserMedicalInfoResponseBody", response.body().toString())
                Log.d("getUserMedicalInfoResponseCode", response.code().toString())
            }


        }
        binding.imageView.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_medicalInfoMain_to_bloodTypeForm)
        }
        binding.bandButton.setOnClickListener {
            userViewModel.getUserInfo(2)
            userViewModel.getUserInfoResponse.observe(viewLifecycleOwner) { response ->
                if (response.isSuccessful) {
                    val user: User? = response.body()
                    if (user != null) {
                        Toast.makeText(context, user.bandCode, LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Error ${response.code()}", LENGTH_SHORT).show()
                }
            }
        }
        binding.addButton.setOnClickListener {
            context?.let { it1 ->
                MaterialAlertDialogBuilder(it1).setTitle("Add medical info?")
                    .setNegativeButton("Cancel") { dialog, which ->
                        dialog.cancel()
                    }
                    .setItems(
                        R.array.medicalInfoArray
                    ) { _, which ->
                        when (which) {
                            0 -> {
                                Navigation.findNavController(view)
                                    .navigate(R.id.action_medicalInfoMain_to_bloodTypeForm)
                            }
                            1 -> {
                                Navigation.findNavController(view)
                                    .navigate(R.id.action_medicalInfoMain_to_allergyForm)
                            }
                            else -> {
                                Navigation.findNavController(view)
                                    .navigate(R.id.action_medicalInfoMain_to_diseaseForm)
                            }
                        }
                        // The 'which' argument contains the index position
                        // of the selected item
                    }.create()
                    .show()
            }
        }
        return view
    }
}