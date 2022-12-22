package com.example.mobileclient.fragments

import androidx.fragment.app.Fragment
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.mobileclient.R
import com.example.mobileclient.adapter.AllergyAdapter
import com.example.mobileclient.adapter.ChronicDiseasesAdapter
import com.example.mobileclient.databinding.FragmentMedicalInfoMainBinding
import com.example.mobileclient.model.Allergy
import com.example.mobileclient.model.Disease
import com.example.mobileclient.viewmodels.UserViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MedicalInfoMain : Fragment(), AllergyAdapter.OnItemClickListener,
    ChronicDiseasesAdapter.OnItemClickListener {
    private var _binding: FragmentMedicalInfoMainBinding? = null
    private val userViewModel: UserViewModel by activityViewModels()

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMedicalInfoMainBinding.inflate(inflater, container, false)
        val view = binding.root
        //Data before api request is handled
        val allergiesEmpty: List<Allergy> = mutableListOf(
            Allergy("", "", "", ""),
        )
        val chronicDiseasesEmpty: List<Disease> = mutableListOf(
            Disease("", "", "", true),
        )
        binding.allergyView.adapter = AllergyAdapter(allergiesEmpty, this)
        binding.diseaseView.adapter = ChronicDiseasesAdapter(chronicDiseasesEmpty, this)
        val userEmail: String =
            requireActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE)
                .getString("email", "")!!
        userViewModel.getUserMedicalInfo(userEmail)
        userViewModel.getUserMedicalInfoResponse.observe(viewLifecycleOwner) { response ->
            Log.d("Medical Info", response.body().toString())
            if (response.code() == 200) {
//TODO("Handle changing picture based on blood type")
                val allergiesFromApi: List<Allergy> = response.body()!!.allergies
                val chronicDiseasesFromApi: List<Disease> = response.body()!!.diseases
                binding.allergyView.adapter = AllergyAdapter(allergiesFromApi, this)
                binding.diseaseView.adapter = ChronicDiseasesAdapter(chronicDiseasesFromApi, this)
            }
        }
        binding.imageView.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_medicalInfoMain_to_bloodTypeForm)
        }
        binding.bandButton.setOnClickListener {

        }
        binding.addButton.setOnClickListener {
            addMedicalInfo(view)
        }
        return view
    }

    private fun addMedicalInfo(view: View) {
        context?.let { it1 ->
            MaterialAlertDialogBuilder(it1).setTitle("Add medical info?")
                .setNegativeButton(getString(R.string.cancel)) { dialog, which ->
                    dialog.cancel()
                }.setItems(
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
                        2 -> {
                            Navigation.findNavController(view)
                                .navigate(R.id.action_medicalInfoMain_to_diseaseForm)
                        }
                        else -> {
                            Navigation.findNavController(view)
                                .navigate(R.id.action_medicalInfoMain_to_trustedPersonForm)
                        }
                    }
                    // The 'which' argument contains the index position
                    // of the selected item
                }.create().show()
        }
    }

    override fun onAllergyClick(position: Int) {
        val allergy = (binding.allergyView.adapter as AllergyAdapter).getAllergy(position)
        Log.d("Allergy", allergy.toString())
        userViewModel.setChosenAllergy(allergy)
        Navigation.findNavController(binding.root)
            .navigate(R.id.action_medicalInfoMain_to_allergyDetails)
    }

    override fun onDiseaseClick(position: Int) {
        val disease = (binding.diseaseView.adapter as ChronicDiseasesAdapter).getDisease(position)
        Log.d("Disease", disease.toString())
        userViewModel.setChosenDisease(disease)
        Navigation.findNavController(binding.root)
            .navigate(R.id.action_medicalInfoMain_to_diseaseDetails)
    }
}