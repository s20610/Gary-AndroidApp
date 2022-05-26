package com.example.mobileclient.fragments

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
import com.example.mobileclient.model.BloodType
import com.example.mobileclient.model.MedicalInfo
import com.example.mobileclient.model.UserViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BloodTypeForm.newInstance] factory method to
 * create an instance of this fragment.
 */
class BloodTypeForm : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentBloodTypeFormBinding? = null
    private val sharedViewModel: UserViewModel by activityViewModels()
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBloodTypeFormBinding.inflate(inflater, container, false)
        val view = binding.root
        sharedViewModel.getMedicalInfoResponse(2)
        var medicalInfo : MedicalInfo? = null
        sharedViewModel.getUserMedicalInfoResponse.observe(viewLifecycleOwner) { response ->
            if (response.isSuccessful) {
                medicalInfo = response.body()

                when(medicalInfo!!.bloodType){
                    "A_PLUS" -> {
                        binding.rhGroup.check(R.id.rh_plus)
                        binding.bloodGroup.check(R.id.blood_A)
                    }
                    "A_MINUS" -> {
                        binding.rhGroup.check(R.id.rh_minus)
                        binding.bloodGroup.check(R.id.blood_A)
                    }
                    "AB_PLUS" -> {
                        binding.rhGroup.check(R.id.rh_plus)
                        binding.bloodGroup.check(R.id.blood_AB)
                    }
                    "AB_MINUS" -> {
                        binding.rhGroup.check(R.id.rh_minus)
                        binding.bloodGroup.check(R.id.blood_AB)
                    }
                    "B_PLUS" -> {
                        binding.rhGroup.check(R.id.rh_plus)
                        binding.bloodGroup.check(R.id.blood_B)
                    }
                    "B_MINUS" -> {
                        binding.rhGroup.check(R.id.rh_minus)
                        binding.bloodGroup.check(R.id.blood_B)
                    }
                    "O_PLUS" -> {
                        binding.rhGroup.check(R.id.rh_plus)
                        binding.bloodGroup.check(R.id.blood_0)
                    }
                    "O_MINUS" -> {
                        binding.rhGroup.check(R.id.rh_minus)
                        binding.bloodGroup.check(R.id.blood_0)
                    }
                }

            } else {
                Toast.makeText(context, "Login error" + response.code(), Toast.LENGTH_LONG).show()
                Log.d("Login Response", response.body().toString())
                Log.d("Response Code: ", response.code().toString())
            }


        }

        binding.button2.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_bloodTypeForm_to_medicalInfoMain)
        }

        binding.button.setOnClickListener{
            var rh = ""
            if(binding.rhPlus.isChecked){
                rh = "PLUS"
            }else if(binding.rhMinus.isChecked){
                rh = "MINUS"
            }
            var blood = ""
            if(binding.blood0.isChecked){
                blood = "O"
            }else if (binding.bloodA.isChecked){
                blood = "A"
            }else if(binding.bloodAB.isChecked){
                blood = "AB"
            }else if(binding.bloodB.isChecked){
                blood = "B"
            }
            if(rh.isNotEmpty() && blood.isNotEmpty()) {
                val req = blood + "_"+rh
                medicalInfo?.bloodType = req
                sharedViewModel.postMedicalInfoResponse(2, medicalInfo!!)
                sharedViewModel.postUserMedicalInfoResponse.observe(viewLifecycleOwner) { response ->
                    Log.e("Blood response: ", response.toString())
                    if(response.isSuccessful){
                        Log.i("Blood: ","Success")
                    }
                }
                Toast.makeText(context, "Login error $req", Toast.LENGTH_LONG).show()
                Navigation.findNavController(view)
                    .navigate(R.id.action_bloodTypeForm_to_medicalInfoMain)
            }
        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BloodTypeForm.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BloodTypeForm().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}