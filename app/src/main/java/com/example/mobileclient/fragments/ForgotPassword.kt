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
import com.example.mobileclient.databinding.FragmentFacilitiesMapBinding
import com.example.mobileclient.databinding.FragmentForgotPasswordBinding
import com.example.mobileclient.viewmodels.FacilitiesViewModel
import com.example.mobileclient.viewmodels.UserViewModel

class ForgotPassword : Fragment() {
    private var _binding: FragmentForgotPasswordBinding? = null
    private val binding get() = _binding!!
    private val userViewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.passwordButton.setOnClickListener {
            Log.d("ForgotPassword", "Forgot password button clicked")
            val email = binding.emailInput.text.toString()
            userViewModel.resetPassword(email)
            userViewModel.changePasswordResponse.observe(viewLifecycleOwner) { response ->
                if (response.isSuccessful) {
                    binding.emailFieldForg.visibility = View.GONE
                    binding.tokenField?.visibility = View.VISIBLE
                    binding.newPasswordField?.visibility = View.VISIBLE
                    binding.passwordButton.visibility = View.GONE
                    binding.tokenButton?.visibility = View.VISIBLE
                } else {
                    binding.emailFieldForg.error = "Error"
                }
            }
        }
        binding.tokenButton?.setOnClickListener {

            val token = binding.tokenFieldInput?.text.toString()
            val newPassword = binding.newPasswordInput?.text.toString()
            userViewModel.confirmPasswordReset(token, newPassword)
            userViewModel.changePasswordResponse.observe(viewLifecycleOwner) { response ->
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), getString(R.string.password_changed), Toast.LENGTH_SHORT).show()
                    Navigation.findNavController(view).navigate(R.id.action_forgotPassword_to_login)
                }
            }
        }
        return view
    }
}