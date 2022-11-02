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
import com.example.mobileclient.model.MedicalInfo
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
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
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
//        binding.allergyView.setHasFixedSize(true)
        binding.diseaseView.adapter = ChronicDiseasesAdapter(chronicDiseasesEmpty, this)
//        binding.diseaseView.setHasFixedSize(true)
        var userEmail: String =
            requireActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE)
                .getString("email", "")!!
        userViewModel.getUserMedicalInfo(userEmail)
        userViewModel.getUserMedicalInfoResponse.observe(viewLifecycleOwner) { response ->
            Log.d("Medical Info", response.toString())
            Log.d("Medical Info", response.body().toString())
            if (response.isSuccessful) {
//TODO("Handle changing picture based on blood type")
                val allergiesFromApi: List<Allergy> = response.body()!!.allergies
                val chronicDiseasesFromApi: List<Disease> = response.body()!!.diseases
                binding.allergyView.adapter = AllergyAdapter(allergiesFromApi, this)
                binding.diseaseView.adapter = ChronicDiseasesAdapter(chronicDiseasesFromApi, this)
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

        }
        binding.addButton.setOnClickListener {
            addMedicalInfo(view)
        }
        return view
    }

    private fun addMedicalInfo(view: View) {
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

    override fun onItemClick(position: Int) {
        if (binding.allergyView.adapter is AllergyAdapter) {
            val allergy = (binding.allergyView.adapter as AllergyAdapter).getAllergy(position)
            val bundle = Bundle()
            bundle.putSerializable("allergy", allergy)
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_medicalInfoMain_to_allergyDetails, bundle)
        } else {
            val disease =
                (binding.diseaseView.adapter as ChronicDiseasesAdapter).getDisease(position)
            val bundle = Bundle()
            bundle.putSerializable("disease", disease)
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_medicalInfoMain_to_diseaseDetails, bundle)
        }
    }

}