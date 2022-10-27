package com.example.mobileclient.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.mobileclient.R
import com.example.mobileclient.databinding.FragmentBloodTypeFormBinding
import com.example.mobileclient.model.Blood
import com.example.mobileclient.model.MedicalInfo
import com.example.mobileclient.viewmodels.TypesViewModel
import com.example.mobileclient.viewmodels.UserViewModel
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class BloodTypeForm : Fragment() {
    private var _binding: FragmentBloodTypeFormBinding? = null
    private val userViewModel: UserViewModel by activityViewModels()
    private val typesViewModel: TypesViewModel by activityViewModels()

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentBloodTypeFormBinding.inflate(inflater, container, false)
        val view = binding.root
        val userEmail: String =
            requireActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE)
                .getString("email", "")!!
        userViewModel.getUserMedicalInfo(userEmail)
        typesViewModel.getBloodTypes()
        typesViewModel.getRhTypes()
        var medicalInfo: MedicalInfo? = null
        userViewModel.getUserMedicalInfoResponse.observe(viewLifecycleOwner) { response ->
            when {
                response.isSuccessful -> {
                    medicalInfo = response.body()

//                    when (medicalInfo!!.bloodType) {
//                        "A_PLUS" -> {
//                            binding.rhGroup.check(R.id.rh_plus)
//                            binding.bloodGroup.check(R.id.blood_A)
//                        }
//                        "A_MINUS" -> {
//                            binding.rhGroup.check(R.id.rh_minus)
//                            binding.bloodGroup.check(R.id.blood_A)
//                        }
//                        "AB_PLUS" -> {
//                            binding.rhGroup.check(R.id.rh_plus)
//                            binding.bloodGroup.check(R.id.blood_AB)
//                        }
//                        "AB_MINUS" -> {
//                            binding.rhGroup.check(R.id.rh_minus)
//                            binding.bloodGroup.check(R.id.blood_AB)
//                        }
//                        "B_PLUS" -> {
//                            binding.rhGroup.check(R.id.rh_plus)
//                            binding.bloodGroup.check(R.id.blood_B)
//                        }
//                        "B_MINUS" -> {
//                            binding.rhGroup.check(R.id.rh_minus)
//                            binding.bloodGroup.check(R.id.blood_B)
//                        }
//                        "O_PLUS" -> {
//                            binding.rhGroup.check(R.id.rh_plus)
//                            binding.bloodGroup.check(R.id.blood_0)
//                        }
//                        "O_MINUS" -> {
//                            binding.rhGroup.check(R.id.rh_minus)
//                            binding.bloodGroup.check(R.id.blood_0)
//                        }
//                    }

                }
                else -> {
                    Toast.makeText(context, "Server error" + response.code(), Toast.LENGTH_LONG)
                        .show()
                    Log.d("getUserMedicalInfoError-Body", response.body().toString())
                    Log.d("getUserMedicalInfoError-ResponseCode", response.code().toString())
                }
            }
        }

        binding.cancelButton.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_bloodTypeForm_to_medicalInfoMain)
        }

        binding.sendButton.setOnClickListener {
            var rh = ""
            if (binding.rhPlus.isChecked) {
                rh = "PLUS"
            } else if (binding.rhMinus.isChecked) {
                rh = "MINUS"
            }
            var blood = ""
            if (binding.blood0.isChecked) {
                blood = "ZERO"
            } else if (binding.bloodA.isChecked) {
                blood = "A"
            } else if (binding.bloodAB.isChecked) {
                blood = "AB"
            } else if (binding.bloodB.isChecked) {
                blood = "B"
            }
            if (rh.isNotEmpty() && blood.isNotEmpty()) {
                val blood = Blood(userEmail, rh, blood)
                userViewModel.putUserMedicalInfoBlood(medicalInfo!!.medicalInfoId, blood)
                userViewModel.updateCallResponseBody.observe(viewLifecycleOwner) { response ->
                    if (response.isSuccessful) {
                        Toast.makeText(context, "Update successful", Toast.LENGTH_SHORT).show()
                        Navigation.findNavController(view)
                            .navigate(R.id.action_bloodTypeForm_to_medicalInfoMain)
                    } else {
                        Log.d("Blood type update", response.body().toString())
                        Toast.makeText(
                            context,
                            "Update error " + response.code(),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

            }
        }
        return view
    }
}