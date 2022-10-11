package com.example.mobileclient.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.mobileclient.R
import com.example.mobileclient.databinding.FragmentAllergyFormBinding
import com.example.mobileclient.model.Allergy
import com.example.mobileclient.viewmodels.UserViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AllergyForm.newInstance] factory method to
 * create an instance of this fragment.
 */
class AllergyForm : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentAllergyFormBinding? = null
    private val userViewModel : UserViewModel by activityViewModels()
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAllergyFormBinding.inflate(inflater, container, false)
        val view = binding.root
        val allergiesArray = resources.getStringArray(R.array.medicalInfoAllergies)
        val arrayAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            allergiesArray
        )
        binding.autoCompleteTextView.setAdapter(arrayAdapter)

        binding.button2.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_allergyForm_to_medicalInfoMain)
        }
        binding.button.setOnClickListener {
            //TODO("Add allergy to database with api call")
            val allergies: ArrayList<Allergy> = ArrayList()
            val allergy = Allergy(
                binding.allergyName.text.toString(),
                binding.autoCompleteTextView.text.toString(),
                binding.additionalInfoInput.text.toString(),
            )
            allergies.add(allergy)
            userViewModel.postMedicalInfoAllergies(2,allergies)
            userViewModel.postCallResponseBody.observe(viewLifecycleOwner) { response ->
                if (response.isSuccessful) {
                    Navigation.findNavController(view).navigate(R.id.action_allergyForm_to_medicalInfoMain)
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AllergyForm.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AllergyForm().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}