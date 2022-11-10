package com.example.mobileclient.fragments

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.mobileclient.R
import com.example.mobileclient.databinding.FragmentMedicalInfoMainBinding
import com.example.mobileclient.databinding.FragmentTrustedPersonFormBinding
import com.example.mobileclient.model.TrustedPerson
import com.example.mobileclient.viewmodels.TrustedPersonViewModel

class TrustedPersonForm : Fragment() {
    private var _binding: FragmentTrustedPersonFormBinding? = null
    private val binding get() = _binding!!
    private val trustedPersonViewModel: TrustedPersonViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTrustedPersonFormBinding.inflate(inflater, container, false)
        val view = binding.root
        val userEmail: String =requireActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE).getString("email", "")!!
        var isUpdate = false
        trustedPersonViewModel.getTrustedPerson(userEmail)
        trustedPersonViewModel.getTrustedPersonResponse.observe(viewLifecycleOwner) { response ->
            if (response.isSuccessful) {
                binding.firstNameInput.setText(response.body()!!.firstName)
                binding.lastNameInput.setText(response.body()!!.lastName)
                binding.emailInput.setText(response.body()!!.email)
                binding.phonenumberInput.setText(response.body()!!.phone)
                isUpdate = true
            }
        }
        binding.cancelButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_trustedPersonForm_to_medicalInfoMain)
        }
        binding.sendButton.setOnClickListener {
            if (isUpdate){
                val trustedPerson = TrustedPerson(
                    userEmail,
                    binding.firstNameInput.text.toString(),
                    binding.lastNameInput.text.toString(),
                    binding.emailInput.text.toString(),
                    binding.phonenumberInput.text.toString(),
                )
                trustedPersonViewModel.putTrustedPerson(trustedPerson)
                trustedPersonViewModel.updateCallResponseBody.observe(viewLifecycleOwner) { response ->
                    if (response.isSuccessful) {
                        Navigation.findNavController(view).navigate(R.id.action_trustedPersonForm_to_medicalInfoMain)
                    }
                }
            }else{
                val trustedPerson = TrustedPerson(
                    userEmail,
                    binding.firstNameInput.text.toString(),
                    binding.lastNameInput.text.toString(),
                    binding.emailInput.text.toString(),
                    binding.phonenumberInput.text.toString(),
                )
                trustedPersonViewModel.postTrustedPerson(trustedPerson)
                trustedPersonViewModel.postCallResponseBody.observe(viewLifecycleOwner) { response ->
                    if (response.isSuccessful) {
                        Navigation.findNavController(view).navigate(R.id.action_trustedPersonForm_to_medicalInfoMain)
                    }
                }
            }

        }
        return view
    }
}