package com.example.mobileclient.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.mobileclient.R
import com.example.mobileclient.adapter.AllergyAdapter
import com.example.mobileclient.databinding.FragmentGuestScreenBinding
import com.example.mobileclient.databinding.FragmentMedicalInfoMainBinding
import com.example.mobileclient.model.Allergy
import com.example.mobileclient.model.UserViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MedicalInfoMain.newInstance] factory method to
 * create an instance of this fragment.
 */
class MedicalInfoMain : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentMedicalInfoMainBinding? = null
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
        _binding = FragmentMedicalInfoMainBinding.inflate(inflater, container, false)
        val view = binding.root
        sharedViewModel.getMedicalInfoResponse(2)
        sharedViewModel.getUserMedicalInfoResponse.observe(viewLifecycleOwner) { response ->
            if (response.isSuccessful) {
                Log.d("Login Response", response.body().toString())
                Log.d("Response Code", response.code().toString())
                Toast.makeText(context, "Login successful", LENGTH_LONG).show()
                val medicalInfo = response.body()

                when(medicalInfo!!.bloodType){
                    "A_PLUS" -> binding.imageView.setImageResource(R.drawable.blood_type_a_plus)
                    "A_MINUS" -> binding.imageView.setImageResource(R.drawable.blood_type_a_minus)
                    "AB_PLUS" -> binding.imageView.setImageResource(R.drawable.blood_type_ab__plus)
                    "AB_MINUS" -> binding.imageView.setImageResource(R.drawable.blood_type_ab_minus)
                    "B_PLUS" -> binding.imageView.setImageResource(R.drawable.blood_type_b_plus)
                    "B_MINUS" -> binding.imageView.setImageResource(R.drawable.blood_type_b_minus)
                    "O_PLUS" -> binding.imageView.setImageResource(R.drawable.blood_type_0_plus)
                    "O_MINUS" -> binding.imageView.setImageResource(R.drawable.blood_type_0_minus)
                    "UNKNOWN" -> binding.imageView.setImageResource(R.drawable.blood_type_add)
                    null -> binding.imageView.setImageResource(R.drawable.blood_type_add)
                }

            } else {
                Toast.makeText(context, "Login error" + response.code(), LENGTH_LONG).show()
                Log.d("Login Response", response.body().toString())
                Log.d("Response Code: ", response.code().toString())
            }


        }
        val allergies: List<Allergy> = mutableListOf(
            Allergy("Czekolada", "Jedzenie"),
            Allergy("Pyłki traw", "Wziewna"),
            Allergy("Truskawki", "Jedzenie"),
            Allergy("Jad pszczoły", "Jad"),
            Allergy("Nauka", "Inne")
        )
        binding.allergyView.adapter = AllergyAdapter(allergies)
        binding.allergyView.setHasFixedSize(true)
        binding.imageView.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_medicalInfoMain_to_bloodTypeForm)
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
         * @return A new instance of fragment MedicalInfoMain.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MedicalInfoMain().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}